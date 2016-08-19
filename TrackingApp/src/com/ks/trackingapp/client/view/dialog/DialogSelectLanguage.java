package com.ks.trackingapp.client.view.dialog;

import java.util.HashMap;
import java.util.Map;

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

	public DialogSelectLanguage() {
		super.add(uiBinder.createAndBindUi(this));
		this.setHideOnBackgroundClick(true);
		initView();
	}
	
	private void initView(){
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
	}
	
	public Map<String, VerticalTouchPanel> getMapTouchPanel(){
		return mapTouchPanel;
	}

}
