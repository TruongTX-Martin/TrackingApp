package com.ks.trackingapp.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.google.gson.JsonObject;
import com.google.gwt.thirdparty.json.JSONArray;
import com.google.gwt.thirdparty.json.JSONObject;
import com.ks.trackingapp.shared.model.ItemApp;


public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
		String paramUserId = req.getParameter("userId");
		Long userId = Long.parseLong(paramUserId.trim());
		resp.setContentType("text/html; charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		FileItemFactory fileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
		servletFileUpload.setHeaderEncoding("UTF-8");
		final PrintWriter writer = resp.getWriter();
		String result = "";
		try {
			FileItemIterator fileItemIterator = servletFileUpload.getItemIterator(req);
			while (fileItemIterator.hasNext()) {
				FileItemStream fileItemStream = fileItemIterator.next();
				if (fileItemStream.isFormField()) {
					System.out.println("Got a form field: "+ fileItemStream.getFieldName() + " "+ Streams.asString(fileItemStream.openStream()));
					result = getMessageResponse("", "Not found file", -1,0);
				} else {
					InputStream is = fileItemStream.openStream();
					String content = getStringFromInputStream(is);
					parserJson(content, userId);
				}
			}
		} catch (FileUploadException e) {
			result = getMessageResponse("", "Upload error", 0,0);
		} finally {
			writer.write(result);
		}
	}
	
	private void parserJson(String json,Long userId){
		String appName  = "";
		String packageName = "";
		String appleId = "";
		DAO dao = new DAO();
		try {
			JSONArray array = new JSONArray(json);
			if(array.length() > 0){
				for (int i=0; i< array.length() ; i++ ) {
					JSONObject object = array.getJSONObject(i);
					if(object.has("APP_NAME")) {
						appName = object.getString("APP_NAME");
					}
					if(object.has("APPLE_ID")) {
						appleId = object.getString("APPLE_ID");
					}
					if(object.has("PACKAGE_NAME")) {
						packageName = object.getString("PACKAGE_NAME");
					}
					ItemApp itemApp = new ItemApp(appName, userId);
					if(validate(appleId)){
						itemApp.setAppleId(appleId);
						itemApp.setIOS(true);
					}
					if(validate(packageName)){
						itemApp.setPackageName(packageName);
						itemApp.setAndroid(true);
					}
					ItemApp app = dao.appItemAddNew(itemApp);
					if(app.isSuccess()){
						dao.getCommentApp(userId, app);
					}
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	public static boolean validate(String input) {
		if (input == null || input.equals("null") || input.length() == 0) {
			return false;
		}
		return true;
	}
	
	private  String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
	
	public static byte[] getBytes(InputStream is) throws IOException {
	    int len;
	    int size = 1024 * 1024 * 50;//max 50 MB
	    byte[] buf = null;
		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1)
				bos.write(buf, 0, len);
				buf = bos.toByteArray();
		}
	    return buf;
	  }
	
	private String getMessageResponse(String url, String message, int status, double size){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("status", status);
		jsonObject.addProperty("msg", message);
		jsonObject.addProperty("url", url);
		jsonObject.addProperty("size", size);
		return jsonObject.toString();
	}

}
