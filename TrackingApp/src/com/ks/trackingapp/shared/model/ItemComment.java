package com.ks.trackingapp.shared.model;

import java.util.ArrayList;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.ks.trackingapp.shared.IOSItemBean;

@SuppressWarnings("serial")
@Entity
public class ItemComment implements IBasic {

	private @Id
	Long id;
	private String title;
	private String comment;
	@Index
	private String platform;
	private String appname;
	private @Index
	Long appId;
	private @Index
	Date date;
	private @Index
	int rating;

	public ItemComment() {
	}

	public ItemComment(String json) {
		System.out.println("JSON IOS:" + json);
		IOSItemBean myBean = new IOSItemBean(json);
		this.title = myBean.getTitle();
		this.date = myBean.getDate();
		this.comment = myBean.getComment();
		this.rating = myBean.getRating();
	}

	public ArrayList<ItemComment> getListItemComment(String json, String appName) {
		ArrayList<ItemComment> list = new IOSItemBean().getListItemComment(
				json, appName);
		System.out.println(list);
		return list;
	}

	public Long getId() {
		return id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getAppId() {
		return appId;
	}

	public Date getDate() {
		return date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
