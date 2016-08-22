package com.ks.trackingapp.client.activity.appcomment;

import java.util.ArrayList;

import com.googlecode.mgwt.ui.client.widget.input.search.MSearchBox;
import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.view.BHTouchImage;
import com.ks.trackingapp.client.view.BhHeaderPanel;
import com.ks.trackingapp.client.view.item.FilterLanguage;
import com.ks.trackingapp.client.view.item.FilterPlatformView;
import com.ks.trackingapp.client.view.item.FilterView;
import com.ks.trackingapp.shared.model.ItemComment;

public interface AppCommentView extends BasicView{

	
	void showCommentApp(ArrayList<ItemComment> list);
	BhHeaderPanel getBhHeaderPanel();
	MSearchBox getSearchBox();
	FilterView getFilterView();
	FilterLanguage getFilterLanguage();
}
