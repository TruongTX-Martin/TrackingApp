package com.ks.trackingapp.client.activity.appcomment;

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
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.input.search.MSearchBox;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowHeader;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.view.BhHeaderPanel;
import com.ks.trackingapp.client.view.item.FilterLanguage;
import com.ks.trackingapp.client.view.item.FilterView;
import com.ks.trackingapp.client.view.item.ItemCommentView;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemComment;

public class AppCommentViewImpl extends BasicViewImpl implements AppCommentView {

	private static AppCommentViewImplUiBinder uiBinder = GWT
			.create(AppCommentViewImplUiBinder.class);

	interface AppCommentViewImplUiBinder extends
			UiBinder<Widget, AppCommentViewImpl> {
	}
	protected @UiField
	MSearchBox searchbox;
	protected @UiField
	ScrollPanel scrollPanel;
	protected @UiField
	FlowPanel panelComment,flowBottom;
	protected @UiField FlowPanel flowBottomLeft,flowBottomRight,flowBottonCenter;
	protected @UiField PullPanel pullPanel;
	private PullArrowHeader arrowHeader = new PullArrowHeader();
	
	private FilterView filterView = new FilterView();
	private FilterLanguage filterLanguage = new FilterLanguage();

	public AppCommentViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.layoutBasic.getHeaderPanel().showNavigation(false);
		this.layoutBasic.getHeaderPanel().setCenter(Config.ITEMSCREEN_APPCOMMENT);
		this.layoutBasic.getHeaderPanel().getRightPanel().getElement().getStyle().setMarginRight(10, Unit.PX);
		filterView.getHTMLFilter().setText(Config.FILTERBY_ALL);
		filterLanguage.setImageLanguageSource(Config.LANGUAGE_1ENGLISH);
		flowBottomLeft.add(filterView);
		flowBottomRight.add(filterLanguage);
		refreshScrollView();
		scrollPanel.setBounce(false);
		pullPanel.setHeader(arrowHeader);
	}

	private void refreshScrollView() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				scrollPanel.setHeight((ClientUtils.getScreenHeight() - searchbox.getOffsetHeight() - flowBottom.getOffsetHeight())
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
				commentView.showCommentApp(comment);
				panelComment.add(commentView);
			}
			
		}
	}
	
	@Override
	public BhHeaderPanel getBhHeaderPanel() {
		return this.layoutBasic.getHeaderPanel();
	}

	@Override
	public MSearchBox getSearchBox() {
		return searchbox;
	}

	@Override
	public FilterLanguage getFilterLanguage() {
		return filterLanguage;
	}

	@Override
	public FilterView getFilterView() {
		return filterView;
	}

	@Override
	public ScrollPanel getScrollPanel() {
		return scrollPanel;
	}

	@Override
	public PullPanel getPullPanel() {
		return pullPanel;
	}

	@Override
	public PullArrowHeader getPullHeader() {
		return arrowHeader;
	}


}
