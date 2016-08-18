package com.ks.trackingapp.client.view.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.view.VerticalTouchPanel;
import com.ks.trackingapp.shared.model.ItemApp;

public class ItemAppView extends Composite {

	private static ItemAppViewUiBinder uiBinder = GWT
			.create(ItemAppViewUiBinder.class);

	interface ItemAppViewUiBinder extends UiBinder<Widget, ItemAppView> {
	}

	protected @UiField
	HTML htmlAppName, htmlAndroid, htmlIOS,htmlIOSRate,htmlAndroidRate;
	protected @UiField
	FlowPanel panelAndroidRating, panelIOSRating;
	protected StarRating ratingIOS;
	protected @UiField VerticalTouchPanel mainPanel;

	public ItemAppView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void showView(ItemApp itemApp) {
		htmlAppName.setText(itemApp.getAppName());
		StarRating ratingAndroid = new StarRating((int)itemApp.getRating(), 5);
		StarRating ratingIOS = new StarRating((int)itemApp.getRating(), 5);
		htmlAndroidRate.setText((int)itemApp.getRating()+"/5");
		htmlIOSRate.setText((int)itemApp.getRating()+"/5");
		panelAndroidRating.add(ratingAndroid);
		panelIOSRating.add(ratingIOS);
	}
	public void showViewi(ItemApp itemApp,int i) {
		htmlAppName.setText(itemApp.getAppName() + i);
		StarRating ratingAndroid = new StarRating(3, 5);
		StarRating ratingIOS = new StarRating(3, 5);
		panelAndroidRating.add(ratingAndroid);
		panelIOSRating.add(ratingIOS);
	}
	
	public VerticalTouchPanel getMainPanel(){
		return mainPanel;
	}
}
