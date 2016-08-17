package com.ks.trackingapp.client.activity.appcomment;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.view.BhHeaderPanel;
import com.ks.trackingapp.client.view.item.ItemCommentView;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemComment;

public class AppCommentViewImpl extends BasicViewImpl implements AppCommentView {

	private static AppCommentViewImplUiBinder uiBinder = GWT
			.create(AppCommentViewImplUiBinder.class);

	interface AppCommentViewImplUiBinder extends
			UiBinder<Widget, AppCommentViewImpl> {
	}

	private int HEIGHT_TEXTBOX = 50;
	protected @UiField
	TextBox textbox;
	protected @UiField
	ScrollPanel scrollPanel;
	protected @UiField
	FlowPanel panelComment;

	public AppCommentViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.layoutBasic.getHeaderPanel().showNavigation(false);
		this.layoutBasic.getHeaderPanel().setCenter(Config.ITEMSCREEN_APPCOMMENT);
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
	

	@Override
	public void showCommentApp(ArrayList<ItemComment> list) {
		panelComment.clear();
		if(list == null || list.size() == 0) {
			HTML html = new HTML("Your app don't have any comment.");
			html.getElement().getStyle().setTextAlign(TextAlign.CENTER);
			html.getElement().getStyle().setPadding(10, Unit.PX);
			html.getElement().getStyle().setFontSize(1.4, Unit.EM);
			html.getElement().getStyle().setColor("#ffffff");
			panelComment.add(html);
			return;
		}else {
			for (int i=0; i< list.size() ; i++){
				ItemComment comment = list.get(i);
				ItemCommentView commentView = new ItemCommentView();
				commentView.showView(comment);
				panelComment.add(commentView);
			}
			
		}
	}
	
	@Override
	public BhHeaderPanel getBhHeaderPanel() {
		return this.layoutBasic.getHeaderPanel();
	}
	

}
