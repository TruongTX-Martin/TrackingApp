package com.ks.trackingapp.client.activity.login;

import com.ks.trackingapp.client.activity.basic.BasicPlace;
import com.ks.trackingapp.shared.model.UserInfo;

public class LoginPlace extends BasicPlace{
	
	private UserInfo userInfo;
	public LoginPlace(UserInfo userInfo) {
		super();
		this.userInfo = userInfo;
	}
	public LoginPlace() {
		super();
	}
	
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}

}
