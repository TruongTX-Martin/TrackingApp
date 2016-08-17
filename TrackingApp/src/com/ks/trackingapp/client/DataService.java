package com.ks.trackingapp.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ks.trackingapp.shared.model.IBasic;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;
import com.ks.trackingapp.shared.model.UserInfo;

@RemoteServiceRelativePath("dataservice")
public interface DataService extends RemoteService{
	
	//new code
	UserInfo userRegister(UserInfo userInfo);
	UserInfo userLogin(String userName,String password);
	
	ItemApp appAddNew(ItemApp itemApp);
	ArrayList<ItemApp> appGetAllItem();

	void commentGetAppComment(ItemApp itemApp);
	ArrayList<ItemComment> commentGetFromAppId(Long appId);
	ArrayList<ItemComment> commentFilterByPlatform(String platform);
}
