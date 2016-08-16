package com.ks.trackingapp.client.activity.allapp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;

public class AllAppViewImpl extends BasicViewImpl implements AllAppView {

	private static AllAppViewImplUiBinder uiBinder = GWT
			.create(AllAppViewImplUiBinder.class);

	interface AllAppViewImplUiBinder extends UiBinder<Widget, AllAppViewImpl> {
	}

	public AllAppViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button button;

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}

}
