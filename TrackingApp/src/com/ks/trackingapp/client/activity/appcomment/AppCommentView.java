package com.ks.trackingapp.client.activity.appcomment;

import java.util.ArrayList;

import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.view.BhHeaderPanel;
import com.ks.trackingapp.shared.model.ItemComment;

public interface AppCommentView extends BasicView{

	
	void showCommentApp(ArrayList<ItemComment> list);
	BhHeaderPanel getBhHeaderPanel();
}
