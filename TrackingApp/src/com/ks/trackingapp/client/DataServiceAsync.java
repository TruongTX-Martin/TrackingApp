package com.ks.trackingapp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ks.trackingapp.shared.model.AndroidItem;
import com.ks.trackingapp.shared.model.AppItem;
import com.ks.trackingapp.shared.model.IBasic;
import com.ks.trackingapp.shared.model.IOSItem;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;
import com.ks.trackingapp.shared.model.UserInfo;

public interface DataServiceAsync {


	void addNewItemApp(AppItem item,AsyncCallback<Void> callback);

	void getAllAppItem(AsyncCallback<List<AppItem>> callback);

	void deleteObject(IBasic basic, AsyncCallback<Void> callback);

	void getIOSComment(String appleId,String platform,String appname, AsyncCallback<Void> callback);

	void getListAppItem(int offset, int limit,
			AsyncCallback<ArrayList<AppItem>> callback);

	void getListCommentIOS(int offset, int limit,
			AsyncCallback<ArrayList<IOSItem>> callback);

	void getCommentAndroid(String url,String platform, String appname, AsyncCallback<Void> callback);

	void getListCommentAndroid(int offset, int limit,
			AsyncCallback<ArrayList<AndroidItem>> callback);

	void userRegister(UserInfo userInfo, AsyncCallback<UserInfo> callback);

	void userLogin(String userName, String password,
			AsyncCallback<UserInfo> callback);

	void appAddNew(ItemApp itemApp, AsyncCallback<ItemApp> callback);

	void appGetAllItem(AsyncCallback<ArrayList<ItemApp>> callback);

	void commentGetFromAppId(Long appId,
			AsyncCallback<ArrayList<ItemComment>> callback);

	void commentFilterByPlatform(String platform,
			AsyncCallback<ArrayList<ItemComment>> callback);

	void commentGetAppComment(ItemApp itemApp, AsyncCallback<Void> callback);


}
