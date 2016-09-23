package com.ks.trackingapp.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Config {
	public static final int PAGER_SIZE = 10;
	public static final String PLATFORM_IOS = "IOS";
	public static final String PLATFORM_ANDROID = "Android";
	public static final int PLATFORM_IOS_VALUE = 0;
	public static final int PLATFORM_ANDROID_VALUE = 1;
	public static final Long NULL_ID = -1L;
	
	
	//Item screen
	public static final String ITEMSCREEN_LOGIN = "Login";
	public static final String ITEMSCREEN_REGISTER = "Register";
	public static final String ITEMSCREEN_ALLAPP = "All App";
	public static final String ITEMSCREEN_APPCOMMENT = "App's Comment";
	public static final String ITEMSCREEN_ADDAPP = "Add New App";
	public static final String ITEMSCREEN_HOME = "All Comment";
	
	//Tag for filter
	public static final String FILTERBY_PLATFORM = "Platform";
	public static final String FILTERBY_DATE = "Date";
	public static final String FILTERBY_RATE = "Rating";
	public static final String FILTERBY_ALL = "All";
	//login failt for reason
	public static final int USER_ACCOUNT_EXITS = 1;
	public static final int USER_ACCOUNT_EMAIL_EXITS = 2;
	public static final int USERT_ACCOUNT_PASSWORD_NOTCORRECT = 3;
	public static final int USER_ACCOUNT_NOTEXITS = 4;
	
	//add new appItem failed
	public static final int APPITEM_APPNAME_EXITS = 1;
	public static final int APPITEM_PACKAGENAME_EXITS = 2;
	public static final int APPITEM_APPLEID_EXITS = 3;
	
	
	
	public static final String LANGUAGE_1ENGLISH = "English";
	public static final String LANGUAGE_2FRENCH = "French";
	public static final String LANGUAGE_3MALAY = "Malay";
	public static final String LANGUAGE_4INDO = "Indonesia";
	public static final String LANGUAGE_5PORTUGUESE = "Portuguese";
	public static final String LANGUAGE_6GERMANY = "Germany";
	public static final String LANGUAGE_7SPANISH = "Spanish";
	public static final String LANGUAGE_8CHINESE = "Chinese";
	public static final String LANGUAGE_9VIETNAMESE = "Vietnamese";
	
	//Config for language 
	public static Map<String, String> getLanguage(){
		Map<String, String> map = new HashMap<>();
		map.put(LANGUAGE_2FRENCH, "fr");
		map.put(LANGUAGE_3MALAY, "ms");
		map.put(LANGUAGE_4INDO, "id");
		map.put(LANGUAGE_5PORTUGUESE, "pt");
		map.put(LANGUAGE_6GERMANY, "de");
		map.put(LANGUAGE_7SPANISH, "es");
		map.put(LANGUAGE_1ENGLISH, "en");
		map.put(LANGUAGE_8CHINESE, "zh");
		map.put(LANGUAGE_9VIETNAMESE, "vi");
		return map;
	}
	public static List<String> getListKeyLanguage(){
		ArrayList<String> listKey = new ArrayList<>();
		for(String key : getLanguage().keySet()) {
			listKey.add(key);
		}
		return listKey;
	}
	public static ArrayList<String> getValueLanguage(){
		ArrayList<String> listValue = new ArrayList<>();
		for(String key : getLanguage().keySet()) {
			listValue.add(getLanguage().get(key));
		}
		return listValue;
	}
	
	public static final String APP_HOST_DOMAIN = "https://test-dot-trackingapp-144102.appspot.com/";
	public static final String APP_ADMOB_DOMAIN = "https://test-dot-trackingapp-144102.appspot.com/";
	public static final String APP_TEST_DOMAIN = "https://test-dot-trackingapp-144102.appspot.com/";
}
