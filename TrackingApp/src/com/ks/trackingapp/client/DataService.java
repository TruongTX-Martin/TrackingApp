package com.ks.trackingapp.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;
import com.ks.trackingapp.shared.model.UserInfo;

@RemoteServiceRelativePath("dataservice")
public interface DataService extends RemoteService{
	
	//new code
	UserInfo userRegister(UserInfo userInfo);
	UserInfo userLogin(String userName,String password);
	
	ItemApp appAddNew(ItemApp itemApp);
	ItemApp appUpdatePlatform(ItemApp itemApp);
	ArrayList<ItemApp> appGetAllItem(Long userId);
	ItemApp appGetFromId(Long appId);
	void appDeleteItem(Long appId);

	void commentGetAppComment(Long userId,ItemApp itemApp);
	ArrayList<ItemComment> commentGetFromAppId(Long userId,Long appId);
	ArrayList<ItemComment> commentFilterByTag(String language,Long userId,Long appId,String tag,String platform);
	ArrayList<ItemComment> getCommentAppWithTag(Long userId, Long appId,String language,String tag);
}
