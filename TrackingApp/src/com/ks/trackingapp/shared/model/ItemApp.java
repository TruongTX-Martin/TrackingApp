package com.ks.trackingapp.shared.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ItemApp implements IBasic {

	private @Id
	Long id;
	private @Index
	Long userId;
	private String appName;
	private String packageName;
	private String appleId;
	@Index
	private boolean isAndroid;
	@Index
	private boolean isIOS;
	
	public ItemApp() {
		// TODO Auto-generated constructor stub
	}

	public ItemApp(String appName,Long userId) {
		this.appName = appName;
		this.userId = userId;
	}

	public ItemApp(String appName, String pakageName, String appleId,
			Long userId) {
		this.appName = appName;
		this.packageName = packageName;
		this.appleId = appleId;
		this.userId = userId;
	}

	public void setAndroid(boolean isAndroid){
		this.isAndroid = isAndroid;
	}
	
	public void setIOS(boolean isIOS){
		this.isIOS = isIOS;
	}
	
	public boolean isAndroid(){
		return isAndroid;
	}
	public boolean isIOS(){
		return isIOS;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAppleId() {
		return appleId;
	}

	public void setAppleId(String appleId) {
		this.appleId = appleId;
	}

}
