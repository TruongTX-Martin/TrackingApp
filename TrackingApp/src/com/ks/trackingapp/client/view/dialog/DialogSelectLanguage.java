package com.ks.trackingapp.client.view.dialog;

import java.util.HashMap;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Image;

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
import com.ks.trackingapp.client.view.KSDialogOverlay;
import com.ks.trackingapp.client.view.VerticalTouchPanel;
import com.ks.trackingapp.shared.Config;

public class DialogSelectLanguage extends KSDialogOverlay {

	private static DialogSelectLanguageUiBinder uiBinder = GWT
			.create(DialogSelectLanguageUiBinder.class);

	interface DialogSelectLanguageUiBinder extends
			UiBinder<Widget, DialogSelectLanguage> {
	}

	private Map<String, VerticalTouchPanel> mapTouchPanel = new HashMap<String, VerticalTouchPanel>();

	protected @UiField
	VerticalTouchPanel touchEnglish, touchMalay, touchIndo, touchPortuge,
			touchBengali, touchArabic, touchRussian, touchSpanish,
			touchChinese, touchVietnam;

	protected @UiField
	Image imgEnglish, imgMalay, imgIndo, imgPortuguese, imgBengali, imgArabic,
			imgRussian, imgSpanish, imgChinese, imgVietnam;

	public DialogSelectLanguage() {
		super.add(uiBinder.createAndBindUi(this));
		this.setHideOnBackgroundClick(true);
		initView();
	}

	private void initView() {
		mapTouchPanel.put(Config.LANGUAGE_ENGLISH, touchEnglish);
		mapTouchPanel.put(Config.LANGUAGE_MALAY, touchMalay);
		mapTouchPanel.put(Config.LANGUAGE_INDO, touchIndo);
		mapTouchPanel.put(Config.LANGUAGE_PORTUGUESE, touchPortuge);
		mapTouchPanel.put(Config.LANGUAGE_BENGALI, touchBengali);
		mapTouchPanel.put(Config.LANGUAGE_ARABIC, touchArabic);
		mapTouchPanel.put(Config.LANGUAGE_RUSSIAN, touchRussian);
		mapTouchPanel.put(Config.LANGUAGE_SPANISH, touchSpanish);
		mapTouchPanel.put(Config.LANGUAGE_CHINESE, touchChinese);
		mapTouchPanel.put(Config.LANGUAGE_VIETNAMESE, touchVietnam);

		imgEnglish.setSize("25px", "25px");
		imgEnglish.setUrl("images/language/ic_english.png");
		
		imgMalay.setSize("25px", "25px");
		imgMalay.setUrl("images/language/ic_malay.png");
		
		imgIndo.setSize("25px", "25px");
		imgIndo.setUrl("images/language/ic_indo.png");
		
		imgPortuguese.setSize("25px", "25px");
		imgPortuguese.setUrl("images/language/ic_portuges.png");
		
		imgBengali.setSize("25px", "25px");
		imgBengali.setUrl("images/language/ic_bengali.png");
		
		imgArabic.setSize("25px", "25px");
		imgArabic.setUrl("images/language/ic_arabic.png");
		
		imgRussian.setSize("25px", "25px");
		imgRussian.setUrl("images/language/ic_russian.png");
		
		imgSpanish.setSize("25px", "25px");
		imgSpanish.setUrl("images/language/ic_spanish.png");
		
		imgChinese.setSize("25px", "25px");
		imgChinese.setUrl("images/language/ic_chinese.png");
		
		imgVietnam.setSize("25px", "25px");
		imgVietnam.setUrl("images/language/ic_vietnamese.png");
		
	}

	public Map<String, VerticalTouchPanel> getMapTouchPanel() {
		return mapTouchPanel;
	}

}
