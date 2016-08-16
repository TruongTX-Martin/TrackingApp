package com.ks.trackingapp.shared.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class UserInfo implements IBasic {

	private @Id
	Long id;
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

	public UserInfo() {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserView() {
		return userView;
	}

	public void setUserView(String userView) {
		this.userView = userView;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
