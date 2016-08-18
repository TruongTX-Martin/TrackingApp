package com.ks.trackingapp.client.view.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.view.KSDialogOverlay;
import com.ks.trackingapp.shared.AppConfig;

public class DialogOpenCommentApp extends KSDialogOverlay{

	private static DialogOpenCommentAppUiBinder uiBinder = GWT
			.create(DialogOpenCommentAppUiBinder.class);

	interface DialogOpenCommentAppUiBinder extends
			UiBinder<Widget, DialogOpenCommentApp> {
	}

	@UiField
	Button btNo;
	@UiField
	Button btYes;
	
	@UiField HTML txtTitle;
	public DialogOpenCommentApp() {
		super();
		super.add(uiBinder.createAndBindUi(this));
		setContent();
	}
	
	private void setContent(){
		txtTitle.getElement().getStyle().setColor(AppConfig.THEME_COLOR);
		btNo.getElement().getStyle().setBackgroundColor(AppConfig.THEME_COLOR);
		btYes.getElement().getStyle().setBackgroundColor(AppConfig.THEME_COLOR);
	}

	public Button getBtNo() {
		return btNo;
	}

	public Button getBtYes() {
		return btYes;
	}
}
