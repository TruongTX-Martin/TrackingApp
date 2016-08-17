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

	void appGetAllItem(AsyncCallback<ArrayList<ItemApp>> callback);

	void commentGetFromAppId(Long appId,
			AsyncCallback<ArrayList<ItemComment>> callback);

	void commentFilterByPlatform(String platform,
			AsyncCallback<ArrayList<ItemComment>> callback);

	void commentGetAppComment(ItemApp itemApp, AsyncCallback<Void> callback);


}
