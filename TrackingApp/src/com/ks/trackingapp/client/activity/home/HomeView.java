package com.ks.trackingapp.client.activity.home;

import java.util.List;
import java.util.Map;

import com.googlecode.mgwt.ui.client.widget.touch.TouchPanel;
import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.view.item.FilterLanguage;
import com.ks.trackingapp.client.view.item.ItemAppHome;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;

public interface HomeView extends BasicView{
	void showData(Map<ItemApp, List<ItemComment>> map);
	FilterLanguage getFilterLanguage();
	TouchPanel getTouchPanel();
	Map<ItemApp, ItemAppHome> getMapItemAppHome();
}
