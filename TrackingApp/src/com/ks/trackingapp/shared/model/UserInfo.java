package com.ks.trackingapp.shared.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.ks.trackingapp.shared.interfacemodel.IUserInfo;

@SuppressWarnings("serial")
@Entity
public class UserInfo implements IUserInfo {

	private @Id Long id;
	@Index
	private String userName;
	private String userView;
	@Index
	private String userEmail;
	@Index
	private String password;
	@Ignore
	private int loginFailtReason = 0;
	@Ignore
	private boolean isSuccess;

	@Ignore
	public static final String createTableCommand = "id text PRIMARY KEY NOT NULL UNIQUE,"
			+ "userName text," + "userPassword text";

	public UserInfo() {
	}

	public UserInfo(IUserInfo iUserInfo) {
			this.id = iUserInfo.getId();
			this.userName = iUserInfo.getUserName();
			this.userView = iUserInfo.getUserView();
			this.userEmail = iUserInfo.getUserEmail();
			this.password = iUserInfo.getPassword();
	}

	public UserInfo(String username, String userview, String useremail,
			String password) {
		this.userName = username;
		this.userView = userview;
		this.userEmail = useremail;
		this.password = password;
		
	}

	public void setLoginFailtReason(int loginFailtReason) {
		this.loginFailtReason = loginFailtReason;
	}

	public int getLoginFailtReason() {
		return loginFailtReason;
	}

	public void setIsSuccess(boolean success) {
		this.isSuccess = success;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	@Override
	public int getStatus() {
		return 0;
	}

	@Override
	public void setPassword(String userPassword) {
		this.password = userPassword;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserView(String userView) {
		this.userView = userView;
	}

	@Override
	public String getUserView() {
		return userView;
	}

	@Override
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String getUserEmail() {
		return userEmail;
	}

}
