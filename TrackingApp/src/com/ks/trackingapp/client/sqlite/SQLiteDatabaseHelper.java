package com.ks.trackingapp.client.sqlite;

import java.util.HashMap;
import java.util.Map;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.mgwt.ui.client.MGWT;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.UserInfo;

public class SQLiteDatabaseHelper {
	 
	public Map<String, JavaScriptObject> dbMap = new HashMap<String, JavaScriptObject>();
	public static final String UserDatabase = "UserDatabase.sqlite";
	public static final String UserInfoTable = "UserInfo";
	
	/**************************** BASIC FUNCTION ************************************/
	
	public SQLiteDatabaseHelper() {
		if (TrackingApp.phoneGap.isPhoneGapDevice()) {
			openDatabase(UserDatabase);
		}
	}
	
	public native void openDatabase(String dbName) /*-{
		//productsRequest("Open DB:" + dbName);
		if (!!$wnd.sqlitePlugin) {
			var db = $wnd.sqlitePlugin.openDatabase({name: dbName,iosDatabaseLocation: 'default'});
			//productsRequest("TypeOf DB: " + typeof db);
			this.@com.ks.trackingapp.client.sqlite.SQLiteDatabaseHelper::onOpenDatabase(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(dbName,db);
		} else {
			//productsRequest("Not install sqlite plugin");
		}
	}-*/;

	public void onOpenDatabase(String dbName, JavaScriptObject db) {
		if(db == null) {
			ClientUtils.log("Failed to open db: " + dbName);
			return;
		}
		dbMap.put(dbName, db);
		try {
			if (dbName.equalsIgnoreCase(UserDatabase)) {
				createAllTableUserDataBase(db, dbName);
			} else {
				ClientUtils.log("Not database match.");
			}
		}
		catch (Exception e) {
			ClientUtils.log("Open database error " + e.getMessage());
		}
	}
	
	public void createAllTableUserDataBase(JavaScriptObject db, String dbName) {
		createTable(db, UserInfoTable, UserInfo.createTableCommand);
		if(MGWT.getOsDetection().isAndroid()) {
			createAndroidMetadataTable(db);
		}
	}
	
	private native void createAndroidMetadataTable(JavaScriptObject database) /*-{
		var db = database;
		db.transaction(function(tx) {
			tx.executeSql('CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT DEFAULT \'en_US\')');
		});
		db.transaction(function(tx) {
		       tx.executeSql("INSERT INTO android_metadata (locale) VALUES (?)", ["en_US"], function(tx, res) {
//					//productsRequest("Res: " + res);
//					//productsRequest("insertId: " + res.insertId + " -- probably 1");
//		        	//productsRequest("rowsAffected: " + res.rowsAffected + " -- should be 1");
		        }, function(e) {
		          //productsRequest("ERROR insert android meta data: " + $wnd.JSON.stringify(e));
		        });
			 });
	}-*/;

	
	public native void alterTable(String databaseName, String tableName, String colName) /*-{
		var db = $wnd.sqlitePlugin.openDatabase({name: databaseName});
		db.transaction(function(tx) {
			tx.executeSql('SELECT ' + colName + ' FROM ' + tableName + ' LIMIT 1;', [], 
				function(tx, res) {
				}, function(err) {
					tx.executeSql('ALTER TABLE ' + tableName + ' ADD COLUMN ' + colName + ' TEXT;'); 
				});
		});
	}-*/;
	
	
	public void updateDatabase(String dbName, Callback<JavaScriptObject> callback) {
		updateDatabase(getDatabase(dbName),callback);
	}
	
	public native void updateDatabase(JavaScriptObject database,AsyncCallback<JavaScriptObject> callback) /*-{
		////productsRequest("Update DB");
		var db = database;
		var app =this;
		db.transaction(function(tx) {
			tx.executeSql('UPDATE_DATABASE', [], function(tx, res) {
				app.@com.ks.trackingapp.client.sqlite.SQLiteDatabaseHelper::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,res);
			}, function(e) {
	          console.log("ERROR: " + $wnd.JSON.stringify(e));
	        });
	  	});
	}-*/;
	
	public void createTable(String database, String table, String data) {
		createTable(getDatabase(database), table, data);
	}
	
