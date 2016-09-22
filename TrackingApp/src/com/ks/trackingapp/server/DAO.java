package com.ks.trackingapp.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gwt.thirdparty.json.JSONArray;
import com.google.gwt.thirdparty.json.JSONObject;
import com.googlecode.objectify.Key;
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
	
	
	
	private final String TAG_START = "<div class=\"details-section-body expandable\" data-load";
	private final String TAG_END = "<div class=\"details-section metadata\">";
	private final String TAG_CONTAINT = "<a class=\"id-no-nav play-button tiny\" href=\"#\" target=\"_blank\">";
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formatter = new  SimpleDateFormat("dd/MM/yyyy");
	DateFormat dateFormatEnglish = new SimpleDateFormat("MMMM dd, yyyy");
	DateFormat dateFormatVietnamese = new SimpleDateFormat("dd-MM-yyyy");
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
	protected ArrayList<UserInfo> getAllUser(){
		return new ArrayList<>(ofy().load().type(UserInfo.class).list());
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
		float rate = 0.0f;
		try {
			Document doc = Jsoup.connect(url).get();
			String rating = doc.select("div[class=score]").get(0).attr("aria-label");
			rate = getRatingFromString(rating);
//			URL website = new URL(url);
//			URLConnection connection = website.openConnection();
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					connection.getInputStream()));
//
//			StringBuilder response = new StringBuilder();
//			String inputLine;
//			String result = "";
//			while ((inputLine = in.readLine()) != null) {
//				result += inputLine.toString() + "\n";
//			}
//			in.close();
//			result = result.substring(result.indexOf("itemtype=\"http://schema.org/AggregateRating\">"),
//					result.indexOf(TAG_END));
//			if(result.contains("<div")){
//				String[] array = result.split("<div");
//				for (int i=0; i< array.length; i++) {
//					String item = array[i];
//					if(item.contains("class=\"score\"")) {
//						String rateString = item.substring(item.indexOf(">")+1, item.length()-7).trim();
//						rating = Float.valueOf(rateString.replace(",", "."));
//					}
//				}
//			}
		} catch (Exception e) {
		}
		return rate;
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
		return ofy().load().type(ItemApp.class).id(appId).now();
	}
	private List<ItemComment> getCommentAndroid(Long userId,ItemApp itemApp,String language){
		List<ItemComment> list = new ArrayList<ItemComment>();
		try {
			String url = "https://play.google.com/store/apps/details?id=" + itemApp.getPackageName()+"&hl=" + language ;
			Document doc = Jsoup.connect(url).get();
			Elements items = doc.select("div[class=single-review]");
			for(Element itemElement : items) {
				Elements header = itemElement.select("div[class=review-header]");
				
				
				Element reviewInfo = header.select("div[class=review-info]").get(0);
				String date = reviewInfo.select("span[class=review-date]").text();
				Element rateElement = reviewInfo.select("div[class=review-info-star-rating]").get(0);
				String rate = rateElement.select("div[class=tiny-star star-rating-non-editable-container]").attr("aria-label");
				
				Elements body = itemElement.select("div[class=review-body with-review-wrapper]");
				String comment = body.select("span[class=review-title]").text();
				String avatarElement = itemElement.select("span[class=responsive-img author-image]").select("span[class=responsive-img author-image]").toString();
				String userName = itemElement.select("span[class=author-name]").text();
				String avatar = avatarElement.substring(avatarElement.lastIndexOf("url(")+4, avatarElement.length() -10);
				if(validate(rate) && validate(comment) && validate(date)){
					ItemComment itemComment = new ItemComment();
					itemComment.setPlatform(Config.PLATFORM_ANDROID);
					itemComment.setAppname(itemApp.getAppName());
					itemComment.setAppId(itemApp.getId());
					itemComment.setRating(getRatingFromString(rate));
					itemComment.setComment(comment.trim());
					itemComment.setDate(convertStringToDate(date, language));
					itemComment.setUserId(userId);
					itemComment.setLanguage(language);
					itemComment.setUserName(userName);
					itemComment.setAvatar(avatar);
					list.add(itemComment);
					saveItemComment(itemComment);
				}
			}

		} catch (Exception e) {
			log.warning("Exception while get comment android" + e.getMessage());
		}
		return list;
	}
	private Date convertStringToDate(String date,String locale){
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, new Locale(locale));
		try {
			Date formattedDate = df.parse(date);
			System.out.println("Date====>"+formattedDate);
			return formattedDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	private Date getDateFromStringVietNamese(String input){
		String day = input.substring(input.indexOf("Ngày")+4,input.indexOf("tháng"));
		String month = input.substring(input.indexOf("tháng") +5,input.indexOf("năm"));
		String year = input.substring(input.indexOf("năm")+3,input.length());
		System.out.println("Day:" + day);
		System.out.println("Month:" + month);
		System.out.println("Year:" + year);
		String date = day.trim() + "-" + month.trim() + "-" + year.trim();
		Date date2 = null;
		try {
			date2  = dateFormatVietnamese.parse(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return date2;
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
	
	private List<ItemComment> getCommentIOS(Long userId,ItemApp itemApp){
		List<ItemComment> list = new ArrayList<ItemComment>();
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
			list.addAll(getItemCommentFromJson(userId,result,itemApp));
		} catch (Exception e) {
			log.warning("Exception while get comment ios" + e.getMessage());
		}
		return list;
	}
	
	private List<ItemComment> getItemCommentFromJson(Long userId,String jsonString,ItemApp itemApp){
		List<ItemComment> list = new ArrayList<ItemComment>();
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
							itemComment.setIdComment(getIdComment(jsonObject));
							itemComment.setRating(getRating(jsonObject));
							itemComment.setPlatform(Config.PLATFORM_IOS);
							itemComment.setAppname(itemApp.getAppName());
							itemComment.setAppId(itemApp.getId());
							itemComment.setUserId(userId);
							itemComment.setLanguage("en");
							list.add(itemComment);
							saveItemComment(itemComment);
						}
					}
				}
			}
		} catch (Exception e) {
			log.warning("Exception while getcomment iso :"+ e.getMessage() );
		}
		return list;
	}
	public String getIdComment (JSONObject mJSON) {
		String idComment = "";
		try {
			idComment = getValueLabel(mJSON, "id").trim();
		} catch (Exception e) {
		}
		return idComment;
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
	public Date getDate(JSONObject mJSON){
		Date date = new Date();
		if (mJSON.has("im:releaseDate")) {
			String dateString = getValueLabel(mJSON, "im:releaseDate");
			try {
				date = formatter.parse(dateString);
			} catch (Exception e) {
			}
		}
		return date;
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
		if(comment.getPlatform().equals(Config.PLATFORM_ANDROID)) {
			List<ItemComment> itemComment = ofy().load().type(ItemComment.class).filter("appname", comment.getAppname()).filter("comment", comment.getComment()).filter("date", comment.getDate()).list();
			if(itemComment.size() > 0) {
				return;
			}
		}
		if(comment.getPlatform().equals(Config.PLATFORM_IOS)) {
			List<ItemComment> itemComment = ofy().load().type(ItemComment.class).filter("idComment", comment.getIdComment()).list();
			if(itemComment.size() > 0){
				return;
			}
		}
//		ItemComment itemComment = ofy().load().type(ItemComment.class).filter("appname", comment.getAppname()).filter("comment", comment.getComment()).first().now();
//		if(itemComment != null){
//			return;
//		}
		ofy().save().entity(comment).now();
	}
	
	protected ArrayList<ItemApp> appGetAllItem(Long userId){
		return new ArrayList<>(ofy().load().type(ItemApp.class).filter("userId", userId).list());
	}
	protected ArrayList<ItemApp> appGetAllItem(){
		return new ArrayList<>(ofy().load().type(ItemApp.class).list());
	}
	
	protected ArrayList<ItemComment> commentGetFromAppId(Long userId,Long appId){
		return new ArrayList<>(ofy().load().type(ItemComment.class).filter("appId", appId).filter("language", "en").list());
	}
	protected ArrayList<ItemComment> commentFilterByTag(String language,Long userId,Long appId,String tag,String platform){
		if(appId != -1){
			if(tag.equals(Config.FILTERBY_ALL)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("appId", appId).filter("language", language).order("-date").list());
			}else if(tag.equals(Config.FILTERBY_PLATFORM)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("appId", appId).filter("platform", platform).filter("language", language).order("-date").list());
			}else if(tag.equals(Config.FILTERBY_DATE)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("appId", appId).order("-date").filter("language", language).list());
			}else if(tag.equals(Config.FILTERBY_RATE)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("appId", appId).order("-rating").filter("language", language).list());
			}
		}else{
			if(tag.equals(Config.FILTERBY_ALL)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("language", language).order("-date").list());
			}else if(tag.equals(Config.FILTERBY_PLATFORM)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).filter("platform", platform).filter("language", language).order("-date").list());
			}else if(tag.equals(Config.FILTERBY_DATE)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).order("-date").filter("language", language).list());
				
			}else if(tag.equals(Config.FILTERBY_RATE)){
				return new ArrayList<>(ofy().load().type(ItemComment.class).filter("userId", userId).order("-rating").filter("language", language).list());
			}
		}
		return new ArrayList<>(ofy().load().type(ItemComment.class).order("-date").list()); 
	}
	
	protected ArrayList<ItemComment> getCommentAppByTag(Long userId,Long appId,String language,String tag){
		if(tag.equals(Config.PLATFORM_ANDROID)){
			return new ArrayList<>(ofy().load().type(ItemComment.class).filter("appId", appId).filter("language", language).filter("platform",tag).order("-date").list());
		}else if(tag.equals(Config.PLATFORM_IOS)){
			return new ArrayList<>(ofy().load().type(ItemComment.class).filter("appId", appId).filter("platform",tag).order("-date").list());
		}else if(tag.equals(Config.FILTERBY_DATE)){
			return new ArrayList<>(ofy().load().type(ItemComment.class).filter("appId", appId).filter("language", language).order("-date").list());
		}else if(tag.equals(Config.FILTERBY_RATE)){
			return new ArrayList<>(ofy().load().type(ItemComment.class).filter("appId", appId).filter("language", language).order("-rating").list());
		}else{
			return new ArrayList<>(ofy().load().type(ItemComment.class).filter("appId", appId).filter("language", language).order("-date").list());
		}
	}
	
	protected ItemApp appItemUpdate(ItemApp itemApp){
		if(itemApp.isAndroid()){
			String url = "https://play.google.com/store/apps/details?id=" + itemApp.getPackageName() ;
			float rating = getRating(url);
			itemApp.setRating(rating);
		}
		itemApp.setIsSuccess(true);
		ofy().save().entity(itemApp).now();
		return itemApp;
	}
	protected void appItemDelete(Long appItem){
		ofy().delete().type(ItemApp.class).id(appItem).now();
		ArrayList<ItemComment> arrayComment = new ArrayList<>(ofy().load().type(ItemComment.class).filter("appId", appItem).list());
		if(arrayComment.size() > 0){
			for(int i=0; i< arrayComment.size(); i++) {
				ItemComment comment = arrayComment.get(i);
				ofy().delete().type(ItemComment.class).id(comment.getId()).now();
			}
		}
	}
	protected void commentDeleteAll(){
		List<Key<ItemComment>> keys = ofy().load().type(ItemComment.class).keys().list();
		ofy().delete().keys(keys).now();
	}
	
	protected void appItemDeleteAll(){
		List<Key<ItemApp>> keys = ofy().load().type(ItemApp.class).keys().list();
		ofy().delete().keys(keys).now();
	}
	protected void commentDeleteByAppId(Long appId){
		List<ItemComment> keys = ofy().load().type(ItemComment.class).filter("appId", appId).list();
		if(keys.size() > 0){
			for (ItemComment comment : keys) {
				ofy().delete().entity(comment).now();
			}
		}
	}
	public ArrayList<ItemComment> getCommentRateAndPlatform(String platfrom,int rate,int offset,int limit){
		if(platfrom.contains("all")){
			return new ArrayList<ItemComment>(ofy().load().type(ItemComment.class).filter("rating", rate).offset(offset).limit(limit).order("-date").list());
		}
		return new ArrayList<ItemComment>(ofy().load().type(ItemComment.class).filter("platform", platfrom).filter("rating", rate).offset(offset).limit(limit).order("-date").list());
	}
	public ArrayList<ItemComment> getCommentFromPlatform(Long appId,String platform,int rate,int offset,int limit){
		if(platform.contains("all")){
			return new ArrayList<ItemComment>(ofy().load().type(ItemComment.class).filter("appId", appId).filter("rating", rate).offset(offset).limit(limit).order("-date").list());
		}
		return new ArrayList<ItemComment>(ofy().load().type(ItemComment.class).filter("appId", appId).filter("platform", platform).filter("rating", rate).offset(offset).limit(limit).order("-date").list());
	}
	public ArrayList<ItemComment> getCommentByLangauge(String language){
		return new ArrayList<ItemComment>(ofy().load().type(ItemComment.class).filter("language", language).list());
	}
	protected List<ItemComment> getCommentNewsts(Long userId,ItemApp itemApp){
		ArrayList<ItemComment> list = new ArrayList<ItemComment>();
		if(itemApp.isAndroid()){
			//get comment android
			for(int i=0; i< Config.getValueLanguage().size(); i++){
				list.addAll(getCommentAndroid(userId,itemApp,Config.getValueLanguage().get(i)));
			}
		}
		if(itemApp.isIOS()){
			//get comment ios
			list.addAll(getCommentIOS(userId,itemApp));
		}
		return list;
	}
}
