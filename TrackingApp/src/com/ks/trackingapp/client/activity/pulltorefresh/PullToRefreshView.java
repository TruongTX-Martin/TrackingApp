package com.ks.trackingapp.client.activity.pulltorefresh;

import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel.Pullhandler;
import com.ks.trackingapp.client.activity.basic.BasicView;

public interface PullToRefreshView extends BasicView{

	public void setHeaderPullHandler(Pullhandler pullHandler);

	public void setFooterPullHandler(Pullhandler pullHandler);

	public PullArrowWidget getPullHeader();

	public void refresh();

	public HasRefresh getPullPanel();
}
