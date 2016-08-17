package com.ks.trackingapp.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.ks.trackingapp.client.activity.allapp.AllAppActivity;
import com.ks.trackingapp.client.activity.allapp.AllAppPlace;
import com.ks.trackingapp.client.activity.appcomment.AppCommentActivity;
import com.ks.trackingapp.client.activity.appcomment.AppCommentPlace;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentActivity;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentPlace;
import com.ks.trackingapp.client.activity.login.LoginActivity;
import com.ks.trackingapp.client.activity.login.LoginPlace;
import com.ks.trackingapp.client.activity.newapp.NewAppActivity;
import com.ks.trackingapp.client.activity.newapp.NewAppPlace;
import com.ks.trackingapp.client.activity.register.RegisterActivity;
import com.ks.trackingapp.client.activity.register.RegisterPlace;

public class PhoneActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public PhoneActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		 if (place instanceof HomeCommentPlace) {
			return new HomeCommentActivity(clientFactory, place);
		} else if (place instanceof LoginPlace) {
			return new LoginActivity(clientFactory, place);
		} else if (place instanceof RegisterPlace) {
			return new RegisterActivity(clientFactory, place);
		} else if (place instanceof AllAppPlace) {
			return new AllAppActivity(clientFactory, place);
		} else if (place instanceof NewAppPlace) {
			return new NewAppActivity(clientFactory, place);
		}else if (place instanceof AppCommentPlace){
			return new AppCommentActivity(clientFactory, place);
		}
		return null;
	}

}
