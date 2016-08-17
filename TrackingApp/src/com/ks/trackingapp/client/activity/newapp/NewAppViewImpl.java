package com.ks.trackingapp.client.activity.newapp;

import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.view.BHTouchImage;
import com.ks.trackingapp.shared.Config;

public class NewAppViewImpl extends BasicViewImpl implements NewAppView {

	private static NewAppViewImplUiBinder uiBinder = GWT
			.create(NewAppViewImplUiBinder.class);

	interface NewAppViewImplUiBinder extends UiBinder<Widget, NewAppViewImpl> {
	}
	private BHTouchImage btnCheck = new BHTouchImage("images/ic_check.png");
	
	protected @UiField TextBox tbAppName,tbPackageName,tbAppleId;
	protected @UiField CheckBox cbAndroid,cbIOS;

	public NewAppViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.layoutBasic.getHeaderPanel().showNavigation(false);
		this.layoutBasic.getHeaderPanel().setCenter(Config.ITEMSCREEN_ADDAPP);
		btnCheck.setPixelSize(40, 40);
		this.layoutBasic.getHeaderPanel().getRightPanel().add(btnCheck);
	}

	@Override
	public TextBox getTextboxAppname() {
		return tbAppName;
	}

	@Override
	public TextBox getTextboxPakageName() {
		return tbPackageName;
	}

	@Override
	public TextBox getTextboxAppleId() {
		return tbAppleId;
	}

	@Override
	public CheckBox getCheckBoxAndroid() {
		return cbAndroid;
	}

	@Override
	public CheckBox getCheckBoxIOS() {
		return cbIOS;
	}

	@Override
	public BHTouchImage getButtonCheck() {
		return btnCheck;
	}


}
