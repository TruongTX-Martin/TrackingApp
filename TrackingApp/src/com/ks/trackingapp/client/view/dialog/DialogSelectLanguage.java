package com.ks.trackingapp.client.view.dialog;

import java.util.HashMap;
import java.util.Map;
import org.gwtbootstrap3.client.ui.Image;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
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
	touchGermany, touchSpanish, touchChinese, touchVietnam,touchFrench;

	protected @UiField
	Image imgEnglish,imgFrench, imgMalay, imgIndo, imgPortuguese, imgGermany, imgSpanish,
			imgChinese, imgVietnam;

	protected @UiField
	HTML htmlEnglish, htmlFrench, htmlMalay, htmlIndonesia, htmlPortuguese,
			htmlGermany, htmlSpanish, htmlChinese, htmlVietname;

	public DialogSelectLanguage() {
		super.add(uiBinder.createAndBindUi(this));
		this.setHideOnBackgroundClick(true);
		initView();
	}

	private void initView() {
		htmlEnglish.setText(Config.LANGUAGE_1ENGLISH);
		htmlFrench.setText(Config.LANGUAGE_2FRENCH);
		htmlMalay.setText(Config.LANGUAGE_3MALAY);
		htmlIndonesia.setText(Config.LANGUAGE_4INDO);
		htmlPortuguese.setText(Config.LANGUAGE_5PORTUGUESE);
		htmlGermany.setText(Config.LANGUAGE_6GERMANY);
		htmlSpanish.setText(Config.LANGUAGE_7SPANISH);
		htmlChinese.setText(Config.LANGUAGE_8CHINESE);
		htmlVietname.setText(Config.LANGUAGE_9VIETNAMESE);
		
		 mapTouchPanel.put(Config.LANGUAGE_1ENGLISH, touchEnglish);
		 mapTouchPanel.put(Config.LANGUAGE_2FRENCH, touchFrench);
		 mapTouchPanel.put(Config.LANGUAGE_3MALAY, touchMalay);
		 mapTouchPanel.put(Config.LANGUAGE_4INDO, touchIndo);
		 mapTouchPanel.put(Config.LANGUAGE_5PORTUGUESE, touchPortuge);
		 mapTouchPanel.put(Config.LANGUAGE_6GERMANY, touchGermany);
		 mapTouchPanel.put(Config.LANGUAGE_7SPANISH, touchSpanish);
		 mapTouchPanel.put(Config.LANGUAGE_8CHINESE, touchChinese);
		 mapTouchPanel.put(Config.LANGUAGE_9VIETNAMESE, touchVietnam);

		imgEnglish.setSize("25px", "25px");
		imgEnglish.setUrl("images/language/ic_english.png");
		
		imgFrench.setSize("25px", "25px");
		imgFrench.setUrl("images/language/ic_franch.png");

		imgMalay.setSize("25px", "25px");
		imgMalay.setUrl("images/language/ic_malay.png");

		imgIndo.setSize("25px", "25px");
		imgIndo.setUrl("images/language/ic_indo.png");

		imgPortuguese.setSize("25px", "25px");
		imgPortuguese.setUrl("images/language/ic_portuges.png");
		
		imgGermany.setSize("25px", "25px");
		imgGermany.setUrl("images/language/ic_germany.png");


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
