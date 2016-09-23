package com.ks.trackingapp.client.sqlite;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class SQLiteUtil {
	public static String ELEMENT_TYPE = "elements";
	public static String ELEMENT_VALUE = "elements_value";
	public static String ELEMENT_DATA = "elements_data";
	
	public static Map<String, JSONValue> getElementOfObject(JSONObject object) {
		Map<String, JSONValue> map = new HashMap<String, JSONValue>();
		for (String key : object.keySet()) {
			map.put(key, object.get(key));
		}
		return map;
	}
	
	public static Map<String, JSONValue> getElementOfObject(String json) {
		JSONObject obj = (JSONObject) JSONParser.parseStrict(json);
		return getElementOfObject(obj);
	}
	
	public static Map<String, JSONArray> getBatchElementOfObject(
			JSONArray listChildObjs) {
		prepareObject(listChildObjs);
		Map<String, JSONArray> map = new HashMap<String, JSONArray>();
		for (int i = 0; i < listChildObjs.size(); i++) {
			JSONObject childObj = listChildObjs.get(i).isObject();
			for (String key : childObj.keySet()) {
				if (!map.containsKey(key)) {
					map.put(key, new JSONArray());
				}
				map.get(key).set(map.get(key).size(), childObj.get(key));
			}
		}
		return map;
	}
	
	public static void prepareObject(
			JSONArray listChildObjs) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < listChildObjs.size(); i++) {
			JSONObject childObj = listChildObjs.get(i).isObject();
			for (String key : childObj.keySet()) {
				if (!map.containsKey(key)) {
					JSONValue value = childObj.get(key);
					int type = -1;
					if(value.isArray() != null) {
						type = JSON_ARRAY;
					}
					else if(value.isBoolean() != null) {
						type = JSON_BOOLEAN;
					}
					else if(value.isNull() != null) {
						type = JSON_NULL;
					}
					else if(value.isNumber() != null) {
						type = JSON_NUMBER;
					}
					else if(value.isObject() != null) {
						type = JSON_OBJECT;
					}
					else if(value.isString() != null) {
						type = JSON_STRING;
					}
					map.put(key, type);
				}
			}
		}
		for (int i = 0; i < listChildObjs.size(); i++) {
			JSONObject childObj = listChildObjs.get(i).isObject();
			for(String key : map.keySet()) {
				int type = map.get(key);
				if(!childObj.containsKey(key)) {
					switch (type) {
					case JSON_NULL:
						break;
					case JSON_ARRAY:
						childObj.put(key, new JSONArray());
						break;
					case JSON_BOOLEAN:
						childObj.put(key, JSONBoolean.getInstance(false));
						break;
					case JSON_NUMBER:
						childObj.put(key, new JSONNumber(0));
						break;
					case JSON_OBJECT:
						childObj.put(key, new JSONObject());
						break;
					case JSON_STRING:
						childObj.put(key, new JSONString(""));
						break;
					}
				}
			}
		}
	}
	
	public static final int JSON_NULL = 0;
	public static final int JSON_ARRAY = 1;
	public static final int JSON_BOOLEAN = 2;
	public static final int JSON_NUMBER = 3;
	public static final int JSON_OBJECT = 4;
	public static final int JSON_STRING = 5;
}
