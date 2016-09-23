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
public class GetCommentServlet extends HttpServlet {

	DAO dao = new DAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		String appId= null,rate = null,platform=null,limit=null,offset=null,count = null;
		final PrintWriter writer = resp.getWriter();
		try {
			appId = req.getParameter("appId").trim();
		} catch (Exception e) {
		}
		try {
			rate = req.getParameter("rate").trim();
		} catch (Exception e) {
		}
		try {
			platform = req.getParameter("platform").trim();
		} catch (Exception e) {
		}
		try {
			limit = req.getParameter("limit").trim();
		} catch (Exception e) {
		}
		try {
			offset = req.getParameter("offset").trim();
		} catch (Exception e) {
		}
		try {
		   count = req.getParameter("count").trim();
		} catch (Exception e) {
		}
		ArrayList<ItemComment> list = null;
		int size = 0;
		if (count == null || count.equals("null")) {
			if (appId.contains("null")) {
				list = dao.getCommentRateAndPlatform(platform,
						Integer.parseInt(rate), Integer.parseInt(offset),
						Integer.parseInt(limit));
			} else {
				list = dao.getCommentFromPlatform(Long.parseLong(appId),
						platform, Integer.parseInt(rate),
						Integer.parseInt(offset), Integer.parseInt(limit));
			}
			if (list != null && list.size() > 0) {
				JsonArray array = new JsonArray();
				for (int i = 0; i < list.size(); i++) {
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
			} else {
				writer.write("Data empty");
			}

		} else {
			if (appId.contains("null")) {
				size = dao.getCountRateAndPlatform(platform,
						Integer.parseInt(rate));
			} else {
				size = dao.getCountFromPlatform(Long.parseLong(appId),
						platform, Integer.parseInt(rate));
			}
			JsonObject object = new JsonObject();
			object.addProperty("count", size);
			writer.write(object.toString());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	private boolean validate(String text) {
		if (text != null && text.length() > 0 && !text.equals("null")) {
			return true;
		}
		return false;
	}

}
