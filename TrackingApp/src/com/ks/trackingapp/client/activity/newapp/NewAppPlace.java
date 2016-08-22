package com.ks.trackingapp.client.activity.newapp;

import com.ks.trackingapp.client.activity.basic.BasicPlace;

public class NewAppPlace extends BasicPlace{
	
	private boolean isFromAll;
	private Long appId;
	public NewAppPlace(boolean isFromAll,Long appId) {
		super();
		this.isFromAll = isFromAll;
		this.appId = appId;
	}
	
	public NewAppPlace() {
		super();
	}

	public boolean isFromAll(){
		return isFromAll;
	}
	
	public Long getAppId(){
		return appId;
	}
}
