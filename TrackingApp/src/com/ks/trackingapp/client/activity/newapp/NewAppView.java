package com.ks.trackingapp.client.activity.newapp;

import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.view.BHTouchImage;

public interface NewAppView extends BasicView{
	
	TextBox getTextboxAppname();
	TextBox getTextboxPakageName();
	TextBox getTextboxAppleId();
	CheckBox getCheckBoxAndroid();
	CheckBox getCheckBoxIOS();
	BHTouchImage getButtonCheck();
	Button getButtonCancel();
	Button getButtonOk();
	Button getButtonSubmit();
	Button getButtonDeleteComment();
	Button getButtonDeleteApp();
	FormPanel getFormPanel();
	FileUpload getFileUpload();
	FlowPanel getFlowPanelUpload();
	FlowPanel getFlowPanelDelete();
}
