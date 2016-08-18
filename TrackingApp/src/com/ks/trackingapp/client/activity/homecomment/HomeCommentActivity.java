package com.ks.trackingapp.client.activity.homecomment;

import java.util.ArrayList;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.manager.TrackingManager;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.client.view.item.DialogFilterPlatform;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemComment;

public class HomeCommentActivity extends BasicActivity {

	private HomeCommentView view;
	private DialogFilterPlatform dialogFilter = new DialogFilterPlatform();
	private ArrayList<ItemComment> listItemComment = new ArrayList<>();
	
	public HomeCommentActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getHomeCommentView();
		super.start(panel, eventBus,view);
		panel.setWidget(view);
	}
	
	
	@Override
	protected void loadData() {
		super.loadData();
		filterData(Config.FILTERBY_ALL,Config.PLATFORM_ALL);
	}
	
	
	@Override
	protected void handleEvent() {
		super.handleEvent();
		view.getFilterPlatformView().getTouchPanel().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				dialogFilter.show();
			}
		});
		
		dialogFilter.getTouchAll().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				handleFilterPlatform(Config.FILTERBY_ALL,Config.PLATFORM_ALL);
			}
		});
		
		dialogFilter.getTouchAndroid().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				handleFilterPlatform(Config.FILTERBY_PLATFORM,Config.PLATFORM_ANDROID);
			}
		});
		
		dialogFilter.getTouchIOS().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				handleFilterPlatform(Config.FILTERBY_PLATFORM,Config.PLATFORM_IOS);
			}
		});
		
		dialogFilter.getTouchDate().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				handleFilterPlatform(Config.FILTERBY_DATE,Config.FILTERBY_DATE);
			}
		});
		
		dialogFilter.getTouchRating().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				handleFilterPlatform(Config.FILTERBY_RATE,Config.FILTERBY_RATE);
			}
		});
		
		view.getTextBoxSearch().addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if(event.getValue().toString().length() > 0) {
					filterCommentByText(event.getValue().toString());
				}
			}
		});
		
	}

	private void handleFilterPlatform(String tag,String input){
		dialogFilter.hide();
		String platform = view.getFilterPlatformView().getHtmlPlatform().getText().toString().trim();
		if(input.equals(platform)){
			return;
		}
		view.getFilterPlatformView().getHtmlPlatform().setText(input);
		filterData(tag,input);
	}
	
	private void filterData(String tag,String filter){
		TrackingApp.dataService.commentFilterByTag(TrackingManager.newInstance().getCurrentUser().getId(),-1L,tag,filter, new AsyncCallback<ArrayList<ItemComment>>() {
			
			@Override
			public void onSuccess(ArrayList<ItemComment> result) {
				showItemComments(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Get comment failed,please check your connection");
			}
		});
	}
	
	private void showItemComments(ArrayList<ItemComment> list){
		listItemComment.clear();
		listItemComment.addAll(list);
		view.showItemComment(listItemComment);
	}
	
	private void filterCommentByText(String text){
		ArrayList<ItemComment> arrayList = new ArrayList<>();
		if(listItemComment.size() > 0){
			for(int i=0; i< listItemComment.size(); i++) {
				ItemComment comment = listItemComment.get(i);
				if(comment.getComment().contains(text) || comment.getAppname().contains(text)) {
					arrayList.add(comment);
				}
			}
		}
		view.showItemComment(arrayList);
	}
}
