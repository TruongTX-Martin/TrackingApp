package com.ks.trackingapp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ks.trackingapp.shared.model.AndroidItem;
import com.ks.trackingapp.shared.model.AppItem;
import com.ks.trackingapp.shared.model.IBasic;
import com.ks.trackingapp.shared.model.IOSItem;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.UserInfo;

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
	
	
	
	//new code
	UserInfo userRegister(UserInfo userInfo);
	UserInfo userLogin(String userName,String password);
	void appAddNew(ItemApp itemApp);
}
