package com.ks.trackingapp.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Config {
	public static final int PAGER_SIZE = 10;
	public static final String PLATFORM_IOS = "IOS";
	public static final String PLATFORM_ANDROID = "Android";
	public static final String PLATFORM_ALL = "All";
	public static final int PLATFORM_IOS_VALUE = 0;
	public static final int PLATFORM_ANDROID_VALUE = 1;
	public static final Long NULL_ID = -1L;
	
	
	//Item screen
	public static final String ITEMSCREEN_LOGIN = "Login";
	public static final String ITEMSCREEN_REGISTER = "Register";
	public static final String ITEMSCREEN_ALLAPP = "All App";
	public static final String ITEMSCREEN_APPCOMMENT = "App's Comment";
	public static final String ITEMSCREEN_ADDAPP = "Add New App";
	public static final String ITEMSCREEN_HOME = "Home";
	
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
	
	
	
	public static final String LANGUAGE_ENGLISH = "Enlish";
	public static final String LANGUAGE_FRENCH = "French";
	public static final String LANGUAGE_MALAY = "Malay";
	public static final String LANGUAGE_INDO = "Indonesia";
	public static final String LANGUAGE_PORTUGUESE = "Portuguese";
	public static final String LANGUAGE_BENGALI = "Bengali";
	public static final String LANGUAGE_ARABIC = "Arabic";
	public static final String LANGUAGE_RUSSIAN = "Russian";
	public static final String LANGUAGE_SPANISH = "Spanish";
	public static final String LANGUAGE_CHINESE = "Chinese";
	public static final String LANGUAGE_VIETNAMESE = "Vietnamese";
	
	//Config for language 
	public static Map<String, String> getLanguage(){
		Map<String, String> map = new HashMap<>();
		map.put("French", "fr");
		map.put("Malay", "ms");
		map.put("Indonesia", "id");
		map.put("Portuguese", "pt");
		map.put("Bengali", "bn");
		map.put("Arabic", "ar");
		map.put("Russian", "ru");
		map.put("Spanish", "es");
		map.put("Enlish", "en");
		map.put("Chinese", "zh");
		map.put("Vietnamese", "vi");
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
}
