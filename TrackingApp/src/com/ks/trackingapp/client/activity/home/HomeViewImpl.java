package com.ks.trackingapp.client.activity.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.touch.TouchPanel;
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
	protected @UiField TouchPanel touchMainPanel;
	private FilterLanguage filterLanguage = new FilterLanguage();
	private Map<ItemApp, ItemAppHome> mapItemApp = new HashMap<ItemApp, ItemAppHome>();

	public HomeViewImpl() {
		this.layoutBasic.getHeaderPanel().showNavigation(true);
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.layoutBasic.getHeaderPanel().setCenter("Home");
		filterLanguage.getHTMLFilter().setText(Config.LANGUAGE_1ENGLISH);
		filterLanguage.setImageLanguageSource(Config.LANGUAGE_1ENGLISH);
		filterLanguage.showImage(false);
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
		mapItemApp.clear();
		if(map != null && map.size() > 0) {
			for (Map.Entry<ItemApp, List<ItemComment>> entry : map.entrySet()){
				final ItemApp itemApp  = entry.getKey();
				List<ItemComment> list = entry.getValue();
				final ItemAppHome itemView = new ItemAppHome();
				itemView.showData(itemApp, list);
				flowComment.add(itemView);
				mapItemApp.put(itemApp, itemView);
			}
			panelScroll.refresh();
		}else {
			ClientUtils.showHTML("Your system app don't have any comment.", flowComment);
		}
	}

	@Override
	public FilterLanguage getFilterLanguage() {
		return filterLanguage;
	}

	@Override
	public TouchPanel getTouchPanel() {
		return touchMainPanel;
	}

	@Override
	public Map<ItemApp, ItemAppHome> getMapItemAppHome() {
		return mapItemApp;
	}

}
