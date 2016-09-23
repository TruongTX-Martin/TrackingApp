package com.ks.trackingapp.client.view.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.view.BHTouchImage;
import com.ks.trackingapp.client.view.VerticalTouchPanel;

public class SettingView extends Composite {

	private static SettingViewUiBinder uiBinder = GWT
			.create(SettingViewUiBinder.class);

	interface SettingViewUiBinder extends UiBinder<Widget, SettingView> {
	}
	protected @UiField FlowPanel flowImage;
	protected @UiField VerticalTouchPanel touchPanel;
	private BHTouchImage imageFilter = new BHTouchImage("images/ic_setting.png");
	public SettingView() {
		initWidget(uiBinder.createAndBindUi(this));
		imageFilter.setSize("30px", "30px");
		flowImage.add(imageFilter);
	}
	public VerticalTouchPanel getTouchPanel(){
		return touchPanel;
	}

}
