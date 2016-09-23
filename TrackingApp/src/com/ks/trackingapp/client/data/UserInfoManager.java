package com.ks.trackingapp.client.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.sqlite.MyBean;
import com.ks.trackingapp.client.sqlite.SQLiteDatabaseHelper;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.shared.interfacemodel.IBasic;
import com.ks.trackingapp.shared.interfacemodel.IUserInfo;
import com.ks.trackingapp.shared.model.UserInfo;

public class UserInfoManager extends DataManager{
	
	public UserInfoManager() {
		databaseName = SQLiteDatabaseHelper.UserDatabase;
	}
	
	public void insertData(final UserInfo userInfo) {
		deleteUserInfo(null, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				UserInfoManager.super.insertData(databaseName, SQLiteDatabaseHelper.UserInfoTable, Arrays.asList(userInfo));
			}

			@Override
			public void onSuccess(Void result) {
				UserInfoManager.super.insertData(databaseName, SQLiteDatabaseHelper.UserInfoTable, Arrays.asList(userInfo));
			}
		});
	}
	
	public void getData(final AsyncCallback<UserInfo> callback){
		if(!TrackingApp.phoneGap.isPhoneGapDevice()) {
			callback.onFailure(null);
			return;
		}
		String select = "*";
		TrackingApp.sqLiteDatabaseHelper.selectTable(this.databaseName, SQLiteDatabaseHelper.UserInfoTable,select, "",new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientUtils.log("Error in sql query: " + caught.toString());
			}
			@Override
			public void onSuccess(JavaScriptObject response) {
				String res = SQLiteDatabaseHelper.getResponseString(response);
				ClientUtils.log("SQLResponse: " + res);
				final List<IBasic> userInfors = convertToResults(res);
				UserInfo userInfo = null;
				if(userInfors != null && !userInfors.isEmpty()){
					userInfo = (UserInfo)userInfors.get(0); 
				}
				callback.onSuccess(userInfo);
			}
		});
	}
	
	@Override
	protected List<IBasic> convertToResults(String json) {
		ClientUtils.log("UserInfo sql response: " + json);
		JSONArray object = (JSONArray) JSONParser.parseStrict(json);
		List<IBasic> userInfos = new ArrayList<IBasic>();
		for(int i=0;i<object.size();i++){
			IUserInfo iUserInfo = (IUserInfo) MyBean.toObject(IUserInfo.class, object.get(i).isObject());
			UserInfo userInfo = new UserInfo(iUserInfo);
			userInfos.add(userInfo);
		}
		ClientUtils.log("Number of topics: " + userInfos.size());
		return userInfos;
	}
	
	public void deleteUserInfo(String id) {
		deleteUserInfo(id, null);
	}
	
	public void deleteUserInfo(String id,final AsyncCallback<Void> callback) {
		if(!TrackingApp.phoneGap.isPhoneGapDevice()) {
			return;
		}
		TrackingApp.sqLiteDatabaseHelper.deleteQuery(databaseName, SQLiteDatabaseHelper.UserInfoTable, "", new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientUtils.log("Delete failt");
				callback.onFailure(caught);
			}

			@Override
			public void onSuccess(JavaScriptObject result) {
				ClientUtils.log("Delete okie");
				callback.onSuccess(null);
			}
		});
	}
}
