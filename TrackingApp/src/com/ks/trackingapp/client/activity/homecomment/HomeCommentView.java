package com.ks.trackingapp.client.activity.homecomment;

import java.util.ArrayList;

import com.googlecode.mgwt.ui.client.widget.input.search.MSearchBox;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.view.item.FilterLanguage;
import com.ks.trackingapp.client.view.item.FilterView;
import com.ks.trackingapp.shared.model.ItemComment;

public interface HomeCommentView extends BasicView{

	void showItemComment(ArrayList<ItemComment> list);
	MSearchBox getTextBoxSearch();
	FilterView getFilterView();
	FilterLanguage getFilterLanguage();
	ScrollPanel getScrollPanel();
}
