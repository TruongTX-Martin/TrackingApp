package com.ks.trackingapp.client.view;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.resource.BHClientBundleBaseTheme;
import com.ks.trackingapp.client.util.ClientUtils;

public class BhHeaderPanel extends HorizontalPanel {
	private HorizontalPanel leftPanel = new HorizontalPanel();
	private HorizontalPanel centerPanel = new HorizontalPanel();
	private HorizontalPanel rightPanel = new HorizontalPanel();
//	private BHTouchImage backButton = new BHTouchImage(BHClientBundleBaseTheme.IMPL.getBHMGWTClientBundle().back());
//	private BHTouchImage leftMenuButton = new BHTouchImage(BHClientBundleBaseTheme.IMPL.getBHMGWTClientBundle().menu(),
//			BHClientBundleBaseTheme.IMPL.getBHMGWTClientBundle().menu_active());
	
	protected BHTouchImage btnBack = new BHTouchImage("images/ic_back_white.png");
	protected BHTouchImage btnNavigation = new BHTouchImage("images/ic_navigation.png"); 
	
	public static final int height = 50;

	public BhHeaderPanel() {
		this.addStyleName("bh-header-panel");
		this.setHeight(height + "px");
		btnBack.setPixelSize(40,40);
		btnNavigation.setPixelSize(height,height);
		leftPanel.setWidth("50px");
		rightPanel.setWidth("100px");
		leftPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		rightPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		rightPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		centerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		centerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		leftPanel.setHeight(height + "px");
		centerPanel.setHeight(height + "px");
		rightPanel.setHeight(height + "px");
		centerPanel.setWidth("100%");
		this.add(leftPanel);
		this.add(centerPanel);
		this.add(rightPanel);
		this.setCellWidth(leftPanel, "60px");
		this.setCellWidth(rightPanel, "60px");
		this.setCellHorizontalAlignment(rightPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		leftPanel.add(btnBack);
		leftPanel.add(btnNavigation);
//		rightPanel.getElement().getStyle().setProperty("borderLeft", "1px solid #C78100");
//		leftPanel.getElement().getStyle().setProperty("borderRight", "1px solid #C78100");
		//leftPanel.add(homeButton);
		this.setWidth("100%");
		//notification.setSize("30px", "30px");
	}

//	public static ChatWindow getChatPanel() {
//		if (chatWindow == null)
//			chatWindow = new ChatWindow();
//		return chatWindow;
//	}
	
	public void	setLeftWidget(Widget left) {
		leftPanel.clear();
		leftPanel.add(left);
	}
	public void	setRightWidget(Widget right) {
		rightPanel.clear();
		rightPanel.add(right);
	}
	
	public void showNavigation(boolean isNavigation){
		if(isNavigation) {
			btnNavigation.setVisible(true);
			btnBack.setVisible(false);
		}else{
			btnNavigation.setVisible(false);
			btnBack.setVisible(true);
		}
	}
	public void hideAllButton(){
		btnNavigation.setVisible(false);
		btnBack.setVisible(false);
	}
	
	public void setCenter(String text) {
		centerPanel.clear();
		centerPanel.add(new HTML("<div style='color: #ffffff;font-size:25px; text-overflow: ellipsis;overflow: hidden;white-space: nowrap !important; max-width:"+(ClientUtils.getScreenWidth()-120)+"px;'>" + text + "</div>"));
	}

	public HorizontalPanel getLeftPanel() {
		return leftPanel;
	}

	public HorizontalPanel getCenterPanel() {
		return centerPanel;
	}

	public HorizontalPanel getRightPanel() {
		return rightPanel;
	}

	public BHTouchImage getButtonBack() {
		return btnBack;
	}

	public BHTouchImage getButtonNavigation() {
		return btnNavigation;
	}

}