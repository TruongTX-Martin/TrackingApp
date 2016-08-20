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

public class FilterLanguage extends Composite{

	private static FilterLanguageUiBinder uiBinder = GWT
			.create(FilterLanguageUiBinder.class);

	interface FilterLanguageUiBinder extends UiBinder<Widget, FilterLanguage> {
	}
	protected @UiField HTML htmlFilter;
	protected @UiField FlowPanel flowImage;
	protected @UiField VerticalTouchPanel touchPanel;
	private BHTouchImage imageFilter = new BHTouchImage("images/ic_language.png");

	public FilterLanguage() {
		initWidget(uiBinder.createAndBindUi(this));
		htmlFilter.setText(Config.LANGUAGE_ENGLISH);
		imageFilter.setSize("30px", "30px");
		flowImage.add(imageFilter);
	}
	
	public HTML getHTMLFilter(){
		return htmlFilter;
	}
	
	public VerticalTouchPanel getTouchPanel(){
		return touchPanel;
	}
	public void setImageLanguageSource(String language){
		if(language.equals(Config.LANGUAGE_ENGLISH)) {
			imageFilter = new BHTouchImage("images/language/ic_english.png");
		}else if(language.equals(Config.LANGUAGE_MALAY)) {
			imageFilter = new BHTouchImage("images/language/ic_malay.png");
		}else if(language.equals(Config.LANGUAGE_INDO)) {
			imageFilter = new BHTouchImage("images/language/ic_indo.png");
		}else if(language.equals(Config.LANGUAGE_PORTUGUESE)) {
			imageFilter = new BHTouchImage("images/language/ic_portuges.png");
		}else if(language.equals(Config.LANGUAGE_BENGALI)) {
			imageFilter = new BHTouchImage("images/language/ic_bengali.png");
		}else if(language.equals(Config.LANGUAGE_ARABIC)) {
			imageFilter = new BHTouchImage("images/language/ic_arabic.png");
		}else if(language.equals(Config.LANGUAGE_RUSSIAN)) {
			imageFilter = new BHTouchImage("images/language/ic_russian.png");
		}else if(language.equals(Config.LANGUAGE_SPANISH)) {
			imageFilter = new BHTouchImage("images/language/ic_spanish.png");
		}else if(language.equals(Config.LANGUAGE_CHINESE)) {
			imageFilter = new BHTouchImage("images/language/ic_chinese.png");
		}else if(language.equals(Config.LANGUAGE_VIETNAMESE)) {
			imageFilter = new BHTouchImage("images/language/ic_vietnamese.png");
		}
		imageFilter.setSize("30px", "30px");
		flowImage.clear();
		flowImage.add(imageFilter);
	}
	


}
