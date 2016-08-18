package com.ks.trackingapp.client.view.leftmenu;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.mgwt.ui.client.util.CssUtil;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.shared.AppConfig;

public class LeftMenuSliding extends SlidingPanel{
	private int heghtButton = 50;
	
	private KSLeftMenuButton btnHome = new KSLeftMenuButton("images/ic_home.png", "Home", "",heghtButton);
	private KSLeftMenuButton btnAllApp = new KSLeftMenuButton("images/ic_allapp.png", "All App", "",heghtButton);
	private KSLeftMenuButton btnAddApp = new KSLeftMenuButton("images/ic_allapp.png", "Add App", "",heghtButton);
	private KSLeftMenuButton btnLogout = new KSLeftMenuButton("images/ic_signout.png", "Logout", "",heghtButton);
	private VerticalPanel mainTopicsPanel;
	private HorizontalPanel userInfoPanel;
	
	public LeftMenuSliding() {
		super(ClientUtils.getScreenWidth() * 3/5);
		this.setStyleName("registerSliding", true);
		mainPanel.setSpacing(0);
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		scrollPanel.getElement().getStyle().setBackgroundColor(AppConfig.TEXT_COLOR_LEFTMENU);
		setPixelSize(this.widthPanel, ClientUtils.getScreenHeight() + 50);
		userInfoPanel = new HorizontalPanel();
		userInfoPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		userInfoPanel.setWidth("100%");
		mainTopicsPanel = new VerticalPanel();
		mainPanel.add(userInfoPanel);
//		mainPanel.add(btnHome);
		mainPanel.add(btnAddApp);
		mainPanel.add(btnAllApp);
		mainPanel.add(btnLogout);
		mainPanel.add(mainTopicsPanel);
		mainTopicsPanel.setWidth("100%");
		scrollPanel.refresh();
		hide();
		this.getElement().getStyle().setBackgroundColor("#152727");
	}
	
	@Override
	public void show() {
		mainTopicsPanel.clear();
		refreshView();
		scrollPanel.setHeight(ClientUtils.getScreenHeight() + 50+ "px");
		scrollPanel.refresh();
		CssUtil.translate(RootPanel.get().getElement(), widthPanel, 0);
		CssUtil.setTransitionDuration(RootPanel.get().getElement(), 200);
		super.show();
	}
	
	public void show(boolean isShowFormUpdateInfo) {
		show();
	}
	
	@Override
	public void hide() {
		super.hide();
		CssUtil.translate(RootPanel.get().getElement(),0, 0);
		CssUtil.setTransitionDuration(RootPanel.get().getElement(), 200);
	}
	
	@Override
	protected void refreshView() {
	}

	@Override
	protected void setPosition() {
		this.getElement().getStyle().setPosition(Position.ABSOLUTE);
		this.getElement().getStyle().setTop(0, Unit.PX);
		this.getElement().getStyle().setLeft(-widthPanel, Unit.PX);
	}
	public KSLeftMenuButton getButtonHome(){
		return btnHome;
	}
	
	public KSLeftMenuButton getButtonAddApp() {
		return btnAddApp;
	}
	
	public KSLeftMenuButton getButtonAllApp() {
		return btnAllApp;
	}
	
	public KSLeftMenuButton getButtonLogout() {
		return btnLogout;
	}
	
}
