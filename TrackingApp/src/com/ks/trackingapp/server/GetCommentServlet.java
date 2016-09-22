package com.ks.trackingapp.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ks.trackingapp.shared.model.ItemComment;

@SuppressWarnings("serial")
public class GetCommentServlet extends HttpServlet{
	
	DAO dao = new DAO();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		final PrintWriter writer = resp.getWriter();
		String userId = req.getParameter("appId").trim();
		String rate = req.getParameter("rate").trim();
		String platform = req.getParameter("platform").trim();
		String limit = req.getParameter("limit").trim();
		String offset = req.getParameter("offset").trim();
		ArrayList<ItemComment> list;
		if(userId.contains("null")){
			list = dao.getCommentRateAndPlatform(platform, Integer.parseInt(rate),Integer.parseInt(offset),Integer.parseInt(limit));
		}else{
			list = dao.getCommentFromPlatform(Long.parseLong(userId),platform, Integer.parseInt(rate),Integer.parseInt(offset),Integer.parseInt(limit));
		}
		if(list != null && list.size() > 0) {
			JsonArray array = new JsonArray();
			for (int i=0; i< list.size() ; i++) {
				ItemComment comment = list.get(i);
				JsonObject object = new JsonObject();
				object.addProperty("id", comment.getId());
				object.addProperty("title", comment.getTitle());
				object.addProperty("comment", comment.getComment());
				object.addProperty("platform", comment.getPlatform());
				object.addProperty("appname", comment.getAppname());
				object.addProperty("appId", comment.getAppId());
				object.addProperty("userId", comment.getUserId());
				object.addProperty("date", comment.getDate().toString());
				object.addProperty("rating", comment.getRating());
				object.addProperty("idComment", comment.getIdComment());
				object.addProperty("language", comment.getLanguage());
				object.addProperty("username", comment.getUserName());
				object.addProperty("avatar", comment.getAvatar());
				array.add(object);
			}
			writer.write(array.toString());
		}else{
			writer.write("Data empty");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	private boolean validate(String text){
		if(text != null && text.length() > 0 && !text.equals("null")){
			return true;
		}
		return false;
	}

}
