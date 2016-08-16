package com.ks.trackingapp.client.view.leftmenu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.util.CssToken;
import com.ks.trackingapp.client.view.BhHeaderPanel;
import com.ks.trackingapp.client.view.VerticalTouchPanel;
import com.ks.trackingapp.shared.AppConfig;

public class SlidingPanel extends FlowPanel {
	private SlidingPanelUiBinder uiBinder = GWT
			.create(SlidingPanelUiBinder.class);
	
	interface SlidingPanelUiBinder extends UiBinder<Widget, SlidingPanel> {

	}
	@UiField protected VerticalTouchPanel slidingPanel;
	@UiField protected ScrollPanel scrollPanel;
	@UiField protected VerticalPanel mainPanel;
	protected boolean isShowing = false;
	protected static SlidingPanel currentSliding = null;
	protected int widthPanel = 0;
	public SlidingPanel() {
		super();
		this.add(uiBinder.createAndBindUi(this));
		scrollPanel.setScrollingEnabledX(false);
		scrollPanel.setShowHorizontalScrollBar(false);
		scrollPanel.setShowVerticalScrollBar(false);
		scrollPanel.setBounce(false);
		slidingPanel.getElement().getStyle().setColor(AppConfig.TEXT_COLOR_LEFTMENU);
		scrollPanel.getElement().getStyle().setBackgroundColor(AppConfig.TEXT_COLOR_LEFTMENU);
		this.setWidth("100%");
		this.setVisible(false);
		this.getElement().getStyle().setZIndex(999);
		setPosition();
		addHandler();
	}

	private void addHandler() {
		scrollPanel.addScrollEndHandler(new ScrollEndEvent.Handler() {
			
			@Override
			public void onScrollEnd(ScrollEndEvent event) {
				scrollPanel.refresh();
			}
		});
	}
	
	public SlidingPanel(int width) {
		this.widthPanel = width;
		this.add(uiBinder.createAndBindUi(this));
		scrollPanel.setScrollingEnabledX(false);
		scrollPanel.setShowHorizontalScrollBar(false);
		scrollPanel.setShowVerticalScrollBar(false);
		scrollPanel.setBounce(false);
		this.setWidth("100%");
		this.setVisible(false);
		this.getElement().getStyle().setZIndex(999);
		setPosition();
		addHandler();
		setSize();
	}
	
	public void show() {
		this.setVisible(true);
		isShowing  = true;
		if(currentSliding!=null) 
			currentSliding.hide();
		currentSliding = this;
		this.setHeight((ClientUtils.getScreenHeight() + 50 )+ "px");
		scrollPanel.refresh();
	}
	
	public void hide() {
		if(isShowing) {
			this.setVisible(false);
			isShowing  = false;
			currentSliding = null;
		}
	}

	public void hideAll() {
		
	}
	
	protected void refreshView() {
		scrollPanel.refresh();
	}
	
	public boolean isShowing() {
		return isShowing;
	}
	
	protected String getAnimationCss() {
		return CssToken.SLIDE_LEFT;
	}
	
	protected void setSize(int width) {
		this.widthPanel = width;
		setSize();
	}
	
	protected void setSize() {
		scrollPanel.setWidth(widthPanel + "px");
		scrollPanel.refresh();
	}
	
	protected void setPosition() {
		this.getElement().getStyle().setPosition(Position.ABSOLUTE);
		this.getElement().getStyle().setTop(BhHeaderPanel.height+2, Unit.PX);
	}

	public static SlidingPanel getCurrentSliding() {
		return currentSliding;
	}
	
	public static boolean hideSliding() {
		if (currentSliding != null && currentSliding.isShowing) {
			currentSliding.hide();
			return true;
		}
		return false;
	}

}