package com.ks.trackingapp.client.activity.appcomment;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.allapp.AllAppPlace;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.shared.model.ItemComment;

public class AppCommentActivity extends BasicActivity{

	private AppCommentView view;
	private Long appId = -1L;
	public AppCommentActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
		appId = ((AppCommentPlace)place).getAppId();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getAppCommentView();
		super.start(panel, eventBus,view);
		panel.setWidget(view);
	}
	
	@Override
	protected void loadData() {
		super.loadData();
		if(appId != -1L){
			TrackingApp.dataService.commentGetFromAppId(appId, new AsyncCallback<ArrayList<ItemComment>>() {
				
				@Override
				public void onSuccess(ArrayList<ItemComment> result) {
					view.showCommentApp(result);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Toaster.showToast("Get comment failed");
				}
			});
		}
	}
	
	@Override
	protected void onBackPress() {
		super.onBackPress();
		goTo(new AllAppPlace());
	}

}