	private native void createTable(JavaScriptObject database, String table, String data) /*-{
		var tb = table;
		var dt = data;
		//productsRequest("Create table: " + tb +'(' + data + ')');
		var db = database;
		db.transaction(function(tx) {
			tx.executeSql('CREATE TABLE IF NOT EXISTS '+ tb +'(' + data + ')');
			$wnd.console.log("Create table:" + table + " done");
	  	});
	}-*/;
	
	public void insertIntoTable(String database, String table, String dataType, String dataArray) {
		insertIntoTable(getDatabase(database), table, dataType, dataArray, Config.SQL_SPLITTER);
	}
	
	private native void updateTable(JavaScriptObject database,String command) /*-{
		////productsRequest("Update table: " + command);
		var db = database;
		db.transaction(function(tx) {
	        tx.executeSql(command, function(tx, res) {
	        	console.log("Res: " + res);
	        }, function(e) {
	          console.log("ERROR: " + $wnd.JSON.stringify(e));
	        });
		 });
	}-*/;
	
	public void insertIntoTable(JavaScriptObject database, String table, String dataType, String data) {
		insertIntoTable(database, table, dataType, data, Config.SQL_SPLITTER);
	}
	
	public native void insertIntoTable(JavaScriptObject database, String table, String dataType, String data, String spliter) /*-{
		var dataArray = data.split(spliter);
		var db = database;
			db.transaction(function(tx) {
	        	tx.executeSql("INSERT OR REPLACE INTO " + table + " " +dataType, dataArray, function(tx, res) {
	        }, function(e) {
	          //productsRequest("ERROR in inserting to table: " + table + ":" + dataArray + ":" + $wnd.JSON.stringify(e));
	        });
		 });
	}-*/;
	
	public void insertBathchIntoTable(String database,String table, String dataType, String data ) {
		insertBathchIntoTable(getDatabase(database), table, dataType, data, Config.SQL_SPLITTER);
	}
	
	private native void insertBathchIntoTable(JavaScriptObject database, final String table, String dataType, String data, String spliter) /*-{
		var dataArray = data.split(spliter);
		var db = database;
		db.transaction(function(tx) {
			var insertSql = "INSERT OR REPLACE INTO " + table + " " +dataType;
			var l =  dataArray.length;
			//productsRequest("insertSql: " +insertSql);
            for (var i = 0; i < l; i++) {
            	var e;
            	try{
            		e =  JSON.parse('[' +dataArray[i] + ']');
            		//productsRequest("data insert : "+ $wnd.JSON.stringify(e));
				}
				catch(err)
				{
					//productsRequest("insertBathchIntoTable convert data err: " +$wnd.JSON.stringify(err));
				}
				//productsRequest("insertBathchIntoTable : " +$wnd.JSON.stringify(e));
                tx.executeSql(insertSql, e, function(tx, res) {
                	//productsRequest("Insert success: to table " + table);
		        }, function(e) {
		          //productsRequest('insertBathchIntoTable error: ' + $wnd.JSON.stringify(e));
		        });
            }
		 });
	}-*/;
	
	public void selectTable(String database, String table,String select, String where, AsyncCallback<JavaScriptObject> callback ) {
		ClientUtils.log(database + ":select " + select + " from " + table+ " " + where +";");
		selectTable(getDatabase(database), table,select, where, callback);
	}
	
	private native void selectTable(JavaScriptObject database, String table,String select, String where, AsyncCallback<JavaScriptObject> callback) /*-{
		var app = this;
		var db = database;
		if(!db) {
			//productsRequest("db: null or undefined");
		}
		db.transaction(function(tx) {
	         tx.executeSql("select " + select + " from " + table+ " " + where +";", [], function(tx, res) {
	          var result = new Array();
          	  for(var i=0;i<res.rows.length;i++) {
	          	result[i] = res.rows.item(i);
	          }
			app.@com.ks.trackingapp.client.sqlite.SQLiteDatabaseHelper::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,result);
	        });
		 });
	}-*/;
	
	public void selectTable(String database, String table, String where, AsyncCallback<JavaScriptObject> callback ) {
		ClientUtils.log("SQLiteDatabaseHelper.SelecTable: " + database + ":" + table + ":" + where);
		selectTable(getDatabase(database), table, where, callback);
		//$wnd.JSON.stringify(e)
	}
	
	public void query(String database,String sql, AsyncCallback<JavaScriptObject> callback ) {
		ClientUtils.log("SQLiteDatabaseHelper.query: " + database + ":" + sql);
		query(getDatabase(database), sql, callback);
	}
	
