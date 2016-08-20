package com.ks.trackingapp.client.activity.allapp;

import java.util.ArrayList;
import java.util.Map;

import org.gwtbootstrap3.client.ui.TextBox;

import com.googlecode.mgwt.ui.client.widget.input.search.MSearchBox;
import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.view.BHTouchImage;
import com.ks.trackingapp.client.view.item.ItemAppView;
import com.ks.trackingapp.shared.model.ItemApp;

public interface AllAppView extends BasicView{
	
	void showItemApp(ArrayList<ItemApp> list);
	MSearchBox getTextBoxSearch();
	Map<Long, ItemAppView> getMapItemApp();
	BHTouchImage getButtonAddNew();
}
