package com.ks.trackingapp.client.view.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.view.KSDialogOverlay;

public class DialogDelete extends KSDialogOverlay {

	private static DialogDeleteUiBinder uiBinder = GWT
			.create(DialogDeleteUiBinder.class);

	interface DialogDeleteUiBinder extends UiBinder<Widget, DialogDelete> {
	}

	protected @UiField Button btYes,btNo;
	public DialogDelete() {
		super.add(uiBinder.createAndBindUi(this));
		this.setHideOnBackgroundClick(true);
	}
	
	
	
	public Button getButtonYes(){
		return btYes;
	}
	
	public Button getButtonNo(){
		return btNo;
	}


}
