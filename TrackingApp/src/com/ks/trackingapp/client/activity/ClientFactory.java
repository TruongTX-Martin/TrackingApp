package com.ks.trackingapp.client.activity;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.ks.trackingapp.client.activity.base.BaseView;
import com.ks.trackingapp.client.activity.comment.CommentView;
import com.ks.trackingapp.client.activity.home.HomeView;

public interface ClientFactory {
	PlaceController getPlaceController();

	EventBus getEventBus();

	BaseView getBaseView();
	
	HomeView getHomeView();
	
	CommentView getCommentView();
}
