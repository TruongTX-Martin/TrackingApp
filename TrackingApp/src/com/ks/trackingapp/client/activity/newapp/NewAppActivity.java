package com.ks.trackingapp.client.activity.newapp;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentPlace;
import com.ks.trackingapp.client.activity.login.LoginPlace;
import com.ks.trackingapp.client.manager.TrackingManager;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.UserInfo;

public class NewAppActivity extends BasicActivity{

	private NewAppView view;
	
	public NewAppActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getNewAppView();
		super.start(panel, eventBus,view);
		panel.setWidget(view);
	}
	
	@Override
	protected void handleEvent() {
		super.handleEvent();
		view.getCheckBoxAndroid().setValue(true);
		view.getCheckBoxIOS().setValue(true);
		view.getTextboxPakageName().setVisible(true);
		view.getTextboxAppleId().setVisible(true);
		//handle checkbox android
		addHandlerRegistration(view.getCheckBoxAndroid().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				boolean isAndroid = view.getCheckBoxAndroid().getValue();
				if(isAndroid){
					view.getTextboxPakageName().setVisible(true);
				}else{
					view.getTextboxPakageName().setVisible(false);
				}
			}
		}));
		
		//handle checkbox android
		addHandlerRegistration(view.getCheckBoxIOS().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
					
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				boolean isIOS = view.getCheckBoxIOS().getValue();
					if(isIOS){
						view.getTextboxAppleId().setVisible(true);
					}else{
						view.getTextboxAppleId().setVisible(false);
					}
			}
		}));
		
		//handle button check
		addHandlerRegistration(view.getButtonCheck().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				addNewApp();
			}
		}));
	}
	

	private void addNewApp(){
		String appName = view.getTextboxAppname().getText().toString();
		String packageName = view.getTextboxPakageName().getText().toString();
		String appleId = view.getTextboxAppleId().getText().toString();
		boolean isAndroid = view.getCheckBoxAndroid().getValue();
		boolean isIOS = view.getCheckBoxIOS().getValue();
		if(!ClientUtils.validate(appName)) {
			Toaster.showToast("Please type your appname.");
			return;
		}
		if(isAndroid && !ClientUtils.validate(packageName)){
			Toaster.showToast("Please type package name");
			return;
		}
		if(isIOS && !ClientUtils.validate(appleId)) {
			Toaster.showToast("Please type apple id");
			return;
		}
		if(!isAndroid && !isIOS){
			Toaster.showToast("Please choose either Android or IOS.");
			return;
		}
		UserInfo currentUser = TrackingManager.newInstance().getCurrentUser();
		if(currentUser == null){
			Toaster.showToast("Please login to continue use app");
			goTo(new LoginPlace());
			return;
		}
		ItemApp itemApp = new ItemApp(appName, currentUser.getId());
		if(isAndroid){
			itemApp.setAndroid(true);
			itemApp.setPackageName(packageName);
		}
		if(isIOS) {
			itemApp.setIOS(true);
			itemApp.setAppleId(appleId);
		}
		TrackingApp.dataService.appAddNew(itemApp, new AsyncCallback<ItemApp>() {
			
			@Override
			public void onSuccess(ItemApp itemResult) {
				if(itemResult.isSuccess()){
					Toaster.showToast("Add app success");
					getCommentForApp(itemResult);
				}else{
					if(itemResult.getAddFailedForReason() == Config.APPITEM_APPNAME_EXITS){
						Toaster.showToast("Failed because your App name exits");
						view.getTextboxAppname().setText("");
					}else if(itemResult.getAddFailedForReason() == Config.APPITEM_PACKAGENAME_EXITS){
						Toaster.showToast("Failed because packagename exits");
						view.getTextboxPakageName().setText("");
					}else if(itemResult.getAddFailedForReason() == Config.APPITEM_APPLEID_EXITS){
						Toaster.showToast("Failed because appleid exits");
						view.getTextboxAppleId().setText("");
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Add app failed,please check your network?");
			}
		});
		
	}
	
	private void getCommentForApp(ItemApp itemApp){
		TrackingApp.dataService.commentGetAppComment(itemApp, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Get comment failed");
			}

			@Override
			public void onSuccess(Void result) {
				Toaster.showToast("Get comment success");
			}
		});
	}
	@Override
	protected void onBackPress() {
		super.onBackPress();
		goTo(new HomeCommentPlace());
	}

}
