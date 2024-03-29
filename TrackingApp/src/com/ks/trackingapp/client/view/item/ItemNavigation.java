package com.ks.trackingapp.client.view.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.view.VerticalTouchPanel;

public class ItemNavigation extends Composite {

	private static ItemNavigationUiBinder uiBinder = GWT
			.create(ItemNavigationUiBinder.class);

	interface ItemNavigationUiBinder extends UiBinder<Widget, ItemNavigation> {
	}

	protected @UiField Image imgIcon,imgExtend;
	protected @UiField HTML htmlTitle;
	protected @UiField VerticalTouchPanel touchPanel;
	public ItemNavigation(String src,String title) {
		initWidget(uiBinder.createAndBindUi(this));
		imgIcon.setUrl(src);
		htmlTitle.setText(title);
		imgExtend.setSize("15px", "15px");
		imgExtend.setUrl("images/ic_extend.png");
	}

	public void setText(String text){
		htmlTitle.setText(text);
		htmlTitle.getElement().getStyle().setWidth(20, Unit.PX);
		htmlTitle.getElement().getStyle().setHeight(20, Unit.PX);
		imgExtend.setVisible(false);
	}
	
	public VerticalTouchPanel getTouchPanel(){
		return touchPanel;
	}


}
