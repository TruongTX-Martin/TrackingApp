package com.ks.trackingapp.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.sqlite.MyBean;
import com.ks.trackingapp.client.sqlite.SQLiteDatabaseHelper;
import com.ks.trackingapp.client.sqlite.SQLiteUtil;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.interfacemodel.IBasic;

public abstract class DataManager {
	
	protected String databaseName = "";
	
	protected void getData(final Long parentId,final String userId, 
			final int limit, final AsyncCallback<List<IBasic>> cb) {
		if (!ClientUtils.isOnline()) {
			getLocalData(parentId,userId, cb);
			return;
		}
		if (!TrackingApp.phoneGap.isPhoneGapDevice()) {
			getServerData(parentId, userId, Config.NULL_ID, limit, cb);
			return;
		}
		getLocalLastUpdate(parentId,userId, new AsyncCallback<Long>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientUtils.log("LastUpdate eror found: NULL");
				getData(parentId,userId, -1L,limit, cb);
			}
			@Override
			public void onSuccess(Long result) {
				ClientUtils.log("LastUpdate success found: " + result);
				getData(parentId,userId, result,limit, cb);
			}
		});
	}
	
	protected void getData(final Long parentId, final String userId,final Long lastUpdate,
			final int limit, final AsyncCallback<List<IBasic>> cb) {
		if(ClientUtils.isOnline()) {
			ClientUtils.log("Client online");
			getServerData(parentId, userId, lastUpdate,limit, new AsyncCallback<List<IBasic>>() {
				@Override
				public void onFailure(Throwable caught) {
					if(TrackingApp.phoneGap.isPhoneGapDevice())
						getLocalData(parentId,userId, cb);
					else cb.onFailure(caught);
				}

				@Override
				public void onSuccess(final List<IBasic> serverResult) {
					if(TrackingApp.phoneGap.isPhoneGapDevice()) {
						getLocalData(parentId, userId, new AsyncCallback<List<IBasic>>() {
	
							@Override
							public void onFailure(Throwable caught) {
								cb.onSuccess(serverResult);
							}
	
							@Override
							public void onSuccess(List<IBasic> localResult) {
								cb.onSuccess(mergeResult(parentId,serverResult, localResult));
							}
						});
					}
					else {
						cb.onSuccess(serverResult);
					}
				}
			});
		} else {
			ClientUtils.log("Client offline");
			if(TrackingApp.phoneGap.isPhoneGapDevice())
				getLocalData(parentId,userId, cb);
			else cb.onFailure(new Throwable());		
		}
 	}
	
	protected List<IBasic> mergeResult(final Long parentId, final List<IBasic> serverResult, final List<IBasic> localResult) {
		ClientUtils.log(this.getClass().getName() + " mergeResult serverResult: " + serverResult.size() +" localResult: " + localResult.size());
		Map<Object,IBasic> map = new HashMap<Object,IBasic>();
		if(localResult != null)
			for(IBasic basic : localResult) {
				if(map.containsKey(basic.getId())) {
					map.put(basic.getId(), basic);
				}
			}
		if(serverResult != null)
			for(IBasic basic : serverResult) {
				map.put(basic.getId(), basic);
			}
		return new ArrayList<IBasic>(map.values());
	}
	
	protected void getLocalLastUpdate(final Long parentId,final String userId,final String database, final String table, final AsyncCallback<Long> cb) {
		if(!TrackingApp.phoneGap.isPhoneGapDevice()) {
			cb.onSuccess(-1L);
			return;
		} 
		String select = "lastUpdate";
		String sql = "";
		if(userId != null && !userId.isEmpty()) {
			sql = " WHERE userId = '" + userId+"'";
		}
		if(parentId == null || parentId < 0)
			sql += " ORDER BY lastUpdate DESC Limit 1";
		else {
			sql += sql.isEmpty() ? "WHERE parentId = " + parentId + " ORDER BY lastUpdate DESC Limit 1" 
					: " AND parentId = " + parentId + " ORDER BY lastUpdate DESC Limit 1";
		}
		//final String className = this.getClass().getSimpleName();
		ClientUtils.log("getLocalLastUpdate : " + sql);
		TrackingApp.sqLiteDatabaseHelper.selectTable(database, table,select, sql,new AsyncCallback<JavaScriptObject>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(JavaScriptObject response) {
				try{
					String res = SQLiteDatabaseHelper.getResponseString(response);
					JSONArray jsonObject = (JSONArray)JSONParser.parseStrict(res);
					if(jsonObject.size() == 0) {
						cb.onSuccess(-1L);
						return;
					}
					Long lastUpdate = Long.parseLong(jsonObject.get(0).isObject().get("lastUpdate").isString().stringValue());
					cb.onSuccess(lastUpdate);
				}catch(Exception e) {
					cb.onSuccess(-1L);
				}
			}
		});
	}
	
	protected void getLocalLastUpdate(final List<Long> parentIds, final String userId,final String database, final String table,
			final AsyncCallback<Map<Long,Long>> cb) {
		Map<Long, Long> map = new HashMap<Long, Long>();
		for(Long parentId : parentIds) {
			map.put(parentId, -1L);
		}
		if(!TrackingApp.phoneGap.isPhoneGapDevice()) {
			cb.onSuccess(map);
			return;
		}
		final Map<Long, Long> returnMaps = new HashMap<Long, Long>();
		final List<Long> gotNumber = new ArrayList<Long>();
		for(final Long parentId : parentIds) {
			String select = "lastUpdate";
			String sql = "";
			if(userId != null) {
				sql = " WHERE userId = '" + userId+"'";
			}
			if(parentId == null || parentId < 0)
				sql += " ORDER BY lastUpdate DESC Limit 1";
			else {
				sql += sql.isEmpty() ? "WHERE parentId = " + parentId + " ORDER BY lastUpdate DESC Limit 1" 
						: " AND parentId = " + parentId + " ORDER BY lastUpdate DESC Limit 1";
			}
			TrackingApp.sqLiteDatabaseHelper.selectTable(database, table,select, sql,new AsyncCallback<JavaScriptObject>() {
				@Override
				public void onFailure(Throwable caught) {
				}
				@Override
				public void onSuccess(JavaScriptObject response) {
					String res = SQLiteDatabaseHelper.getResponseString(response);
					try{
						JSONArray jsonObject = (JSONArray)JSONParser.parseStrict(res);
						Long lastUpdate = Long.parseLong(jsonObject.get(0).isObject().get("lastUpdate").isString().stringValue());
						returnMaps.put(parentId, lastUpdate);
					} catch(Exception e) {
						returnMaps.put(parentId, -1L);
					}
					gotNumber.add(parentId);
					if(gotNumber.size() == parentIds.size()) {
						cb.onSuccess(returnMaps);
					}
				}
			});
		}
	}
	
	protected List<Long> getLongs(String key, JSONObject jsonObject) {
		List<Long> multiChoise = new ArrayList<Long>();
		try{
			String choices = jsonObject.get(key).isString().stringValue();
			if(choices!= null && !choices.isEmpty()) {
				ClientUtils.log("choices : " + choices);
				JSONArray array = (JSONArray)JSONParser.parseStrict(choices);
				for(int i=0;i<array.size();i++) {
					if(array.get(i).isString()!=null) {
						multiChoise.add(Long.parseLong(array.get(i).isString().stringValue()));
					} else if(array.get(i).isNumber()!=null){
						multiChoise.add((long) array.get(i).isNumber().doubleValue());
					}
				}
			}
		}catch (Exception e){
			try{
				JSONArray array = jsonObject.get(key).isArray();
				for(int i=0;i<array.size();i++) {
					multiChoise.add((long) array.get(i).isNumber().doubleValue());
				}
			}catch (Exception e1){
			}
		}
		return multiChoise;
	}
	
	public static void insertData(String database,String table, IBasic object) {
		if(!TrackingApp.phoneGap.isPhoneGapDevice()){
			return;
		}
		if(object == null)
			return;
		List<IBasic> objectToInsert = new ArrayList<IBasic>();
		List<IBasic> objectToDelete = new ArrayList<IBasic>();
		if(object.getStatus() != Config.STATUS_DELETE) {
			objectToInsert.add(object);
		} else {
			objectToDelete.add(object);
		}
		if(!objectToInsert.isEmpty()) {
			try {
				String data = MyBean.toJson(objectToInsert);
				ClientUtils.log("Data====================+>"+ data);
				Map<String, String> map = TrackingApp.sqLiteDatabaseHelper.getBatchInsertSQLCommand(data);
				ClientUtils.log("Data Map:======================+>"+ map);
				TrackingApp.sqLiteDatabaseHelper.insertBathchIntoTable(database,table,
						map.get(SQLiteUtil.ELEMENT_TYPE) + " VALUES "+ map.get(SQLiteUtil.ELEMENT_VALUE),
						map.get(SQLiteUtil.ELEMENT_DATA));
			}
			catch (Exception e) {
			}
		}
		if(!objectToDelete.isEmpty()) {
			ClientUtils.log("xxxDelete " + objectToDelete.size() + " from " + table);
			List<Object> cardIds = new ArrayList<Object>();
			for(IBasic basic : objectToDelete) {
				cardIds.add(basic.getId());
			}
			String ids = "";
			List<Object> toRemoved = new ArrayList<Object>(cardIds);
			for (int i = 0; i < toRemoved.size(); i ++) {
				Object o = toRemoved.get(i);
				if(o instanceof String)
					ids += "'" + o +"'";
				else if(o instanceof Long)
					ids += o;
				if ( i < toRemoved.size() - 1)
					ids += ",";
			}
			String sqlCommand = "DELETE FROM " + table + " WHERE id in (" + ids + ")";
			TrackingApp.sqLiteDatabaseHelper.query(database, sqlCommand, new AsyncCallback<JavaScriptObject>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(JavaScriptObject result) {
				}
			});
		}
	}
	
	public void insertData(String database,String table, List<? extends IBasic> objects) {
		if(!TrackingApp.phoneGap.isPhoneGapDevice()){
			return;
		}
		if(objects == null || objects.isEmpty())
			return;
		List<IBasic> objectToInsert = new ArrayList<IBasic>();
		List<IBasic> objectToDelete = new ArrayList<IBasic>();
		for(IBasic basic : objects) {
			if(basic.getStatus() != Config.STATUS_DELETE) {
				objectToInsert.add(basic);
			} else {
				objectToDelete.add(basic);
			}
		}
		if(!objectToInsert.isEmpty()) {
			ClientUtils.log("Insert " + objectToInsert.size() + " " + table+" to database");
			try {
				String data = MyBean.toJson(objectToInsert);
				Map<String, String> map = TrackingApp.sqLiteDatabaseHelper.getBatchInsertSQLCommand(data);
				TrackingApp.sqLiteDatabaseHelper.insertBathchIntoTable(database,table,
						map.get(SQLiteUtil.ELEMENT_TYPE) + " VALUES "+ map.get(SQLiteUtil.ELEMENT_VALUE),
						map.get(SQLiteUtil.ELEMENT_DATA));
			}
			catch (Exception e) {
				ClientUtils.log("error : " + e.getMessage());
			}
		}
		if(!objectToDelete.isEmpty()) {
			List<Object> cardIds = new ArrayList<Object>();
			for(IBasic basic : objectToDelete) {
				cardIds.add(basic.getId());
			}
			String ids = "";
			List<Object> toRemoved = new ArrayList<Object>(cardIds);
			for (int i = 0; i < toRemoved.size(); i ++) {
				Object object = toRemoved.get(i);
				if(object instanceof String)
					ids += "'" + object +"'";
				else if(object instanceof Long)
					ids += object;
				if ( i < toRemoved.size() - 1)
					ids += ",";
			}
			TrackingApp.sqLiteDatabaseHelper.deleteQuery(database, table, " WHERE id in (" + ids + ")", new AsyncCallback<JavaScriptObject>() {

				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(JavaScriptObject result) {
				}
			});
		}
	}
	
	protected List<IBasic> convertToResults(String json) {
		return null;
	}

	public void getLocalLastUpdate(final Long parentId,final String userId, final AsyncCallback<Long> cb) {
	}
	
	protected void getServerData(final Long parentId,final String userId,final Long lastUpdate,final int limit, final AsyncCallback<List<IBasic>> cb) {
	}
	
	protected void getLocalData(final Long parentId,final String userId, final AsyncCallback<List<IBasic>> cb){
	}

}
