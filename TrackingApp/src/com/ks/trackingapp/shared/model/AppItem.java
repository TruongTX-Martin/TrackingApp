package com.ks.trackingapp.shared.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class AppItem implements IBasic{

	@Id
	private Long id;
	private String appName;
	private String packageName;
	private int platform; // 1 - android, 0- ios

	
	
	public AppItem() {
	}
	public AppItem(String appName,String packageName,int platform) {
		this.appName = appName;
		this.packageName = packageName;
		this.platform = platform;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

}
