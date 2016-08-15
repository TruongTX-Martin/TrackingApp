package com.ks.trackingapp.client.activity.comment;

import java.util.List;

import com.google.gwt.user.client.ui.ListBox;
import com.ks.trackingapp.client.activity.base.BaseView;
import com.ks.trackingapp.shared.model.IBasic;

public interface CommentView extends BaseView{
	
	ListBox getListboxPlatform();
	
	void updateData(int start, List<IBasic> customers);
	void resetTable();
	int getStart();

}
