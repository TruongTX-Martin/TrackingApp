package com.ks.trackingapp.client.activity.appcomment;

import com.google.gwt.place.shared.Place;
import com.ks.trackingapp.client.activity.basic.BasicPlace;

public class AppCommentPlace extends BasicPlace {

	private Long appId = -1L;
	private Place placePrevious;
	public AppCommentPlace() {
		super();
	}
	
	public void setPlacePrevious(Place placePrevious) {
		this.placePrevious = placePrevious;
	}
	public Place getPlacePrevious() {
		return placePrevious;
	}

	public AppCommentPlace(Long appId) {
		super();
		this.appId = appId;
	}

	public Long getAppId() {
		return appId;
	}
}
