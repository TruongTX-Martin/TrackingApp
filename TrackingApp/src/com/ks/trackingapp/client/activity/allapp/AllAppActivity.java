package com.ks.trackingapp.client.activity.allapp;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.basic.BasicActivity;

public class AllAppActivity extends BasicActivity{

	private AllAppView allAppView;
	public AllAppActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		allAppView  = clientFactory.getAllAppView();
		super.start(panel, eventBus,allAppView);
		panel.setWidget(allAppView);
	}
	
	

}
