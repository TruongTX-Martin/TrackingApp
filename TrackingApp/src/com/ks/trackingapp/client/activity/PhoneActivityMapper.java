package com.ks.trackingapp.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.ks.trackingapp.client.activity.comment.CommentActivity;
import com.ks.trackingapp.client.activity.comment.CommentPlace;
import com.ks.trackingapp.client.activity.home.HomeActivity;
import com.ks.trackingapp.client.activity.home.HomePlace;

public class PhoneActivityMapper implements ActivityMapper{
	
	
	private ClientFactory clientFactory;
	public PhoneActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if(place instanceof HomePlace){
			return new HomeActivity(clientFactory, place);
		}else if (place instanceof CommentPlace) {
			return new CommentActivity(clientFactory, place);
		}
		return null;
	}

}
