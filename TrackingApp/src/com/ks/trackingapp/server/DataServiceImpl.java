package com.ks.trackingapp.server;

import java.util.ArrayList;
import java.util.List;

import com.ks.trackingapp.client.DataService;
import com.ks.trackingapp.shared.AndroidItem;
import com.ks.trackingapp.shared.AppItem;
import com.ks.trackingapp.shared.IOSItem;
import com.ks.trackingapp.shared.model.IBasic;

@SuppressWarnings("serial")
public class DataServiceImpl extends DAO implements
		DataService {


	@Override
	public void addNewItemApp(AppItem item) {
		super.addNewItemApp(item);
	}

	@Override
	public List<AppItem> getAllAppItem() {
		return super.getAllAppItems();
	}

	@Override
	public void deleteObject(IBasic basic) {
		if(basic instanceof AppItem) {
			super.deleteAppItem((AppItem)basic);
		}
	}

	@Override
	public void getIOSComment(String appleId,String platform,String appname) {
		super.getIOSComment(appleId, appname, platform);
	}

	@Override
	public ArrayList<AppItem> getListAppItem(int offset, int limit) {
		return super.getListAppItem(offset, limit);
	}

	@Override
	public ArrayList<IOSItem> getListCommentIOS(int offset, int limit) {
		return super.getListCommentIOS(offset, limit);
	}

	@Override
	public void getCommentAndroid(String url,String platform, String appname) {
		 super.getCommentAndroidFromURl(url,platform,appname);
	}

	@Override
	public ArrayList<AndroidItem> getListCommentAndroid(int offset, int limit) {
		return super.getListCommentAndroid(offset, limit);
	}

}
