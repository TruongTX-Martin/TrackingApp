package com.ks.trackingapp.server;

import java.util.ArrayList;

import com.ks.trackingapp.client.DataService;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;
import com.ks.trackingapp.shared.model.UserInfo;

@SuppressWarnings("serial")
public class DataServiceImpl extends DAO implements
		DataService {




	@Override
	public UserInfo userRegister(UserInfo userInfo) {
		return super.registerUser(userInfo);
	}

	@Override
	public UserInfo userLogin(String userName, String password) {
		return super.userLogin(userName, password);
	}

	@Override
	public ItemApp appAddNew(ItemApp itemApp) {
		return super.appItemAddNew(itemApp);
	}

	@Override
	public ArrayList<ItemApp> appGetAllItem(Long userId) {
		return super.appGetAllItem(userId);
	}

	@Override
	public ArrayList<ItemComment> commentGetFromAppId(Long userId,Long appId) {
		return super.commentGetFromAppId(userId,appId);
	}

	@Override
	public ArrayList<ItemComment> commentFilterByTag(String language,Long userId,Long appId,String tag,String platform) {
		return super.commentFilterByTag(language,userId,appId,tag,platform);
	}

	@Override
	public void commentGetAppComment(Long userId,ItemApp itemApp) {
		super.getCommentApp(userId,itemApp);
	}

	@Override
	public ItemApp appGetFromId(Long appId) {
		return super.appGetFromAppId(appId);
	}

}
