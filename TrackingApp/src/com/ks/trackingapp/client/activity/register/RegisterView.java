package com.ks.trackingapp.client.activity.register;

import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.view.KSMPasswordTextBox;
import com.ks.trackingapp.client.view.KSMTextBox;

public interface RegisterView extends BasicView{
	
	Button getButtonRegister();
	Button getButtonHasAccount();
	KSMTextBox getTextBoxUsername();
	KSMTextBox getTextBoxUserview();
	KSMTextBox getTextBoxEmail();
	KSMPasswordTextBox getTextBoxPassword();
	KSMPasswordTextBox getTextBoxRePassword();

}
