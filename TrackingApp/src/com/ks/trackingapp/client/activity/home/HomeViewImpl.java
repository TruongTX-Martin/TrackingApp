package com.ks.trackingapp.client.activity.home;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.appcomment.AppCommentPlace;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.view.KSScrollPanel;
import com.ks.trackingapp.client.view.item.FilterLanguage;
import com.ks.trackingapp.client.view.item.ItemAppHome;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;

public class HomeViewImpl extends BasicViewImpl implements HomeView {

	private static HomeViewImplUiBinder uiBinder = GWT
			.create(HomeViewImplUiBinder.class);

	interface HomeViewImplUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}
	protected @UiField KSScrollPanel panelScroll;
	protected @UiField FlowPanel flowComment;
	private FilterLanguage filterLanguage = new FilterLanguage();

	public HomeViewImpl() {
		this.layoutBasic.getHeaderPanel().showNavigation(true);
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		filterLanguage.getHTMLFilter().setText(Config.LANGUAGE_1ENGLISH);
		filterLanguage.setImageLanguageSource(Config.LANGUAGE_1ENGLISH);
		this.layoutBasic.getHeaderPanel().getRightPanel().add(filterLanguage);
		refreshScrollView();
	}

	private void refreshScrollView() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				panelScroll.setHeight((ClientUtils.getScreenHeight())
						+ "px");
			}
		});
	}

	@Override
	public void showData(Map<ItemApp, List<ItemComment>> map) {
		flowComment.clear();
		if(map != null && map.size() > 0) {
			for (Map.Entry<ItemApp, List<ItemComment>> entry : map.entrySet()){
				final ItemApp itemApp  = entry.getKey();
				List<ItemComment> list = entry.getValue();
				final ItemAppHome itemView = new ItemAppHome();
				itemView.getTouchExpand().addTapHandler(new TapHandler() {
					
					@Override
					public void onTap(TapEvent event) {
						if(itemView.getFlowComment().isVisible()){
							itemView.getFlowComment().setVisible(false);
							itemView.getImageExpand().setUrl("images/ic_expand.png");
						}else{
							itemView.getFlowComment().setVisible(true);
							itemView.getImageExpand().setUrl("images/ic_expand_close.png");
						}
					}
				});
				itemView.getTouchDetail().addTapHandler(new TapHandler() {
					
					@Override
					public void onTap(TapEvent event) {
						TrackingApp.getClientFactory().getPlaceController().goTo(new AppCommentPlace(itemApp.getId()));
					}
				});
				itemView.showData(itemApp, list);
				flowComment.add(itemView);
			}
		}else {
			
		}
	}

	@Override
	public FilterLanguage getFilterLanguage() {
		return filterLanguage;
	}

}
