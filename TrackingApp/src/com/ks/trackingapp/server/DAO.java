package com.ks.trackingapp.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.thirdparty.json.JSONArray;
import com.google.gwt.thirdparty.json.JSONObject;
import com.googlecode.objectify.ObjectifyService;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;
import com.ks.trackingapp.shared.model.UserInfo;

public class DAO extends CustomRemoteServiceServlet {

	private static final long serialVersionUID = 1L;
	protected static final Logger log = Logger.getLogger(DAO.class.getName());
	private final String TAG_COMMENT = "class=\"review-body with-review-wrapper\">";
	private final String TAG_DATE = "class=\"review-info\"";
	private final String TAG_RATING = "class=\"tiny-star star-rating-non-editable-container\"";
	
	
//	private final String TAG_START = "class=\"review-row-header heading\"";
//	private final String TAG_START = "<h1 class=\"details-scroll-anchor\" id=\"details-reviews\"></h1> <h1 class=\"heading\">";
//	private final String TAG_END = "class=\"details-section-heading\"> <h1 class=\"heading\">";
//	private final String TAG_CONTAINS = "<a class=\"id-no-nav play-button tiny\" href=\"#\" target=\"_blank\">";
	
	private final String TAG_START = "<div class=\"details-section-body expandable\" data-load";
	private final String TAG_END = "<div class=\"details-section metadata\">";
	private final String TAG_CONTAINT = "<a class=\"id-no-nav play-button tiny\" href=\"#\" target=\"_blank\">";
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formatter = new  SimpleDateFormat("dd/MM/yyyy");
	static {
		ObjectifyService.register(UserInfo.class);
		ObjectifyService.register(ItemApp.class);
		ObjectifyService.register(ItemComment.class);
		
	}

	public DAO() {
	}

	private boolean validate(String input) {
		if(input.equals("null") || input == null || input.length() ==0){
			return false;
		}
		return true;
	}

