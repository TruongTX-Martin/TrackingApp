package com.ks.trackingapp.client.activity.homecomment;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.input.search.MSearchBox;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.view.item.FilterPlatformView;
import com.ks.trackingapp.client.view.item.ItemCommentView;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemComment;

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
	MSearchBox textbox;
	private int HEIGHT_TEXTBOX = 50;
	private FilterPlatformView filterPlatformView = new FilterPlatformView();

	public HomeCommentViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.layoutBasic.getHeaderPanel().showNavigation(true);
		this.layoutBasic.getHeaderPanel().getRightPanel().setWidth("120px");
		this.layoutBasic.getHeaderPanel().setCenter(Config.ITEMSCREEN_HOME);
		this.layoutBasic.getHeaderPanel().getRightPanel().add(filterPlatformView);
		filterPlatformView.getHtmlPlatform().setText(Config.PLATFORM_ALL);
		filterPlatformView.getHtmlLanguage().setText(Config.LANGUAGE_ENGLISH);
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
	public void showItemComment(ArrayList<ItemComment> list) {
		panelComment.clear();
		if(list == null || list.size() == 0) {
			HTML html = new HTML("Your app don't have any comment.");
			html.getElement().getStyle().setTextAlign(TextAlign.CENTER);
			html.getElement().getStyle().setPadding(10, Unit.PX);
			html.getElement().getStyle().setFontSize(1.4, Unit.EM);
			html.getElement().getStyle().setColor("#ffffff");
			panelComment.add(html);
			return;
		}else{
			for (int i=0; i< list.size(); i++){
				ItemComment itemComment = list.get(i);
				ItemCommentView itemView = new ItemCommentView();
				itemView.showView(itemComment);
				panelComment.add(itemView);
			}
		}
	}

	@Override
	public FilterPlatformView getFilterPlatformView() {
		return filterPlatformView;
	}

	@Override
	public MSearchBox getTextBoxSearch() {
		return textbox;
	}

}
