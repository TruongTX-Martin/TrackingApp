package com.ks.trackingapp.client.activity.pulltorefresh;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowHeader;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel.Pullhandler;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl.LayoutBasic;
import com.ks.trackingapp.client.view.BhHeaderPanel;
import com.ks.trackingapp.client.view.leftmenu.LeftMenuSliding;

public class PullToRefreshImpl extends Composite implements PullToRefreshView{

	private static PullToRefreshImplUiBinder uiBinder = GWT
			.create(PullToRefreshImplUiBinder.class);

	interface PullToRefreshImplUiBinder extends
			UiBinder<Widget, PullToRefreshImpl> {
	}
	private PullPanel pullToRefresh = new PullPanel();
	private PullArrowHeader pullArrowHeader = new PullArrowHeader();
	protected @UiField FlowPanel mainPanel;

	public PullToRefreshImpl() {
		pullToRefresh.setHeader(pullArrowHeader);
		super.initWidget(uiBinder.createAndBindUi(this));
		mainPanel.add(pullToRefresh);
//		layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
//		mainPanel.add(pullToRefresh);

	}

	@Override
	public void setHeaderPullHandler(Pullhandler pullHandler) {
		pullToRefresh.setHeaderPullHandler(pullHandler);
	}

	@Override
	public void setFooterPullHandler(Pullhandler pullHandler) {
		pullToRefresh.setFooterPullHandler(pullHandler);
	}

	@Override
	public PullArrowWidget getPullHeader() {
		return pullArrowHeader;
	}


	@Override
	public void refresh() {
		pullToRefresh.refresh();
	}

	@Override
	public HasRefresh getPullPanel() {
		return pullToRefresh;
	}

	@Override
	public LayoutBasic getLayoutBasic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LeftMenuSliding getSlidingMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BhHeaderPanel getBhHeaderPanel() {
		// TODO Auto-generated method stub
		return null;
	}


}