	protected UserInfo registerUser(UserInfo userInfo){
		UserInfo userResult = ofy().load().type(UserInfo.class).filter("userName", userInfo.getUserName()).first().now();
		if(userResult != null) {
			userResult.setIsSuccess(false);
			userResult.setLoginFailtReason(Config.USER_ACCOUNT_EXITS);
			return userResult;
		}
		UserInfo userEmail = ofy().load().type(UserInfo.class).filter("userEmail", userInfo.getUserEmail()).first().now();
		if(userEmail != null) {
			userEmail.setIsSuccess(false);
			userEmail.setLoginFailtReason(Config.USER_ACCOUNT_EMAIL_EXITS );
			return userEmail;
		}
		saveUserInfo(userInfo);
		userInfo.setIsSuccess(true);
		return userInfo;
	}
	private void saveUserInfo(UserInfo userInfo){
		ofy().save().entity(userInfo).now();
	}
	protected UserInfo userLogin(String userName,String password){
		UserInfo userResult = ofy().load().type(UserInfo.class).filter("userName", userName).first().now();
		if(userResult == null){
			UserInfo info = new UserInfo();
			info.setIsSuccess(false);
			info.setLoginFailtReason(Config.USER_ACCOUNT_NOTEXITS);
			return info;
		}
		if(!userResult.getPassword().equals(password)){
			userResult.setIsSuccess(false);
			userResult.setLoginFailtReason(Config.USERT_ACCOUNT_PASSWORD_NOTCORRECT);
			return userResult;
		}
		userResult.setIsSuccess(true);
		return userResult;
	}
	
	
	protected ItemApp appItemAddNew(ItemApp itemApp){
		ItemApp appName = ofy().load().type(ItemApp.class).filter("appName", itemApp.getAppName()).first().now();
		if(appName != null) {
			appName.setIsSuccess(false);
			appName.setAddFailedForReason(Config.APPITEM_APPNAME_EXITS);
			return appName;
		}
		if(itemApp.isAndroid()) {
			ItemApp appAndroid = ofy().load().type(ItemApp.class).filter("packageName", itemApp.getPackageName()).first().now();
			if(appAndroid != null){
				appAndroid.setIsSuccess(false);
				appAndroid.setAddFailedForReason(Config.APPITEM_PACKAGENAME_EXITS);
				return appAndroid;
			}
		}
		if(itemApp.isIOS()){
			ItemApp appIOS = ofy().load().type(ItemApp.class).filter("appleId", itemApp.getAppleId()).first().now();
			if(appIOS != null){
				appIOS.setIsSuccess(false);
				appIOS.setAddFailedForReason(Config.APPITEM_APPLEID_EXITS);
				return appIOS;
			}
		}
		if(itemApp.isAndroid()){
			String url = "https://play.google.com/store/apps/details?id=" + itemApp.getPackageName() ;
			float rating = getRating(url);
			itemApp.setRating(rating);
		}
		ofy().save().entity(itemApp).now();
		itemApp.setIsSuccess(true);
		
		return itemApp;
	}
	private float getRating(String url) {
		float rating = 0.0f;
		try {
			URL website = new URL(url);
			URLConnection connection = website.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			StringBuilder response = new StringBuilder();
			String inputLine;
			String result = "";
			while ((inputLine = in.readLine()) != null) {
				result += inputLine.toString() + "\n";
			}
			in.close();
			result = result.substring(result.indexOf(TAG_START),
					result.indexOf(TAG_END));
			if(result.contains("<div")){
				String[] array = result.split("<div");
				for (int i=0; i< array.length; i++) {
					String item = array[i];
					if(item.contains("class=\"score\"")) {
						String rateString = item.substring(item.indexOf(">")+1, item.length()-7).trim();
						rating = Float.valueOf(rateString.replace(",", "."));
					}
				}
			}
		} catch (Exception e) {
		}
		return rating;
	}
	protected void getCommentApp(Long userId,ItemApp itemApp){
		if(itemApp.isAndroid()){
			//get comment android
			for(int i=0; i< Config.getValueLanguage().size(); i++){
				getCommentAndroid(userId,itemApp,Config.getValueLanguage().get(i));
			}
		}
		if(itemApp.isIOS()){
			//get comment ios
			getCommentIOS(userId,itemApp);
		}
	}
	protected ItemApp appGetFromAppId(Long appId){
		return ofy().load().type(ItemApp.class).filterKey(appId).first().now();
	}
	private void getCommentAndroid(Long userId,ItemApp itemApp,String language){
		try {
			String url = "https://play.google.com/store/apps/details?id=" + itemApp.getPackageName()+"&hl=" + language ;
			URL website = new URL(url);
			URLConnection connection = website.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			StringBuilder response = new StringBuilder();
			String inputLine;
			String result = "";
			while ((inputLine = in.readLine()) != null) {
				result += inputLine.toString() + "\n";
			}
			in.close();
			result = result.substring(result.indexOf(TAG_START),
					result.indexOf(TAG_END));
			if (result.contains(TAG_CONTAINT)) {
				String[] arrayDiv = result
						.split(TAG_CONTAINT);
				if(arrayDiv.length > 0){
					for (int a =0; a < arrayDiv.length; a++) {
						String item = arrayDiv[a];
						String rating = "",comment = "",date = "";
						if (item.contains("<div") ) {
							String[] arrayItem = item.split("<div");
							for (int i = 0; i < arrayItem.length; i++) {
								String classEntity = arrayItem[i];
								if (classEntity.contains(TAG_DATE)) {
									date = classEntity.substring(classEntity.indexOf("<span class=\"review-date\">")+26, classEntity.indexOf("</span> <a class=\"reviews-permalink\""));
								}
								if (classEntity.contains(TAG_RATING)) {
									rating = classEntity.substring(classEntity.indexOf("aria-label=\"")+12, classEntity.length()-2);
								}
								if (classEntity.contains(TAG_COMMENT)) {
									String title = classEntity.substring(classEntity.indexOf("<span class=\"review-title\">") +27, classEntity.indexOf("</span>"));
									String content  = classEntity.substring(classEntity.indexOf("</span>")+7,classEntity.length());
									if(validate(title)) {
										comment = title + "-" + content;
									}else{
										comment = content;
									}
								}
							}
						}
						
						if(validate(rating) && validate(comment) && validate(date)){
							ItemComment itemComment = new ItemComment();
							itemComment.setPlatform(Config.PLATFORM_ANDROID);
							itemComment.setAppname(itemApp.getAppName());
							itemComment.setAppId(itemApp.getId());
							itemComment.setRating(getRatingFromString(rating));
							itemComment.setComment(comment.trim());
							itemComment.setDate(date);
							itemComment.setUserId(userId);
							itemComment.setLanguage(language);
							saveItemComment(itemComment);
						}
						
					}
				}
			}

		} catch (Exception e) {
			log.warning("Exception while get comment android" + e.getMessage());
		}
	}
	
	private int getRatingFromString(String rating){	
		String rate = "";
		if(rating.length() > 0){
			for (int i=0; i< rating.length() ; i++) {
				if(isLeadingDigit(rating.charAt(i))){
					rate += rating.charAt(i);
				}
			}
		}
		return Integer.parseInt(rate.trim().charAt(0)+"");
	}
	public boolean isLeadingDigit(char c){
	    return (c >= '0' && c <= '9');
	}
	
