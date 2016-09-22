package com.ks.trackingapp.client.activity.pulltoload;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowHeader;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.view.KSPullArrowFooter;
import com.ks.trackingapp.client.view.KSPullArrowHeader;
import com.ks.trackingapp.client.view.KSPullPanel;

public class PullToLoadViewImpl extends BasicViewImpl implements PullToLoadView {

	private static PullToLoadViewImplUiBinder uiBinder = GWT
			.create(PullToLoadViewImplUiBinder.class);

	interface PullToLoadViewImplUiBinder extends
			UiBinder<Widget, PullToLoadViewImpl> {
	}
	protected @UiField FlowPanel mainPanel;
	private PullPanel pullToRefresh = new PullPanel();
	private PullArrowHeader arrowHeader = new PullArrowHeader();
	

	public PullToLoadViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.layoutBasic.getHeaderPanel().showNavigation(false);
		pullToRefresh.getElement().getStyle().setProperty("width", "100%");
		pullToRefresh.getElement().getStyle().setProperty("height", "100%");
		pullToRefresh.addStyleName("pullToRefresh");
		pullToRefresh.setHeader(arrowHeader);
		mainPanel.add(pullToRefresh);
	}

	@Override
	public PullPanel getPullToRefreshPlay() {
		return pullToRefresh;
	}

	@Override
	public PullArrowHeader getPullHeader() {
		return arrowHeader;
	}


}
