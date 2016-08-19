package com.ks.trackingapp.client.activity.appcomment;

import java.util.ArrayList;
import java.util.Map;

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
import com.ks.trackingapp.client.activity.allapp.AllAppPlace;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.manager.TrackingManager;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.client.view.VerticalTouchPanel;
import com.ks.trackingapp.client.view.dialog.DialogFilterPlatform;
import com.ks.trackingapp.client.view.dialog.DialogSelectLanguage;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;

public class AppCommentActivity extends BasicActivity{

	private AppCommentView view;
	private Long appId = -1L;
	private ArrayList<ItemComment> listComment = new ArrayList<>();
	private DialogFilterPlatform dialogFilter = new DialogFilterPlatform();
	private DialogSelectLanguage dialogLanguage = new DialogSelectLanguage();
	
	private String TAG = "";
	private String INPUT = "";
	public AppCommentActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
		appId = ((AppCommentPlace)place).getAppId();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getAppCommentView();
		super.start(panel, eventBus,view);
		panel.setWidget(view);
	}
	
	@Override
	protected void loadData() {
		super.loadData();
		if(appId != -1L){
			TrackingApp.dataService.commentGetFromAppId(TrackingManager.newInstance().getCurrentUser().getId(),appId, new AsyncCallback<ArrayList<ItemComment>>() {
				
				@Override
				public void onSuccess(ArrayList<ItemComment> result) {
					showItemComments(result);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Toaster.showToast("Get comment failed");
				}
			});
			//get App name
//			TrackingApp.dataService.appGetFromId(appId, new AsyncCallback<ItemApp>() {
//
//				@Override
//				public void onFailure(Throwable caught) {
//					Toaster.showToast("Get app item failed");
//				}
//
//				@Override
//				public void onSuccess(ItemApp result) {
//					if(result != null) {
//						view.getBhHeaderPanel().setCenter(result.getAppName());
//					}
//				}
//			});
		}
	}
	@Override
	protected void handleEvent() {
		super.handleEvent();
		view.getSearchBox().addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if(event.getValue().toString().length() > 0) {
					if(listComment.size() > 0) {
						ArrayList<ItemComment> array = new ArrayList<>();
						for (int i=0; i< listComment.size(); i++) {
							ItemComment item = listComment.get(i);
							if(item.getComment().contains(event.getValue().toString())) {
								array.add(item);
							}
						}
						view.showCommentApp(array);
					}
				}
			}
		});
		
		view.getFilterView().getTouchFilter().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				dialogFilter.show();
			}
		});
		
		view.getFilterView().getTouchLanguage().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				dialogLanguage.show();
			}
		});
		
		
		if(dialogLanguage.getMapTouchPanel().size() > 0){
			for(final Map.Entry<String,VerticalTouchPanel> map : dialogLanguage.getMapTouchPanel().entrySet()){
				final String key = map.getKey();
				VerticalTouchPanel itemAppView = map.getValue();
				itemAppView.addTapHandler(new TapHandler() {
					
					@Override
					public void onTap(TapEvent event) {
						filterLanguage(key);
					}
				});
			}
		}
		
		
		dialogFilter.getTouchAll().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				handleFilterComment(Config.FILTERBY_ALL,Config.PLATFORM_ALL);
			}
		});
		
		dialogFilter.getTouchAndroid().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				handleFilterComment(Config.FILTERBY_PLATFORM,Config.PLATFORM_ANDROID);
			}
		});
		
		dialogFilter.getTouchIOS().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				handleFilterComment(Config.FILTERBY_PLATFORM,Config.PLATFORM_IOS);
			}
		});
		
		dialogFilter.getTouchDate().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				handleFilterComment(Config.FILTERBY_DATE,Config.FILTERBY_DATE);
			}
		});
		
		dialogFilter.getTouchRating().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				handleFilterComment(Config.FILTERBY_RATE,Config.FILTERBY_RATE);
			}
		});
	}
	
	private void filterLanguage(String filter){
		dialogLanguage.hide();
		String language = view.getFilterView().getHtmlLanguage().getText().toString();
		if(filter.equals(language)){
			return;
		}
		view.getFilterView().getHtmlLanguage().setText(filter);
		String laguageCode = Config.getLanguage().get(filter);
		filterComment(laguageCode,appId, TAG, INPUT);
	}
	
	private void handleFilterComment(String tag,String input){
		dialogFilter.hide();
		String platform = view.getFilterView().getHtmlPlatform().getText().toString().trim();
		if(input.equals(platform)){
			return;
		}
		view.getFilterView().getHtmlPlatform().setText(input);
		String language = view.getFilterView().getHtmlLanguage().getText().toString();
		String valueLanguage = Config.getLanguage().get(language);
		filterComment(valueLanguage,appId, tag, input);
	}
	
	private void filterComment(String language,Long appId,String tag,String filter) {
		this.TAG = tag;
		this.INPUT = filter;
		TrackingApp.dataService.commentFilterByTag(language,TrackingManager.newInstance().getCurrentUser().getId(),appId,tag,filter, new AsyncCallback<ArrayList<ItemComment>>() {
			
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
		listComment.clear();
		listComment.addAll(list);
		view.showCommentApp(listComment);
	}
	
	@Override
	protected void onBackPress() {
		super.onBackPress();
		goTo(new AllAppPlace());
	}

}
