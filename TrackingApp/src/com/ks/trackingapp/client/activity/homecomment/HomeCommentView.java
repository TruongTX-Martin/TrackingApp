package com.ks.trackingapp.client.activity.homecomment;

import java.util.ArrayList;

import com.googlecode.mgwt.ui.client.widget.input.search.MSearchBox;
import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.view.item.FilterPlatformView;
import com.ks.trackingapp.shared.model.ItemComment;

public interface HomeCommentView extends BasicView{

	void showItemComment(ArrayList<ItemComment> list);
	FilterPlatformView getFilterPlatformView();
	MSearchBox getTextBoxSearch();
	
}
