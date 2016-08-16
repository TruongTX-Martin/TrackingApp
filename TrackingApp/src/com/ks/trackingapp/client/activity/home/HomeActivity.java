package com.ks.trackingapp.client.activity.home;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.base.BaseActivity;
import com.ks.trackingapp.client.event.DeleteObjectEvent;
import com.ks.trackingapp.client.event.DeleteObjectEventHandler;
import com.ks.trackingapp.client.event.ReloadAppEvent;
import com.ks.trackingapp.client.event.ReloadAppEventHandler;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.client.view.DialogNewApp;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.AppItem;
import com.ks.trackingapp.shared.model.IBasic;

public class HomeActivity extends BaseActivity{

	
	
	private HomeView homeView;
	private DialogNewApp dialogNewApp = new DialogNewApp();
	private EventBus eventBus;
	private String appName,platform,packageName;
	public HomeActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		homeView = clientFactory.getHomeView();
		this.eventBus = eventBus;
		super.start(panel, eventBus,homeView);
		panel.setWidget(homeView);
	}
	

	
	@Override
	protected void loadData() {
		super.loadData();
		refreshTableApps();
	}
	
	private void refreshTableApps(){
		TrackingApp.dataService.getListAppItem(homeView.getStart(),Config.PAGER_SIZE, new AsyncCallback<ArrayList<AppItem>>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Failed to get data");
			}

			@Override
			public void onSuccess(ArrayList<AppItem> result) {
				int page = homeView.getStart() % Config.PAGER_SIZE;
				homeView.updateData(page, new ArrayList<IBasic>(result));
			}
		});
	}
	
	@Override
	protected void handleEvent() {
		super.handleEvent();
		changeTapColor(super.getBaseView().getLayout().getTapApps());
		addHandlerRegistration(homeView.getAddApp().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				dialogNewApp.show();
			}
		}));
		
		addHandlerRegistration(dialogNewApp.getBtnAdd().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				addNewApp();
			}
		}));
		
		addHandlerRegistration(dialogNewApp.getBtnCancel().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				dialogNewApp.hide();
			}
		}));
		
		addHandlerRegistration(dialogNewApp.getListBoxPlatform().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				String value = dialogNewApp.getListBoxPlatform().getSelectedValue();
				if(value.contains(Config.PLATFORM_ANDROID_VALUE+"")){
					dialogNewApp.getHTMLPackageName().setText("");
					dialogNewApp.getHTMLPackageName().setText("PackageName:");
				}else{
					dialogNewApp.getHTMLPackageName().setText("");
					dialogNewApp.getHTMLPackageName().setText("AppleId:");
				}
			}
		}));
		if(eventBus != null) {
			addHandlerRegistration(eventBus.addHandler(DeleteObjectEvent.TYPE, new DeleteObjectEventHandler() {
				
				@Override
				public void deleteObject(DeleteObjectEvent objectEvent) {
					IBasic basic = objectEvent.getBasic();
					if(basic != null){
						TrackingApp.dataService.deleteObject(basic, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								Toaster.showToast("delete failed");
							}

							@Override
							public void onSuccess(Void result) {
								Toaster.showToast("delete success");
								homeView.updateData(0, new ArrayList<IBasic>());
//								refreshTableApps();
							}
						});
					}
				}
			}));
			addHandlerRegistration(eventBus.addHandler(ReloadAppEvent.TYPE, new ReloadAppEventHandler() {
				
				@Override
				public void reloadListApp(ReloadAppEvent event) {
					refreshTableApps();
				}
			}));
		}
		
	}
	
	private void addNewApp() {
		this.appName = dialogNewApp.getTbAppName().getText().toString();
		this.platform = dialogNewApp.getListBoxPlatform().getSelectedValue();
		this.packageName = dialogNewApp.getTbPackageName().getText().toString();
		if(!ClientUtils.validate(appName)){
			Toaster.showToast("Chưa nhập AppName?");
			return;
		}
		if(!ClientUtils.validate(packageName)){
			if(platform.contains(Config.PLATFORM_ANDROID_VALUE+"")) {
				Toaster.showToast("Chưa nhập packagename?");
			}else{
				Toaster.showToast("Chưa nhập appleid?");
			}
			return;
		}
		AppItem appItem = new AppItem(appName, packageName, Integer.parseInt(platform));
		TrackingApp.dataService.addNewItemApp(appItem, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				refreshTableApps();
				if(platform.contains(Config.PLATFORM_IOS_VALUE+"")) {
					//get comment ios 
					TrackingApp.dataService.getIOSComment(packageName.trim(),platform,appName, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							Toaster.showToast("get comment app failed, please check appleId again.");
						}

						@Override
						public void onSuccess(Void result) {
							Toaster.showToast("Get Comment Success.");
						}
					});
				}else{
					TrackingApp.dataService.getCommentAndroid(packageName.trim(), platform, appName, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							Toaster.showToast("Get comment android faild");
						}

						@Override
						public void onSuccess(Void result) {
							Toaster.showToast("Get comment android success");
						}
					});
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Add new item failed");
			}
		});
		dialogNewApp.hide();
		
	}
}
