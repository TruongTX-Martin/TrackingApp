package com.ks.trackingapp.shared.interfacemodel;

public interface IUserInfo extends IBasic{

	public void setId(Long id);
	public Long getId();
	
	public void setUserName(String userName);
	public String getUserName();
	
	public void setUserView(String userView);
	public String getUserView();
	
	public void setUserEmail(String userEmail);
	public String getUserEmail();
	
	public void setPassword(String userPassword);
	public String getPassword();
}
