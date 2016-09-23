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

public class FilterView extends Composite  {

	private static FilterViewUiBinder uiBinder = GWT
			.create(FilterViewUiBinder.class);

	interface FilterViewUiBinder extends UiBinder<Widget, FilterView> {
	}

	protected @UiField HTML htmlFilter;
//	protected @UiField FlowPanel flowImage;
	protected @UiField VerticalTouchPanel touchPanel;
	private BHTouchImage imageFilter = new BHTouchImage("images/ic_filter.png");
	public FilterView() {
		initWidget(uiBinder.createAndBindUi(this));
		imageFilter.setSize("30px", "30px");
//		flowImage.add(imageFilter);
		htmlFilter.setText(Config.FILTERBY_ALL);
	}
	
	
	
	public VerticalTouchPanel getTouchPanel(){
		return touchPanel;
	}
	
	public HTML getHTMLFilter(){
		return htmlFilter;
	}


}
