package com.ks.trackingapp.client.activity.allapp;

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
import com.ks.trackingapp.client.activity.appcomment.AppCommentPlace;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentPlace;
import com.ks.trackingapp.client.manager.TrackingManager;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.client.view.item.ItemAppView;
import com.ks.trackingapp.shared.model.ItemApp;

public class AllAppActivity extends BasicActivity{
	
	private ArrayList<ItemApp> listApp = new ArrayList<>();

	private AllAppView view;
	public AllAppActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view  = clientFactory.getAllAppView();
		super.start(panel, eventBus,view);
		panel.setWidget(view);
	}
	
	@Override
	protected void loadData() {
		super.loadData();
		TrackingApp.dataService.appGetAllItem(TrackingManager.newInstance().getCurrentUser().getId(),new AsyncCallback<ArrayList<ItemApp>>() {
			
			@Override
			public void onSuccess(ArrayList<ItemApp> result) {
				view.showItemApp(result);
				listApp.clear();
				listApp.addAll(result);
				handleClickAppItem();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Get app failed");
			}
		});
		
	}
	
	private void handleClickAppItem(){
		if(view.getMapItemApp().size() > 0){
			for(final Map.Entry<Long,ItemAppView> map : view.getMapItemApp().entrySet()){
				ItemAppView itemAppView = map.getValue();
				itemAppView.getMainPanel().addTapHandler(new TapHandler() {
					
					@Override
					public void onTap(TapEvent event) {
						goTo(new AppCommentPlace(map.getKey()));
					}
				});
				
			}
		}
	}
	@Override
	protected void handleEvent() {
		super.handleEvent();
		view.getTextBoxSearch().addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String value = view.getTextBoxSearch().getText().toString();
				if(value.length() > 0 && listApp.size() > 0){
					ArrayList<ItemApp> array = new ArrayList<>();
					for (int i=0; i< listApp.size(); i++){
						ItemApp app = listApp.get(i);
						if(app.getAppName().contains(value)){
							array.add(app);
						}
					}
					view.showItemApp(array);
				}
			}
		});
	}
	
	@Override
	protected void onBackPress() {
		super.onBackPress();
		goTo(new HomeCommentPlace());
	}
	
	

}
