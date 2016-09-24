package com.ks.trackingapp.client.activity.login;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.ks.trackingapp.client.RPCCall;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentPlace;
import com.ks.trackingapp.client.activity.register.RegisterPlace;
import com.ks.trackingapp.client.manager.TrackingManager;
import com.ks.trackingapp.client.plugin.CallPlugin;
import com.ks.trackingapp.client.util.CipherDES;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.UserInfo;

public class LoginActivity extends BasicActivity{

	
	private LoginView loginView;
	private UserInfo userInfo;
	String	passwordEncrypt = "";
	public LoginActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
		userInfo = ((LoginPlace)place).getUserInfo();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		loginView = clientFactory.getLoginView();
		super.start(panel, eventBus,loginView);
		panel.setWidget(loginView);
		if(userInfo != null){
			loginView.getTextboxUserName().setText(userInfo.getUserName());
			loginView.getTextboxPassword().setText(userInfo.getPassword());
		}
	}
	
	
	@Override
	protected void handleEvent() {
		super.handleEvent();
		addHandlerRegistration(loginView.getButtonRegister().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				goTo(new RegisterPlace());
			}
		}));
		
		addHandlerRegistration(loginView.getButtonLogin().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				if(TrackingApp.phoneGap.isPhoneGapDevice()){
					CallPlugin.hideKeyBoard();
				}
				handleLogin();
			}
		}));
		
	}
	
	private void handleLogin(){
		final String userName = loginView.getTextboxUserName().getText().toString();
		String password = loginView.getTextboxPassword().getText().toString();
		if(!ClientUtils.validate(userName)){
			Toaster.showToast("Please type your username.");
			return;
		}
		if(!ClientUtils.validate(password)) {
			Toaster.showToast("Please type your password.");
			return;
		}
		String key = ClientUtils.md5(userName.toLowerCase() +"."+password);
		
		try {
			passwordEncrypt = CipherDES.encryptByDES(password, key);
		} catch (Exception e) {
			ClientUtils.log("Exception : " + e.toString());
		}
		new RPCCall<UserInfo>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Login failed,please check your connection.");
			}

			@Override
			public void onSuccess(UserInfo userResult) {
				if(userResult.isSuccess()){
					TrackingManager.newInstance().setCurrentUser(userResult);
					Toaster.showToast("Hello," + userResult.getUserView());
					goTo(new HomeCommentPlace());
				}else{
					if(userResult.getLoginFailtReason() == Config.USER_ACCOUNT_NOTEXITS){
						Toaster.showToast("Account not exits, please type correct your account or register new account.");
						loginView.getTextboxUserName().setText("");
						loginView.getTextboxPassword().setText("");
					}
					if(userResult.getLoginFailtReason() == Config.USERT_ACCOUNT_PASSWORD_NOTCORRECT) {
						Toaster.showToast("Your password not correct,please type correct password.");
						loginView.getTextboxPassword().setText("");
					}
				}
			}

			@Override
			protected void callService(AsyncCallback<UserInfo> cb) {
				TrackingApp.dataService.userLogin(userName, passwordEncrypt, cb);
			}
		}.retry(0);;
		
		
		
//		TrackingApp.dataService.userLogin(userName, passwordEncrypt, new AsyncCallback<UserInfo>() {
//			
//			@Override
//			public void onSuccess(UserInfo userResult) {
//				if(userResult.isSuccess()){
//					TrackingManager.newInstance().setCurrentUser(userResult);
//					Toaster.showToast("Hello," + userResult.getUserView());
//					goTo(new HomeCommentPlace());
//				}else{
//					if(userResult.getLoginFailtReason() == Config.USER_ACCOUNT_NOTEXITS){
//						Toaster.showToast("Account not exits, please type correct your account or register new account.");
//						loginView.getTextboxUserName().setText("");
//						loginView.getTextboxPassword().setText("");
//					}
//					if(userResult.getLoginFailtReason() == Config.USERT_ACCOUNT_PASSWORD_NOTCORRECT) {
//						Toaster.showToast("Your password not correct,please type correct password.");
//						loginView.getTextboxPassword().setText("");
//					}
//				}
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				Toaster.showToast("Login failed,please check your connection.");
//			}
//		});
		
	}

}
