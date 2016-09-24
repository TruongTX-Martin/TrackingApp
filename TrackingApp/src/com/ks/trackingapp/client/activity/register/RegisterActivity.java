package com.ks.trackingapp.client.activity.register;

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
import com.ks.trackingapp.client.activity.login.LoginPlace;
import com.ks.trackingapp.client.util.CipherDES;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.UserInfo;

public class RegisterActivity extends BasicActivity{

	private RegisterView registerView;
	public RegisterActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}
	
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		registerView = clientFactory.getRegisterView();
		super.start(panel, eventBus,registerView);
		panel.setWidget(registerView);
	}
	
	@Override
	protected void onBackPress() {
		super.onBackPress();
		goTo(new LoginPlace());
	}
	
	@Override
	protected void handleEvent() {
		super.handleEvent();
		addHandlerRegistration(registerView.getButtonRegister().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
					handleRegister();
			}
		}));
		
		addHandlerRegistration(registerView.getButtonHasAccount().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				goTo(new LoginPlace());
			}
		}));
	}
	
	private void handleRegister(){
		String userName = registerView.getTextBoxUsername().getText().toString();
		String userView = registerView.getTextBoxUserview().getText().toString();
		String userEmail = registerView.getTextBoxEmail().getText().toString();
		final String password = registerView.getTextBoxPassword().getText().toString();
		String rePassword = registerView.getTextBoxRePassword().getText().toString();
		if(!ClientUtils.validate(userName)) {
			Toaster.showToast("Please type your username.");
			return;
		}
		if(!ClientUtils.validate(userView)) {
			Toaster.showToast("Please type your userview.");
			return;
		}
		if(!ClientUtils.validate(userEmail)) {
			Toaster.showToast("Please type your email.");
			return;
		}
		if(!ClientUtils.validate(password)) {
			Toaster.showToast("Please type your password.");
			return;
		}
		if(!ClientUtils.validate(rePassword)) {
			Toaster.showToast("Please type your repassword");
			return;
		}
		if(!password.equals(rePassword)){
			Toaster.showToast("Password not match,please type password again.");
			return;
		}
		
		final UserInfo userInfo = new UserInfo(userName, userView, userEmail, password);
		String key = ClientUtils.md5(userName.toLowerCase() +"."+password);
		String	passwordEncrypt = "";
		try {
			passwordEncrypt = CipherDES.encryptByDES(password, key);
		} catch (Exception e) {
			ClientUtils.log("Exception : " + e.toString());
		}
		userInfo.setPassword(passwordEncrypt);
		Toaster.showToast("Start register");
		new RPCCall<UserInfo>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Register account failed, please check your network.");
			}

			@Override
			public void onSuccess(UserInfo userResult) {
				if(userResult.isSuccess()){
					userResult.setPassword(password);
					goTo(new LoginPlace(userResult));
					Toaster.showToast("Register success.");
				}else{
					if(userResult.getLoginFailtReason() == Config.USER_ACCOUNT_EXITS){
						Toaster.showToast("Account exits,please register another account.");
					}
					if(userResult.getLoginFailtReason() == Config.USER_ACCOUNT_EMAIL_EXITS){
						Toaster.showToast("Email has registed, please register another email.");
					}
				}
			}

			@Override
			protected void callService(AsyncCallback<UserInfo> cb) {
				TrackingApp.dataService.userRegister(userInfo, cb);
			}
		}.retry(0);;
		
//		
//		
//		TrackingApp.dataService.userRegister(userInfo, new AsyncCallback<UserInfo>() {
//			
//			@Override
//			public void onSuccess(UserInfo userResult) {
//				if(userResult.isSuccess()){
//					userResult.setPassword(password);
//					goTo(new LoginPlace(userResult));
//					Toaster.showToast("Register success.");
//				}else{
//					if(userResult.getLoginFailtReason() == Config.USER_ACCOUNT_EXITS){
//						Toaster.showToast("Account exits,please register another account.");
//					}
//					if(userResult.getLoginFailtReason() == Config.USER_ACCOUNT_EMAIL_EXITS){
//						Toaster.showToast("Email has registed, please register another email.");
//					}
//				}
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				Toaster.showToast("Register account failed, please check your network.");
//			}
//		});
	}
	
	

}
