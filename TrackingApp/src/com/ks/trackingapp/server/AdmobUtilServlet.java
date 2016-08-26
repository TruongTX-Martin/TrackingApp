package com.ks.trackingapp.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.ks.trackingapp.shared.Config;

@SuppressWarnings("serial")
public class AdmobUtilServlet extends HttpServlet{
	
	
	
	private Map<String, String> mapAdmobIOS = new HashMap<String, String>();
	private Map<String, String> mapAdmobAndroid = new HashMap<String, String>();
	private final String  APP_TRACKER = "APPTRACKER";
	
	private void prepareAdmobs() {
		if(mapAdmobIOS.isEmpty()) {
			mapAdmobIOS.put(APP_TRACKER, "ca-app-pub-5116052080517162/9941679730");
		}
		if(mapAdmobAndroid.isEmpty()) {
			mapAdmobAndroid.put(APP_TRACKER, "ca-app-pub-5116052080517162/9921660139");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idAdmob = "";
		String platform = req.getParameter("platform");
		prepareAdmobs();
		if(platform.equalsIgnoreCase(Config.PLATFORM_IOS)) {
			idAdmob = mapAdmobIOS.get(APP_TRACKER);
		}else {
			idAdmob = mapAdmobAndroid.get(APP_TRACKER);
		}
		JsonObject object = new JsonObject();
		object.addProperty("idAdmob", idAdmob);
		try {
			resp.getWriter().print(object.toString());
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
