package com.ks.trackingapp.client.manager;

import com.ks.trackingapp.shared.model.UserInfo;

public class TrackingManager {
	
	public static TrackingManager instance;
	
	private UserInfo currentUser = null;
	public static TrackingManager newInstance(){
		if(instance == null){
			instance = new TrackingManager();
		}
		return instance;
	}
	
	
	public void setCurrentUser(UserInfo currentUser) {
		this.currentUser = currentUser;
	}
	
	public UserInfo getCurrentUser() {
		return currentUser;
	}

}
