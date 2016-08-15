package com.ks.trackingapp.shared;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.ks.trackingapp.client.util.IOSItemBean;
import com.ks.trackingapp.shared.model.IBasic;

@SuppressWarnings("serial")
@Entity
public class IOSItem implements IBasic {

	@Id
	private Long id;
	@Index
	private Date date;

	private String title;
	private String comment;
	private String platForm;
	private String appName;
	private int rating;

	private final String TITLE = "";
	private final String RATING = "";
	private final String CONTENT = "";
	private final String DATE = "";

	public IOSItem() {
	}

	public IOSItem(String jsonString) {
		IOSItemBean myBean = new IOSItemBean(jsonString);
		this.title = myBean.getTitle();
		this.date = myBean.getDate();
		this.comment = myBean.getComment();
		this.rating = myBean.getRating();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public void setId(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEditing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEditing(boolean editing) {
		// TODO Auto-generated method stub

	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppName() {
		return appName;
	}

	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}

	public String getPlatForm() {
		return platForm;
	}
}