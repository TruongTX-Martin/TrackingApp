package com.ks.trackingapp.client.activity;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.ks.trackingapp.client.activity.allapp.AllAppView;
import com.ks.trackingapp.client.activity.base.BaseView;
import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.activity.comment.CommentView;
import com.ks.trackingapp.client.activity.home.HomeView;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentView;
import com.ks.trackingapp.client.activity.login.LoginView;
import com.ks.trackingapp.client.activity.newapp.NewAppView;
import com.ks.trackingapp.client.activity.register.RegisterView;

public interface ClientFactory {
	PlaceController getPlaceController();

	EventBus getEventBus();

	BaseView getBaseView();
	
	HomeView getHomeView();
	
	CommentView getCommentView();
	
	
	//new code
	BasicView getBasicView();
	HomeCommentView getHomeCommentView();
	LoginView getLoginView();
	RegisterView getRegisterView();
	AllAppView getAllAppView();
	NewAppView getNewAppView();
}