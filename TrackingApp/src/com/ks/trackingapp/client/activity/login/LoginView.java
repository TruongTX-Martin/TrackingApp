package com.ks.trackingapp.client.activity.login;

import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.view.KSMPasswordTextBox;
import com.ks.trackingapp.client.view.KSMTextBox;

public interface LoginView extends BasicView{

	Button getButtonLogin();
	Button getButtonForgetPassword();
	Button getButtonRegister();
	Button getButtonLoginFacebook();
	KSMTextBox getTextboxUserName();
	KSMPasswordTextBox getTextboxPassword();
}
