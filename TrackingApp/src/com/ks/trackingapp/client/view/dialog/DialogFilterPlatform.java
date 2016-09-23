package com.ks.trackingapp.client.view.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.view.KSDialogOverlay;
import com.ks.trackingapp.client.view.VerticalTouchPanel;

public class DialogFilterPlatform extends KSDialogOverlay {

	private static DialogFilterPlatformUiBinder uiBinder = GWT
			.create(DialogFilterPlatformUiBinder.class);

	interface DialogFilterPlatformUiBinder extends
			UiBinder<Widget, DialogFilterPlatform> {
	}
	


	protected @UiField
	VerticalTouchPanel touchAll, touchAndroid, touchIOS, touchDate,
			touchRating;

	public DialogFilterPlatform() {
		super.add(uiBinder.createAndBindUi(this));
		this.setHideOnBackgroundClick(true);
		
	}

	public VerticalTouchPanel getTouchAll() {
		return touchAll;
	}

	public VerticalTouchPanel getTouchAndroid() {
		return touchAndroid;
	}

	public VerticalTouchPanel getTouchIOS() {
		return touchIOS;
	}

	public VerticalTouchPanel getTouchDate() {
		return touchDate;
	}

	public VerticalTouchPanel getTouchRating() {
		return touchRating;
	}

}
