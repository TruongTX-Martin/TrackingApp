package com.ks.trackingapp.client.activity.newapp;

import com.ks.trackingapp.client.activity.basic.BasicPlace;

public class NewAppPlace extends BasicPlace{
	
	private boolean isFromAll;
	private Long appId;
	private boolean isEdit;
	public NewAppPlace(boolean isFromAll,Long appId) {
		super();
		this.isFromAll = isFromAll;
		this.appId = appId;
	}
	
	public NewAppPlace() {
		super();
	}

	
	public void setIsEdit(boolean edit){
		this.isEdit = edit;
	}
	public boolean isEdit(){
		return isEdit;
	}
	public boolean isFromAll(){
		return isFromAll;
	}
	
	public Long getAppId(){
		return appId;
	}
}
