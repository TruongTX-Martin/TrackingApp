package com.ks.trackingapp.client.activity.basic;

import com.google.gwt.user.client.ui.IsWidget;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl.LayoutBasic;
import com.ks.trackingapp.client.view.BhHeaderPanel;
import com.ks.trackingapp.client.view.leftmenu.LeftMenuSliding;

public interface BasicView extends IsWidget{

	LayoutBasic getLayoutBasic();
	LeftMenuSliding getSlidingMenu();
	BhHeaderPanel getBhHeaderPanel();
}
