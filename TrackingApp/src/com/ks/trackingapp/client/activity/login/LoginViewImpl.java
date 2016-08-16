package com.ks.trackingapp.client.activity.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.view.KSMPasswordTextBox;
import com.ks.trackingapp.client.view.KSMTextBox;

public class LoginViewImpl extends BasicViewImpl implements LoginView {

	private static LoginViewImplUiBinder uiBinder = GWT
			.create(LoginViewImplUiBinder.class);

	interface LoginViewImplUiBinder extends UiBinder<Widget, LoginViewImpl> {
	}

	protected @UiField Button btnLogin,btnForgetPassword,btnRegister,btnLoginFacebook;
	protected @UiField KSMTextBox tbUserName;
	protected @UiField KSMPasswordTextBox tbPassword;
	public LoginViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.layoutBasic.getHeaderPanel().hideAllButton();
	}
	@Override
	public Button getButtonLogin() {
		return btnLogin;
	}
	@Override
	public Button getButtonForgetPassword() {
		return btnForgetPassword;
	}
	@Override
	public Button getButtonRegister() {
		return btnRegister;
	}
	@Override
	public Button getButtonLoginFacebook() {
		return btnLoginFacebook;
	}
	@Override
	public KSMTextBox getTextboxUserName() {
		return tbUserName;
	}
	@Override
	public KSMPasswordTextBox getTextboxPassword() {
		return tbPassword;
	}


	

}
