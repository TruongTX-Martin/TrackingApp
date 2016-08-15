package com.ks.trackingapp.client.util;

import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class IOSItemBean {

	private Date date = new Date();
	private String title;
	private String comment;
	private String platForm;
	private String appName;
	private int rating;

	private JSONObject mJSON;
	//"2016-01-13T17:39:17-07:00"
	DateTimeFormat formatter =  DateTimeFormat.getFormat("dd/MM/yyyy");

	public IOSItemBean(String jsonString) {
		try {
			JSONValue value = JSONParser.parseStrict(jsonString);
			if (value.isObject() != null) {
				JSONObject jsonObject = (JSONObject) value;
				if (jsonObject.containsKey("feed")) {
					JSONObject totalObject = (JSONObject) jsonObject
							.get("feed");
					if (totalObject.containsKey("entry")) {
						JSONArray arrayEntry = (JSONArray) totalObject
								.get("entry");
						if (arrayEntry.size() > 0) {
							for (int i = 0; i < arrayEntry.size(); i++) {
								JSONObject obEntry = (JSONObject) arrayEntry
										.get(i);
								this.mJSON = obEntry;
								this.title = getTitle();
								this.date = getDate();
								this.comment = getComment();
								this.rating = getRating();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			ClientUtils.log("ISOITem Exception:" + e.getMessage());
		}
	}

	public void setJSON(JSONObject mJSON) {
		this.mJSON = mJSON;
	}

	public Date getDate(){
		if (mJSON.containsKey("im:releaseDate")) {
			String dateString = getValueLabel(mJSON, "im:releaseDate");
			try {
				this.date = formatter.parse(dateString);
			} catch (Exception e) {
				this.date = new Date();
			}
		}
		return this.date;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		if (mJSON != null && mJSON.containsKey("title")) {
			String title = getValueLabel(mJSON, "title");
			this.title = title;
		}
		return this.title;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		String comment = getValueLabel(mJSON, "content");
		this.comment = comment;
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		try {
			if (mJSON != null && mJSON.containsKey("im:rating")) {
				String rating = getValueLabel(mJSON, "im:rating");
				this.rating = Integer.parseInt(rating.substring(1,
						rating.length() - 1).trim());
			}
		} catch (Exception e) {
		}
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	private String getValueLabel(JSONObject object, String key) {
		if (object != null && object.containsKey(key)) {
			try {
				JSONObject jsonObject = (JSONObject) object.get(key);
				if (jsonObject.containsKey("label")) {
					String result = jsonObject.get("label").toString();
					return result;
				}
			} catch (Exception e) {
				ClientUtils.log("Exception:" + e.getMessage());
			}
		}
		return "";
	}

}
