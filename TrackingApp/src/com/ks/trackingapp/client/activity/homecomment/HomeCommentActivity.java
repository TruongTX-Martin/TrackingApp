package com.ks.trackingapp.client.activity.homecomment;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.basic.BasicActivity;

public class HomeCommentActivity extends BasicActivity {

	private HomeCommentView commentView;
	
	public HomeCommentActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		commentView = clientFactory.getHomeCommentView();
		super.start(panel, eventBus,commentView);
		panel.setWidget(commentView);
	}

}
