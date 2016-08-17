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
	public ArrayList<ItemApp> appGetAllItem() {
		return super.appGetAllItem();
	}

	@Override
	public ArrayList<ItemComment> commentGetFromAppId(Long appId) {
		return super.commentGetFromAppId(appId);
	}

	@Override
	public ArrayList<ItemComment> commentFilterByPlatform(String platform) {
		return super.commentFilterByFlatform(platform);
	}

	@Override
	public void commentGetAppComment(ItemApp itemApp) {
		super.getCommentApp(itemApp);
	}

}
