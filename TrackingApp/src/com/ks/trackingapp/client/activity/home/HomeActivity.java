package com.ks.trackingapp.client.activity.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.ks.trackingapp.client.RPCCall;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.manager.TrackingManager;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.client.view.VerticalTouchPanel;
import com.ks.trackingapp.client.view.dialog.DialogSelectLanguage;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;

public class HomeActivity extends BasicActivity {

	private HomeView view;
	private List<ItemApp> listApp = new ArrayList<ItemApp>();
	private Map<ItemApp, List<ItemComment>> map = new HashMap<ItemApp, List<ItemComment>>();
	private DialogSelectLanguage dialogLanguage = new DialogSelectLanguage();
	
	public HomeActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getHomeView();
		super.start(panel, eventBus, view);
		panel.setWidget(view);
	}

	@Override
	protected void handleEvent() {
		super.handleEvent();
		addHandlerRegistration(view.getFilterLanguage().getTouchPanel().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				dialogLanguage.show();
			}
		}));
		if(dialogLanguage.getMapTouchPanel().size() > 0){
			for(final Map.Entry<String,VerticalTouchPanel> map : dialogLanguage.getMapTouchPanel().entrySet()){
				final String key = map.getKey();
				VerticalTouchPanel itemAppView = map.getValue();
				itemAppView.addTapHandler(new TapHandler() {
					
					@Override
					public void onTap(TapEvent event) {
						dialogLanguage.hide();
						view.getFilterLanguage().setImageLanguageSource(key);
						view.getFilterLanguage().getHTMLFilter().setText(key);
						String laguageCode = Config.getLanguage().get(key);
						requestAllComment(laguageCode);
					}
				});
			}
		}
	}
	@Override
	protected void loadData() {
		super.loadData();
		requestApps();
	}

	private void requestApps() {
		ClientUtils.log("Start request app");
		TrackingApp.dataService.appGetAllItem(TrackingManager.newInstance()
				.getCurrentUser().getId(),
				new AsyncCallback<ArrayList<ItemApp>>() {

					@Override
					public void onSuccess(ArrayList<ItemApp> result) {
						ClientUtils.log("App success:" + result.size());
						listApp.clear();
						listApp.addAll(result);
						String language = Config.LANGUAGE_1ENGLISH;
						String valueLanguage = Config.getLanguage().get(language);
						requestAllComment(valueLanguage);
					}

					@Override
					public void onFailure(Throwable caught) {
						Toaster.showToast("Get app failed");
					}
				});
	}
	private void requestAllComment(final String language){
		ClientUtils.log("Start request comment");
		new RPCCall<List<ItemComment>>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientUtils.log("Request comment failed");
			}

			@Override
			public void onSuccess(List<ItemComment> result) {
				ClientUtils.log("Request comment success:" + result.size());
				handleListData(result);
			}

			@Override
			protected void callService(AsyncCallback<List<ItemComment>> cb) {
				TrackingApp.dataService.getCommentByLanguage(language, cb);
			}
		}.retry(0);;
	}
	
	private void handleListData(List<ItemComment> list){
		if(listApp.size() > 0) {
			map.clear();
			for (int i=0; i< listApp.size(); i++) {
				ItemApp itemApp  = listApp.get(i);
				List<ItemComment> comments = filterItemComment(itemApp, list); 
				if(comments.size() > 0){
					map.put(itemApp, comments);
				}
			}
			view.showData(map);
		}
	}
	private List<ItemComment> filterItemComment(ItemApp itemApp,List<ItemComment> list){
		List<ItemComment> listComment = new ArrayList<ItemComment>();
		for (int i=0; i< list.size() ; i++) {
			if(itemApp.getId().toString().equals(list.get(i).getAppId().toString())){
				listComment.add(list.get(i));
			}
		}
		return listComment;
	}

}
