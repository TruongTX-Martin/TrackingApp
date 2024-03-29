package com.ks.trackingapp.client.activity.appcomment;

import java.util.ArrayList;

import com.googlecode.mgwt.ui.client.widget.input.search.MSearchBox;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowHeader;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
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
	ScrollPanel getScrollPanel();
	PullPanel getPullPanel();
	PullArrowHeader getPullHeader();
}
