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
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent.Handler;
import com.ks.trackingapp.client.RPCCall;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.allapp.AllAppPlace;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.manager.TrackingManager;
import com.ks.trackingapp.client.util.ClientUtils;
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
		view.getFilterLanguage().getHTMLFilter().setText(Config.LANGUAGE_1ENGLISH);
		if(appId != -1L){
			new RPCCall<ArrayList<ItemComment>>() {

				@Override
				public void onFailure(Throwable caught) {
					Toaster.showToast("Get comment failed");
				}

				@Override
				public void onSuccess(ArrayList<ItemComment> result) {
					showItemComments(result);
				}

				@Override
				protected void callService(AsyncCallback<ArrayList<ItemComment>> callback) {
					TrackingApp.dataService.commentGetFromAppId(TrackingManager.newInstance().getCurrentUser().getId(),appId, callback);
				}
			}.retry(0);;
			//get App name
			TrackingApp.dataService.appGetFromId(appId, new AsyncCallback<ItemApp>() {

				@Override
				public void onFailure(Throwable caught) {
					Toaster.showToast("Get app item failed");
				}

				@Override
				public void onSuccess(ItemApp result) {
					if(result != null) {
						view.getBhHeaderPanel().setCenter(result.getAppName());
					}
				}
			});
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
		
		view.getFilterView().getTouchPanel().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				dialogFilter.show();
			}
		});
		
		view.getFilterLanguage().getTouchPanel().addTapHandler(new TapHandler() {
			
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
						String tag = view.getFilterView().getHTMLFilter().getText().toString();
						filterLanguage(tag,key);
					}
				});
			}
		}
		
		
		dialogFilter.getTouchAll().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				filterTag(Config.FILTERBY_ALL, view.getFilterLanguage().getHTMLFilter().getText().toString());
			}
		});
		
		dialogFilter.getTouchAndroid().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				filterTag(Config.PLATFORM_ANDROID, view.getFilterLanguage().getHTMLFilter().getText().toString());
			}
		});
		
		dialogFilter.getTouchIOS().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				filterTag(Config.PLATFORM_IOS, view.getFilterLanguage().getHTMLFilter().getText().toString());
			}
		});
		
		dialogFilter.getTouchDate().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				filterTag(Config.FILTERBY_DATE, view.getFilterLanguage().getHTMLFilter().getText().toString());
			}
		});
		
		dialogFilter.getTouchRating().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				filterTag(Config.FILTERBY_RATE, view.getFilterLanguage().getHTMLFilter().getText().toString());
			}
		});
		view.getScrollPanel().addScrollEndHandler(new Handler() {
			
			@Override
			public void onScrollEnd(ScrollEndEvent event) {
				if(view.getScrollPanel().getY() == view.getScrollPanel().getMaxScrollY()) {
					view.getScrollPanel().refresh();
				}
			}
		});
	}
	
	private void filterTag(String tag, String filter){
		dialogFilter.hide();
		String text = view.getFilterView().getHTMLFilter().getText().toString();
		if(tag.equals(text)){
			return;
		}
		view.getFilterView().getHTMLFilter().setText(tag);
		
		view.getFilterLanguage().getHTMLFilter().setText(filter);
		String laguageCode = Config.getLanguage().get(filter);
		filterCommentByTag(TrackingManager.newInstance().getCurrentUser().getId(), appId, laguageCode,tag);
		
	}
	
	private void filterLanguage(String tag,String filter){
		dialogLanguage.hide();
		String language = view.getFilterLanguage().getHTMLFilter().getText().toString();
		if(filter.equals(language)){
			return;
		}
		view.getFilterLanguage().getHTMLFilter().setText(filter);
		String laguageCode = Config.getLanguage().get(filter);
		ClientUtils.log("Language Code==========>" + tag);
		if(tag.equals(Config.PLATFORM_IOS)){
			laguageCode = "en";
		}
		filterCommentByTag(TrackingManager.newInstance().getCurrentUser().getId(), appId, laguageCode,tag);
	}
	
	private void filterCommentByTag(final Long userId,final Long appId,final String languageCode,final String tag){
		new RPCCall<ArrayList<ItemComment>>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Get comment failed.");
			}

			@Override
			public void onSuccess(ArrayList<ItemComment> result) {
				showItemComments(result);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<ItemComment>> cb) {
				TrackingApp.dataService.getCommentAppWithTag(userId, appId, languageCode,tag, cb);
			}
		}.retry(0);;
	}
	
	private void handleFilterComment(String tag,String input){
		dialogFilter.hide();
		String platform = view.getFilterView().getHTMLFilter().getText().toString().trim();
		if(input.equals(platform)){
			return;
		}
		view.getFilterView().getHTMLFilter().setText(input);
		String language = view.getFilterLanguage().getHTMLFilter().getText().toString();
		String valueLanguage = Config.getLanguage().get(language);
		filterComment(valueLanguage,appId, tag, input);
	}
	
	private void filterComment(final String language,final Long appId,final String tag,final String filter) {
		this.TAG = tag;
		this.INPUT = filter;
		new RPCCall<ArrayList<ItemComment>>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Get comment failed,please check your connection");
			}

			@Override
			public void onSuccess(ArrayList<ItemComment> result) {
				showItemComments(result);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<ItemComment>> cb) {
				TrackingApp.dataService.commentFilterByTag(language,TrackingManager.newInstance().getCurrentUser().getId(),appId,tag,filter,cb);
			}
		}.retry(0);;
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