	public native void query(JavaScriptObject database,String sql, AsyncCallback<JavaScriptObject> callback) /*-{
		var app = this;
		var db = database;
		//productsRequest("QueryXXX: " + sql);
		db.transaction(function(tx) {
	        tx.executeSql(sql +";", [], function(tx, res) {
	          var result = new Array();
	           for(var i=0;i<res.rows.length;i++) {
	           	result[i] = res.rows.item(i);
	           }
	           //productsRequest("XXXXXXXXXX:" + $wnd.JSON.stringify(result));
			 app.@com.ks.trackingapp.client.sqlite.SQLiteDatabaseHelper::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,result);
	        });
		 });
	}-*/;
	
	public native void selectTable(JavaScriptObject database, String table, String where, AsyncCallback<JavaScriptObject> callback) /*-{
		var app = this;
		////productsRequest($wnd.JSON.stringify(database) + ": select * from " + table + " " + where +";");
		var db = database;
		db.transaction(function(tx) {
	         tx.executeSql("select * from " + table + " " + where +";", [], function(tx, res) {
	          var result = new Array();
	          for(var i=0;i<res.rows.length;i++) {
	          	result[i] = res.rows.item(i);
	          }
	          $wnd.console.log("Success get time progress:" + $wnd.JSON.stringify(result));
	          ////productsRequest("XXXXXXXXXX:" + $wnd.JSON.stringify(result));
			app.@com.ks.trackingapp.client.sqlite.SQLiteDatabaseHelper::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,result);
	        }, function(e) {
	        	$wnd.console.log("Get Time Progress:" + $wnd.JSON.stringify(e));
	        	//productsRequest('Transaction error: ' + $wnd.JSON.stringify(e));
			});
		 });
	}-*/;
	
	protected void callbackSuccess(AsyncCallback<JavaScriptObject> callback,
			JavaScriptObject obj) {
		callback.onSuccess(obj);
	}
	
	public JavaScriptObject getDatabase(String database) {
		if(!dbMap.containsKey(database) || dbMap.get(database) == null){
			openDatabase(database);
		}else {
		}
		return dbMap.get(database);
	}
	
	public Map<String, String> getInsertSQLCommand(String jsonString) {
		Map<String, JSONValue> map = SQLiteUtil.getElementOfObject(jsonString);
		System.out.println(map.size());
		String elements = new String("(");
		String values = new String("(");
		String data = new String();
		int i =0;
		for(String key : map.keySet()) {
			elements += (i==0 ? "" : ",") +key;
			values+= (i==0 ? "" : ",") +"?";
			data += (i==0 ? "" : Config.SQL_SPLITTER) + map.get(key);
			i++; 
		}
		if(data.contains("\""))
			data = data.replaceAll("\"", "");
		elements += ")";
		values += ")";
		Map<String,String> list = new HashMap<String, String>();
		list.put(SQLiteUtil.ELEMENT_TYPE, elements);
		list.put(SQLiteUtil.ELEMENT_VALUE, values);
		list.put(SQLiteUtil.ELEMENT_DATA, data);
		return list;
	} 
	
	public Map<String, String> getBatchInsertSQLCommand(String jsonString) {
		JSONArray listChildObjs = (JSONArray) JSONParser.parseStrict(jsonString);
		return getBatchInsertSQLCommand(listChildObjs);
	}
	
	public static Map<String, String> getBatchInsertSQLCommand(JSONArray array) {
		Map<String, JSONArray> map = SQLiteUtil.getBatchElementOfObject(array);
		String elements = new String("(");
		String values = new String("(");
		String data = new String();
		int i =0;
		for(String key : map.keySet()) {
			elements += (i==0 ? "" : ",") +key;
			values+= (i==0 ? "" : ",") +"?";
			i++;
		}
		i = 0;
		for(i =0;i<array.size();i++) {
			String childData = "";
			int j=0;
			for(String childKey : map.keySet()) {
				String childVal = null;
				if(map.get(childKey).get(i) != null){
					childVal = map.get(childKey).get(i).toString();
					if (childVal != null && childVal.startsWith("[") && childVal.endsWith("]")) {
						childVal = childVal.replaceAll("\"", "\\\\\"");
						childVal = childVal.replace("[", "\"[");
						childVal = childVal.replace("]", "]\"");
						childVal = childVal.replace("\\\\", "\\\\\\");
					}
					childData += (j==0 ? "" : ",") + childVal;
				}
				else {
					int type = getChildKeyType(map, childKey);
					if(type == 0 ) {
						childData += (j==0 ? "" : ",") + 0;
					}
					else if(type == 1){
						childData += (j==0 ? "" : ",") + "\"\"";
					}
				}
				j++;
			}
			data += (i==0 ? "" : Config.SQL_SPLITTER) + childData;
		}
		elements += ")";
		values += ")";
		Map<String,String> list = new HashMap<String, String>();
		list.put(SQLiteUtil.ELEMENT_TYPE, elements);
		list.put(SQLiteUtil.ELEMENT_VALUE, values);
		list.put(SQLiteUtil.ELEMENT_DATA, data);
		return list;
	}
	
