package com.ks.trackingapp.client.view.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.view.BHTouchImage;
import com.ks.trackingapp.client.view.VerticalTouchPanel;
import com.ks.trackingapp.shared.Config;

public class FilterPlatformView extends Composite {

	private static FilterPlatformViewUiBinder uiBinder = GWT
			.create(FilterPlatformViewUiBinder.class);

	interface FilterPlatformViewUiBinder extends
			UiBinder<Widget, FilterPlatformView> {
	}
	
	protected @UiField HTML htmlPlatform,htmlLanguage;
	protected @UiField FlowPanel flowImage;
	protected @UiField VerticalTouchPanel touchPlatform,touchLanguage;

	public FilterPlatformView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public HTML getHtmlPlatform() {
		return htmlPlatform;
	}
	
	public HTML getHtmlLanguage(){
		return htmlLanguage;
	}
	
	public VerticalTouchPanel getTouchFilter(){
		return touchPlatform;
	}
		
	public VerticalTouchPanel getTouchLanguage(){
		return touchLanguage;
	}

}
