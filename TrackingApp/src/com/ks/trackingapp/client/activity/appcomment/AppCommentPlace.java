package com.ks.trackingapp.client.activity.appcomment;

import com.ks.trackingapp.client.activity.basic.BasicPlace;

public class AppCommentPlace extends BasicPlace {

	private Long appId = -1L;

	public AppCommentPlace() {
		super();
	}

	public AppCommentPlace(Long appId) {
		super();
		this.appId = appId;
	}

	public Long getAppId() {
		return appId;
	}
}
