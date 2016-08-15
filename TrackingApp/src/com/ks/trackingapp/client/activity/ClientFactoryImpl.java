package com.ks.trackingapp.client.activity;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.ks.trackingapp.client.activity.base.BaseView;
import com.ks.trackingapp.client.activity.comment.CommentView;
import com.ks.trackingapp.client.activity.comment.CommentViewImpl;
import com.ks.trackingapp.client.activity.home.HomeView;
import com.ks.trackingapp.client.activity.home.HomeViewImpl;

public class ClientFactoryImpl implements ClientFactory{

	
	private SimpleEventBus eventBus;
	private PlaceController placeController;
	private BaseView basicView;
	private HomeView homeView;
	private CommentView commentView;
	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();
		placeController = new PlaceController(eventBus);
	}
	
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public BaseView getBaseView() {
		return basicView;
	}

	@Override
	public HomeView getHomeView() {
		if(homeView == null){
			homeView = new HomeViewImpl();
		}
		return homeView;
	}

	@Override
	public CommentView getCommentView() {
		if(commentView == null) {
			commentView = new CommentViewImpl();
		}
		return commentView;
	}

}
