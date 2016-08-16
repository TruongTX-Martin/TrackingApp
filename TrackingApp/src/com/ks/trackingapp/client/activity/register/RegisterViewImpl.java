package com.ks.trackingapp.client.activity.register;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.view.KSMPasswordTextBox;
import com.ks.trackingapp.client.view.KSMTextBox;

public class RegisterViewImpl extends BasicViewImpl implements RegisterView {

	private static RegisterViewImplUiBinder uiBinder = GWT
			.create(RegisterViewImplUiBinder.class);

	interface RegisterViewImplUiBinder extends
			UiBinder<Widget, RegisterViewImpl> {
	}
	protected @UiField Button btnRegister,btnHasAccount;
	protected @UiField KSMTextBox tbUsername,tbUserview,tbEmail;
	protected @UiField KSMPasswordTextBox tbPassword,tbRePassword;
	
	public RegisterViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.layoutBasic.getHeaderPanel().showNavigation(false);
	}

	@Override
	public Button getButtonRegister() {
		return btnRegister;
	}

	@Override
	public Button getButtonHasAccount() {
		return btnHasAccount;
	}

	@Override
	public KSMTextBox getTextBoxUsername() {
		return tbUsername;
	}

	@Override
	public KSMTextBox getTextBoxUserview() {
		return tbUserview;
	}

	@Override
	public KSMTextBox getTextBoxEmail() {
		return tbEmail;
	}

	@Override
	public KSMPasswordTextBox getTextBoxPassword() {
		return tbPassword;
	}

	@Override
	public KSMPasswordTextBox getTextBoxRePassword() {
		return tbRePassword;
	}




}
