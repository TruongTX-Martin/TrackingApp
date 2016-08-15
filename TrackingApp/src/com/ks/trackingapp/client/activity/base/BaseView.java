package com.ks.trackingapp.client.activity.base;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.ks.trackingapp.client.activity.base.BaseViewImpl.Layout;

public interface BaseView extends IsWidget {
	Layout getLayout();

	FlowPanel getContentPanel();

	void refreshView();

	int getViewId();
}
