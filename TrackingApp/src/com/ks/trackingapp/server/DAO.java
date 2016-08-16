package com.ks.trackingapp.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.AndroidItem;
import com.ks.trackingapp.shared.model.AppItem;
import com.ks.trackingapp.shared.model.IOSItem;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.UserInfo;

public class DAO extends CustomRemoteServiceServlet {

	private static final long serialVersionUID = 1L;
	protected static final Logger log = Logger.getLogger(DAO.class.getName());
	private final String TAG_COMMENT = "class=\"review-body with-review-wrapper\">";
	private final String TAG_DATE = "class=\"review-info\"";
	private final String TAG_RATING = "class=\"tiny-star star-rating-non-editable-container\"";
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	static {
		ObjectifyService.register(IOSItem.class);
		ObjectifyService.register(AppItem.class);
		ObjectifyService.register(AndroidItem.class);
		ObjectifyService.register(UserInfo.class);
		ObjectifyService.register(ItemApp.class);
	}

	public DAO() {
	}

	public void getIOSComment(String appleId, String appName, String platform) {
		String result = "";
		String urlString = "https://itunes.apple.com/us/rss/customerreviews/id="
				+ appleId.trim() + "/sortBy=mostRecent/json";
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
			IOSItem iosItem = new IOSItem(result);
			iosItem.setPlatForm(platform);
			iosItem.setAppName(appName);
			putIosItemToDB(iosItem);
		} catch (Exception e) {

		}
	}

	protected void putIosItemToDB(IOSItem item) {
		ofy().save().entity(item).now();
	}
	
	protected void putAndroidItemToDB(AndroidItem item){
		ofy().save().entities(item).now();
	}

	protected void addNewItemApp(AppItem appItem) {
		ofy().save().entities(appItem).now();
	}

	protected ArrayList<AppItem> getAllAppItems() {
		ArrayList<AppItem> list = new ArrayList<AppItem>();
		List<AppItem> data = ofy().load().type(AppItem.class).list();
		list.addAll(data);
		return list;
	}

	protected ArrayList<AppItem> getListAppItem(int offset, int limit) {
		return new ArrayList<AppItem>((ofy().load().type(AppItem.class)
				.offset(offset).limit(limit)).list());
	}

	protected void deleteAppItem(AppItem appItem) {
		ofy().delete().entity(appItem);
	}

	protected ArrayList<IOSItem> getListCommentIOS(int offset, int limit) {
		return new ArrayList<IOSItem>((ofy().load().type(IOSItem.class)
				.offset(offset).limit(limit)).list());
	}
	protected ArrayList<AndroidItem> getListCommentAndroid(int offset,int limit) {
		return new ArrayList<AndroidItem>((ofy().load().type(AndroidItem.class).order("-date").offset(offset).limit(limit).list()));
	}

	protected void getCommentAndroidFromURl(String packageName,String platForm, String appName) {
		try {
			String url = "https://play.google.com/store/apps/details?id=" + packageName ;
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
			result = result.substring(result.indexOf("Đánh giá"),
					result.indexOf("Thông tin bổ sung"));
			if (result.contains("Bài đánh giá đầy đủ")) {
				String[] arrayDiv = result
						.split("Bài đánh giá đầy đủ");
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
							AndroidItem androidItem = new AndroidItem();
							androidItem.setAppName(appName);
							androidItem.setPlatForm(platForm);
							androidItem.setRating(getRatingFromString(rating));
							androidItem.setComment(comment);
							androidItem.setDate(formatDate.parse(getDateFromString(date)));
							putAndroidItemToDB(androidItem);
						}
						
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private int getRatingFromString(String ratingString) {
		int rating = 0;
		if(ratingString.contains("Được xếp hạng")) {
			String ra = ratingString.substring(ratingString.indexOf("hạng") +5, ratingString.indexOf("sao/"));
			rating = Integer.parseInt(ra.trim());
		}
		return rating;
	}
	private boolean validate(String input) {
		if(input.equals("null") || input == null || input.length() ==0){
			return false;
		}
		return true;
	}

	private String getDateFromString (String dateString) {
		String date = "";
		String day = dateString.substring(dateString.indexOf("Ngày")+4, dateString.indexOf("tháng")).trim();
		String month = dateString.substring(dateString.indexOf("tháng")+5, dateString.indexOf("năm")).trim();
		String year = dateString.substring(dateString.indexOf("năm")+3, dateString.length()).trim();
		date = day + "/" + month + "/" + year;
		return date;
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
	
	
	protected void appItemAddNew(ItemApp itemApp){
		ofy().save().entity(itemApp).now();
	}
	

}
