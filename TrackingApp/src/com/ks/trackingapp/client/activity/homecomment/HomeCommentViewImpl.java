package com.ks.trackingapp.client.activity.homecomment;

import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.util.ClientUtils;

public class HomeCommentViewImpl extends BasicViewImpl implements
		HomeCommentView {

	private static HomeCommentViewImplUiBinder uiBinder = GWT
			.create(HomeCommentViewImplUiBinder.class);

	interface HomeCommentViewImplUiBinder extends
			UiBinder<Widget, HomeCommentViewImpl> {
	}

	protected @UiField
	ScrollPanel scrollPanel;
	protected @UiField
	FlowPanel panelComment;
	protected @UiField
	TextBox textbox;
	private int HEIGHT_TEXTBOX = 50;

	public HomeCommentViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.layoutBasic.getHeaderPanel().showNavigation(true);
		refreshScrollView();
	}

	private void refreshScrollView() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				scrollPanel.setHeight((ClientUtils.getScreenHeight() - HEIGHT_TEXTBOX)
						+ "px");
			}
		});
	}

}
