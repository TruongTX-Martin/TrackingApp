package com.ks.trackingapp.client.activity.comment;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.base.BaseActivity;
import com.ks.trackingapp.client.event.ReloadCommentEvent;
import com.ks.trackingapp.client.event.ReloadCommentEventHandler;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.shared.AndroidItem;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.IOSItem;
import com.ks.trackingapp.shared.model.IBasic;

public class CommentActivity extends BaseActivity{

	
	private CommentView view;
	private EventBus eventBus;
	
	public CommentActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getCommentView();
		this.eventBus = eventBus;
		super.start(panel, eventBus,view);
		panel.setWidget(view);
	}
	
	
	@Override
	protected void handleEvent() {
		super.handleEvent();
		changeTapColor(super.getBaseView().getLayout().getTapComments());
		
		//add handle event 
		if(eventBus != null) {
			addHandlerRegistration(eventBus.addHandler(ReloadCommentEvent.TYPE, new ReloadCommentEventHandler() {
				
				@Override
				public void reloadComment(ReloadCommentEvent event) {
					refreshTable();
				}
			}));
		}
		
		view.getListboxPlatform().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				String value = view.getListboxPlatform().getSelectedValue();
				if(value.contains(Config.PLATFORM_IOS_VALUE+"")) {
					Toaster.showToast("Choose ios");
//					requestIOSComment();
				}else{
					Toaster.showToast("Choose android");
//					requestAndroidComment();
				}
			}
		});
	}
	
	@Override
	protected void loadData() {
		super.loadData();
		refreshTable();
	}
	
	private void refreshTable(){
		String value = view.getListboxPlatform().getSelectedValue();
		if(value.contains(Config.PLATFORM_IOS_VALUE+"")){
			requestIOSComment();
		}else{
			requestAndroidComment();
		}
	}
	
	private void requestIOSComment(){
		TrackingApp.dataService.getListCommentIOS(view.getStart(), Config.PAGER_SIZE, new AsyncCallback<ArrayList<IOSItem>>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Failed to get comment ios");
			}

			@Override
			public void onSuccess(ArrayList<IOSItem> result) {
				view.updateData(view.getStart(), new ArrayList<IBasic>(result));
			}
		});
	}
	
	private void requestAndroidComment() {
		TrackingApp.dataService.getListCommentAndroid(view.getStart(), Config.PAGER_SIZE, new AsyncCallback<ArrayList<AndroidItem>>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Get comment android failed");
			}

			@Override
			public void onSuccess(ArrayList<AndroidItem> result) {
				view.updateData(view.getStart(), new ArrayList<IBasic>(result));
			}
		});
	}
	
	
	

}
