package com.ks.trackingapp.client.sqlite;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.shared.interfacemodel.IBasic;
import com.ks.trackingapp.shared.interfacemodel.IUserInfo;

public class MyBean  {
	private static MyFactory factory = GWT.create(MyFactory.class);
	
	public MyBean(){
	}
	  
	public static String toJson(List<IBasic> objs) {
		JSONArray arr = new JSONArray();
		int index = 0;
		for (IBasic obj : objs) {
			String json = toJson(obj);
			if (json != null) {
				arr.set(index ++, JSONParser.parseStrict(json));
			}
		}
		return arr.toString();
	}
	
	public static String toJson(IBasic obj) {
		AutoBean<IBasic> autoBean = null;
		IBasic ss = null;
		if(obj instanceof IUserInfo)
			ss = factory.toObject((IUserInfo)obj).as();
		if (ss == null) {
			ClientUtils.log("Something wrong happens with AutoBean");
			return "";
		}
		autoBean = AutoBeanUtils.getAutoBean(ss); 
		return AutoBeanCodex.encode(autoBean).getPayload();
	  }
	  
	  @SuppressWarnings("rawtypes")
	  public static IBasic toObject(Class clazz, JSONObject json) {
		  return toObject(clazz, json.toString());
	  }
	  
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  public static IBasic toObject(Class clazz, String json) {
		AutoBean<IBasic> autoBean = AutoBeanCodex.decode(factory, clazz, json);
		  IBasic dto = autoBean.as();
		  return dto;
	  }
}