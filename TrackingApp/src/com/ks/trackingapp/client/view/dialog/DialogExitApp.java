package com.ks.trackingapp.client.view.dialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.view.KSDialogOverlay;

public class DialogExitApp extends KSDialogOverlay{

	private static DialogExitAppUiBinder uiBinder = GWT
			.create(DialogExitAppUiBinder.class);

	interface DialogExitAppUiBinder extends UiBinder<Widget, DialogExitApp> {
	}
	@UiField
	Button btNo;
	@UiField
	Button btYes;
	
	@UiField HTML txtTitle;

	public DialogExitApp() {
		super();
		super.add(uiBinder.createAndBindUi(this));
	}
	
	public Button getBtNo() {
		return btNo;
	}

	public Button getBtYes() {
		return btYes;
	}



}