	private static int getChildKeyType(Map<String, JSONArray> map, String childKey) { //return 0: number, 1 text
		JSONArray array = map.get(childKey);
		if(array == null)
			return -1;
		for(int i =0;i<array.size();i++) {
			if(array.get(i) != null) {
				if(array.get(i).isNumber() != null)
					return 0;
				else return 1;
			}
		}
		return 1;
	}
	
	public static native String getResponseString(JavaScriptObject object )/*-{
		return JSON.stringify(object);
	}-*/;
	
	/*************************** DROP TABLE ************************************/
	public void dropTable(String database, String table) {
		dropTable(getDatabase(database), table);
	}
	
	public native void dropTable (JavaScriptObject database, String table) /*-{
		//productsRequest("Drop table: " + table);
		var db = database;
		db.transaction(function(tx) {
	        tx.executeSql('DROP TABLE IF EXISTS '+ table);
	  	});
	}-*/;
	
	/**************************** DELETE ROW  *********************************/
	public void deleteQuery(String database, String table, String condition,AsyncCallback<JavaScriptObject> callback) {
		deleteQuery(getDatabase(database), table,condition,callback);
	}
	
	public native void deleteQuery(JavaScriptObject database, String table, String condition,AsyncCallback<JavaScriptObject> callback) /*-{
		var app = this;
		var db = database;
		var sql = "DELETE FROM "+ table +" "+condition;
		//productsRequest("Delete query: " + sql);
		db.transaction(function(tx) {
	        tx.executeSql(sql);
	        app.@com.ks.trackingapp.client.sqlite.SQLiteDatabaseHelper::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,null);
	  	});
	}-*/;
	
	
	/**************************** DELETE ROW  *********************************/
	
	/**************************** INSERT INTO TABLE  *********************************/
	

	
	/**************************** UPDATE TABLE  *********************************/
	public void updateTable(String database, String table, String where, Map<String, String> updateContent) {
		String command = "";
		int i=0;
		for(String key : updateContent.keySet()) {
			command += (i==0 ? "" : ",") + key + " = '" + updateContent.get(key) +"'";
			i++;
		}
		updateTable(getDatabase(database), table,where, command );
	}
	
	public native void updateTable(JavaScriptObject database, String table,String where, String sqlCommand) /*-{
		var db = database;
		db.transaction(function(tx) {
	        tx.executeSql("UPDATE " + table + " SET " +sqlCommand + where +";", [], function(tx, res) {
	        	//productsRequest("Res: " + JSON.stringify(res));
	        }, function(e) {
	          //productsRequest("ERROR WHILE UPDATE: " + JSON.stringify(res));
	        });
		 });
	}-*/;
	
	public static void replaceArrayStringToArray(JSONObject object, String key) {
		String str = object.get(key).isString().stringValue();
		JSONArray array = (JSONArray) JSONParser.parseStrict(str);
		object.put(key, null);
		object.put(key, array);
	}

	public Map<String, String> getInsertSQLCommand(JSONObject object) {
		Map<String, JSONValue> map = SQLiteUtil.getElementOfObject(object);
		String elements = new String("(");
		String values = new String("(");
		String data = new String();
		int i =0;
		for(String key : map.keySet()) {
			elements += (i==0 ? "" : ",") +key;
			values+= (i==0 ? "" : ",") +"?";
			data += (i==0 ? "" : Config.SQL_SPLITTER) + map.get(key);
			i++; 
		}
		elements += ")";
		values += ")";
		Map<String,String> list = new HashMap<String, String>();
		list.put(SQLiteUtil.ELEMENT_TYPE, elements);
		list.put(SQLiteUtil.ELEMENT_VALUE, values);
		list.put(SQLiteUtil.ELEMENT_DATA, data);
		return list;
	} 
}
