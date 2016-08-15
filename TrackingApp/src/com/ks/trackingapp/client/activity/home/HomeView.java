package com.ks.trackingapp.client.activity.home;

import java.util.List;

import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.ks.trackingapp.client.activity.base.BaseView;
import com.ks.trackingapp.shared.model.IBasic;

public interface HomeView extends BaseView{
	
	Button getAddApp();
	void updateData(int start, List<IBasic> customers);
	int getStart();
	void setStart(int start);
}
