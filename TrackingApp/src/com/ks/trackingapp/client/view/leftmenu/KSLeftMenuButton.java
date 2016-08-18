package com.ks.trackingapp.client.view.leftmenu;

import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.ks.trackingapp.client.util.CssToken;
import com.ks.trackingapp.client.view.HorizontalTouchPanel;

public class KSLeftMenuButton extends HorizontalTouchPanel{
	
	private Image image;
	private HTML titleHTML;
	private HTML descriptionHTML;
	
	public KSLeftMenuButton(String imagUrl, String title, String description, int height) {
		this.setVerticalAlignment(ALIGN_MIDDLE);
		this.setWidth("100%");
		image = new Image();
		titleHTML = new HTML();
		descriptionHTML = new HTML();
		titleHTML.setText(title);
		image.setUrl(imagUrl);
		descriptionHTML.setText(description);
		if (imagUrl != null && !imagUrl.isEmpty())
			this.add(image);
		image.getElement().getStyle().setMarginLeft(10, Unit.PX);
		this.add(titleHTML);
		titleHTML.setStyleName("titleKsLeftMenuBtn");
		this.add(descriptionHTML);
		this.setCellWidth(image, CssToken.ICON_MEDIUM_SIZE + "px");
		setTitleCellWidth(70);
		this.setCellWidth(descriptionHTML, "18%");
		titleHTML.getElement().getStyle().setTextAlign(TextAlign.LEFT);
		setSizeIcon(CssToken.ICON_MEDIUM_SIZE, CssToken.ICON_MEDIUM_SIZE);
		titleHTML.getElement().getStyle().setColor("#ffffff");
		this.setHeight(height + "px");
	}
	
	public void setTitleCellWidth(int percent){
		this.setCellWidth(titleHTML, percent +"%");
	}
	
	public HTML getTitleHTML() {
		return titleHTML;
	}
	
	public void setSizeIcon(int height, int width){
		image.setPixelSize(width, height);
	}
	
	
	public void setDescription(String description){
		descriptionHTML.setText(description);
	}

}
