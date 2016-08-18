package com.ks.trackingapp.shared;

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
	public static final String ITEMSCREEN_HOME = "Home Comment";
	
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
	
}
