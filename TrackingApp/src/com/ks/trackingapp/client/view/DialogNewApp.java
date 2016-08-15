package com.ks.trackingapp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.shared.Config;

public class DialogNewApp extends KSDialogPanel {

	private static DialogNewAppUiBinder uiBinder = GWT
			.create(DialogNewAppUiBinder.class);

	interface DialogNewAppUiBinder extends UiBinder<Widget, DialogNewApp> {
	}

	@UiField
	Button btnCancel, btnAdd;
	@UiField
	TextBox tbAppName, tbPackageName;
	@UiField ListBox lbPlatform;
	@UiField HTML htmlPackageName;

	public DialogNewApp() {
		super();
//		super.add(uiBinder.createAndBindUi(this));
//		this.setHideOnBackgroundClick(false);
		this.getModal().setWidth("70%");
		this.getModalBody().add(uiBinder.createAndBindUi(this));
		addTypePlatform();
		super.getConfirmButton().setVisible(false);
		super.getCloseButton().setVisible(false);
	}
	
	
	private void addTypePlatform() {
		lbPlatform.addItem(Config.PLATFORM_ANDROID, String.valueOf(Config.PLATFORM_ANDROID_VALUE));
		lbPlatform.addItem(Config.PLATFORM_IOS, String.valueOf(Config.PLATFORM_IOS_VALUE));
	}

	public Button getBtnAdd() {
		return btnAdd;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public TextBox getTbAppName() {
		return tbAppName;
	}

	public TextBox getTbPackageName() {
		return tbPackageName;
	}
	
	public ListBox getListBoxPlatform() {
		return lbPlatform;
	}

	public HTML getHTMLPackageName(){
		return htmlPackageName;
	}
}
