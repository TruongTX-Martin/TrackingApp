package com.ks.trackingapp.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;
import com.ks.trackingapp.shared.model.UserInfo;

public interface DataServiceAsync {

	void userRegister(UserInfo userInfo, AsyncCallback<UserInfo> callback);

	void userLogin(String userName, String password,
			AsyncCallback<UserInfo> callback);

	void appAddNew(ItemApp itemApp, AsyncCallback<ItemApp> callback);

	void appGetAllItem(Long userId,AsyncCallback<ArrayList<ItemApp>> callback);

	void commentGetFromAppId(Long userId,Long appId,
			AsyncCallback<ArrayList<ItemComment>> callback);

	void commentFilterByTag(String language,Long userId,Long appId,String tag,String platform,
			AsyncCallback<ArrayList<ItemComment>> callback);

	void commentGetAppComment(Long userId,ItemApp itemApp, AsyncCallback<Void> callback);

	void appGetFromId(Long appId, AsyncCallback<ItemApp> callback);

	void getCommentAppWithTag(Long userId, Long appId, String language,String tag,
			AsyncCallback<ArrayList<ItemComment>> callback);

	void appUpdatePlatform(ItemApp itemApp, AsyncCallback<ItemApp> callback);

	void appDeleteItem(Long appId, AsyncCallback<Void> callback);


}