	private void getCommentIOS(Long userId,ItemApp itemApp){
		String result = "";
		String urlString = "https://itunes.apple.com/us/rss/customerreviews/id="
				+ itemApp.getAppleId() + "/sortBy=mostRecent/json";
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1) {
				buffer.append(chars, 0, read);
			}
			result = buffer.toString();
			getItemCommentFromJson(userId,result,itemApp);
		} catch (Exception e) {
			log.warning("Exception while get comment ios" + e.getMessage());
		}
	}
	
	private void getItemCommentFromJson(Long userId,String jsonString,ItemApp itemApp){
		try {
			JSONObject object = new JSONObject(jsonString);
			if(object.has("feed")) {
				JSONObject totalObject = object.getJSONObject("feed");
				if(totalObject.has("entry")) {
					JSONArray array = totalObject.getJSONArray("entry");
					if(array.length() > 0) {
						for(int i=0; i < array.length(); i++){
							JSONObject jsonObject = array.getJSONObject(i);
							ItemComment itemComment = new ItemComment();
							itemComment.setDate(getDate(jsonObject));
							if(validate(getTitle(jsonObject))){
								itemComment.setComment(getTitle(jsonObject) + "-"+getComment(jsonObject));
							}else{
								itemComment.setComment(getComment(jsonObject));
							}
							itemComment.setRating(getRating(jsonObject));
							itemComment.setPlatform(Config.PLATFORM_IOS);
							itemComment.setAppname(itemApp.getAppName());
							itemComment.setAppId(itemApp.getId());
							itemComment.setUserId(userId);
							saveItemComment(itemComment);
						}
					}
				}
			}
		} catch (Exception e) {
			log.warning("Exception while getcomment iso :"+ e.getMessage() );
		}
	}
	public int getRating(JSONObject mJSON) {
		int rate = 0;
		try {
			if (mJSON != null && mJSON.has("im:rating")) {
				String rating = getValueLabel(mJSON, "im:rating");
				rate = Integer.parseInt(rating.trim());
			}
		} catch (Exception e) {
		}
		return rate;
	}
	public String getTitle(JSONObject mJSON) {
		String title = "";
		if (mJSON != null && mJSON.has("title")) {
			title = getValueLabel(mJSON, "title");
		}
		return title;
	}
	public String getDate(JSONObject mJSON){
		Date date = new Date();
		if (mJSON.has("im:releaseDate")) {
			String dateString = getValueLabel(mJSON, "im:releaseDate");
			try {
				date = formatter.parse(dateString);
			} catch (Exception e) {
			}
		}
		return formatter.format(date);
	}
	public String getComment(JSONObject mJSON) {
		String comment = getValueLabel(mJSON, "content");
		return comment;
	}
	private String getValueLabel(JSONObject object, String key) {
		if (object != null && object.has(key)) {
			try {
				JSONObject jsonObject = (JSONObject) object.get(key);
				if (jsonObject.has("label")) {
					String result = jsonObject.get("label").toString();
					return result;
				}
			} catch (Exception e) {
				ClientUtils.log("Exception:" + e.getMessage());
			}
		}
		return "";
	}
	
	private void saveItemComment(ItemComment comment){
		ItemComment itemComment = ofy().load().type(ItemComment.class).filter("appname", comment.getAppname()).filter("comment", comment.getComment()).first().now();
		if(itemComment != null){
			return;
		}
		ofy().save().entity(comment).now();
	}
	
	protected ArrayList<ItemApp> appGetAllItem(Long userId){
		return new ArrayList<>(ofy().load().type(ItemApp.class).filter("userId", userId).list());
	}
	
	protected ArrayList<ItemComment> commentGetFromAppId(Long userId,Long appId){
		return new ArrayList<>(ofy().load().type(ItemComment.class).filter("appId", appId).filter("language", "en").list());
	}
	protected ArrayList<ItemComment> commentFilterByTag(String language,Long userId,Long appId,String tag,String platform){
		if(appId != -1){
			if(tag.equals(Config.FILTERBY_ALL)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("appId", appId).filter("language", language).list());
			}else if(tag.equals(Config.FILTERBY_PLATFORM)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("appId", appId).filter("platform", platform).filter("language", language).list());
			}else if(tag.equals(Config.FILTERBY_DATE)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("appId", appId).order("-date").filter("language", language).list());
			}else if(tag.equals(Config.FILTERBY_RATE)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("appId", appId).order("-rating").filter("language", language).list());
			}
		}else{
			if(tag.equals(Config.FILTERBY_ALL)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("language", language).list());
			}else if(tag.equals(Config.FILTERBY_PLATFORM)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("platform", platform).filter("language", language).list());
			}else if(tag.equals(Config.FILTERBY_DATE)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).order("-date").filter("language", language).list());
			}else if(tag.equals(Config.FILTERBY_RATE)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).order("-rating").filter("language", language).list());
			}
		}
		return new ArrayList<>(ofy().load().type(ItemComment.class).list()); 
	}
		
}
