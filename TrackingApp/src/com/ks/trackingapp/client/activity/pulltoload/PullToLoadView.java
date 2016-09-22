package com.ks.trackingapp.client.activity.pulltoload;

import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowHeader;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel;
import com.ks.trackingapp.client.activity.basic.BasicView;

public interface PullToLoadView extends BasicView{
	
	PullPanel getPullToRefreshPlay();
	PullArrowHeader getPullHeader();
}
