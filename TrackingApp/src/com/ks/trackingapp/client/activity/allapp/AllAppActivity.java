package com.ks.trackingapp.client.activity.allapp;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent.Handler;
import com.ks.trackingapp.client.RPCCall;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.appcomment.AppCommentPlace;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentPlace;
import com.ks.trackingapp.client.activity.newapp.NewAppPlace;
import com.ks.trackingapp.client.manager.TrackingManager;
import com.ks.trackingapp.client.util.Toaster;
import com.ks.trackingapp.client.view.dialog.DialogDelete;
import com.ks.trackingapp.client.view.item.ItemAppView;
import com.ks.trackingapp.shared.model.ItemApp;

public class AllAppActivity extends BasicActivity{
	
	private ArrayList<ItemApp> listApp = new ArrayList<>();
	private DialogDelete dialogDelete = new DialogDelete();
	private AllAppView view;
	private Long appIdDelete  = -1L;
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
		loadItemApps();
	}
	
	private void loadItemApps(){
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
				addHandlerRegistration(itemAppView.getMainPanel().addTapHandler(new TapHandler() {
					
					@Override
					public void onTap(TapEvent event) {
						goTo(new AppCommentPlace(map.getKey()));
					}
				}));
	
				itemAppView.getButtonMissAndroid().addTapHandler(new TapHandler() {
					
					@Override
					public void onTap(TapEvent event) {
						goTo(new NewAppPlace(false,map.getKey()));
					}
				});
				
				itemAppView.getButtonMissIOS().addTapHandler(new TapHandler() {
					
					@Override
					public void onTap(TapEvent event) {
						goTo(new NewAppPlace(false,map.getKey()));
					}
				});
				
				itemAppView.getButtonEdit().addTapHandler(new TapHandler() {
					
					@Override
					public void onTap(TapEvent event) {
						Toaster.showToast("edit");
						NewAppPlace appPlace = new NewAppPlace(false,  map.getKey());
						appPlace.setIsEdit(true);
						goTo(appPlace);
					}
				});
				
				itemAppView.getButtonDelete().addTapHandler(new TapHandler() {
					
					@Override
					public void onTap(TapEvent event) {
						appIdDelete = map.getKey();
						dialogDelete.show();
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
				String value = view.getTextBoxSearch().getText().toString().toLowerCase();
				if(value.length() > 0 && listApp.size() > 0){
					ArrayList<ItemApp> array = new ArrayList<>();
					for (int i=0; i< listApp.size(); i++){
						ItemApp app = listApp.get(i);
						if(app.getAppName().toLowerCase().contains(value)){
							array.add(app);
						}
					}
					view.showItemApp(array);
					handleClickAppItem();
				}
			}
		});
		addHandlerRegistration(view.getButtonAddNew().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				goTo(new NewAppPlace(true,-1L));
			}
		}));
		
		dialogDelete.getButtonNo().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				dialogDelete.hide();
			}
		});
		
		dialogDelete.getButtonYes().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				dialogDelete.hide();
				deleteItemApp();
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
	
	private void deleteItemApp(){
		new RPCCall<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Toaster.showToast("Delete ItemApp failed.");
			}

			@Override
			public void onSuccess(Void result) {
				Toaster.showToast("Delete ItemApp Success");
				loadItemApps();
				TrackingApp.dataService.commentDeleteByAppId(appIdDelete, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Toaster.showToast("Delete app's comment failled");
					}

					@Override
					public void onSuccess(Void result) {
						Toaster.showToast("Delete app's comment success");
					}
				});
			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				TrackingApp.dataService.appDeleteItem(appIdDelete, cb);
			}
		}.retry(0);;
	}
	@Override
	protected void onBackPress() {
		super.onBackPress();
		goTo(new HomeCommentPlace());
	}
	
	

}
