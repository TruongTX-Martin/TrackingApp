package com.ks.trackingapp.client.view.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.client.view.BHTouchImage;
import com.ks.trackingapp.client.view.VerticalTouchPanel;
import com.ks.trackingapp.shared.model.ItemApp;

public class ItemAppView extends Composite {

	private static ItemAppViewUiBinder uiBinder = GWT
			.create(ItemAppViewUiBinder.class);

	interface ItemAppViewUiBinder extends UiBinder<Widget, ItemAppView> {
	}

	protected @UiField
	HTML htmlAppName, htmlAndroid, htmlIOS;
	protected @UiField
	FlowPanel panelAndroidRating, panelIOSRating,panelAndroidRate,panelIOSRate;
	protected @UiField Button btnMissAndroid,btnMissIOS;
	protected StarRating ratingIOS;
	protected @UiField VerticalTouchPanel mainPanel;
	protected @UiField Button btnEdit,btnDelete;
	public ItemAppView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void showView(ItemApp itemApp) {
		htmlAppName.setText(itemApp.getAppName());
		StarRating ratingAndroid = new StarRating((int)itemApp.getRating(), 5);
		StarRating ratingIOS = new StarRating((int)itemApp.getRating(), 5);
		if(itemApp.isAndroid()){
			btnMissAndroid.setVisible(false);
		}else{
			btnMissAndroid.setVisible(true);
		}
		
		if(itemApp.isIOS()){
			btnMissIOS.setVisible(false);
		}else{
			btnMissIOS.setVisible(true);
		}
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
	
	public Button getButtonMissAndroid(){
		return btnMissAndroid;
	}
	public Button getButtonMissIOS(){
		return btnMissIOS;
	}
	public Button getButtonEdit(){
		return btnEdit;
	}
	public Button getButtonDelete(){
		return btnDelete;
	}
}
