package com.ks.trackingapp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ks.trackingapp.shared.AndroidItem;
import com.ks.trackingapp.shared.AppItem;
import com.ks.trackingapp.shared.IOSItem;
import com.ks.trackingapp.shared.model.IBasic;

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


}
