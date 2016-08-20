package com.ks.trackingapp.client.activity.newapp;

import com.ks.trackingapp.client.activity.basic.BasicPlace;

public class NewAppPlace extends BasicPlace{
	
	private boolean isFromAll;
	public NewAppPlace(boolean isFromAll) {
		super();
		this.isFromAll = isFromAll;
	}
	public NewAppPlace() {
		super();
	}

	public boolean isFromAll(){
		return isFromAll;
	}
}
