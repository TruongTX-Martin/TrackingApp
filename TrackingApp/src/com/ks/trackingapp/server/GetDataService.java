package com.ks.trackingapp.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.UserInfo;

@SuppressWarnings("serial")
public class GetDataService extends HttpServlet {
	private static final Logger logger = Logger.getLogger(GetDataService.class
			.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
		try {
			logger.info("Cron Job has been executed ***************************");
			logger.warning("Excute cron job log server");
			DAO dao = new DAO();
			ArrayList<UserInfo> listUser = dao.getAllUser();
			if(listUser.size() > 0) {
				for (int i=0; i< listUser.size(); i++) {
					UserInfo userInfo = listUser.get(i);
					ArrayList<ItemApp> list = dao.appGetAllItem(userInfo.getId());
					if(list.size() > 0){
						for (int j=0; j< list.size() ; j++) {
							ItemApp app = list.get(j);
							dao.getCommentApp(userInfo.getId(), app);
						}
					}
				}
			}
			
		} catch (Exception ex) {
			// Log any exceptions in your Cron Job
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
