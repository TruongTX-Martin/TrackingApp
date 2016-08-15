package com.ks.trackingapp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ks.trackingapp.shared.AndroidItem;
import com.ks.trackingapp.shared.AppItem;
import com.ks.trackingapp.shared.IOSItem;
import com.ks.trackingapp.shared.model.IBasic;

@RemoteServiceRelativePath("dataservice")
public interface DataService extends RemoteService{

	void addNewItemApp(AppItem item);
	List<AppItem> getAllAppItem();
	void deleteObject(IBasic basic);
	void getIOSComment(String appleId,String platform,String appname);
	ArrayList<IOSItem> getListCommentIOS(int offset, int limit);
	ArrayList<AppItem> getListAppItem(int offset,int limit);
	ArrayList<AndroidItem> getListCommentAndroid(int offset, int limit);
	void getCommentAndroid(String url,String platform, String appname);
}
