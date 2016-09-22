package com.ks.trackingapp.client.activity.basic;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwtphonegap.client.event.BackButtonPressedEvent;
import com.googlecode.gwtphonegap.client.event.BackButtonPressedHandler;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.allapp.AllAppPlace;
import com.ks.trackingapp.client.activity.login.LoginPlace;
import com.ks.trackingapp.client.activity.newapp.NewAppPlace;
import com.ks.trackingapp.client.plugin.CallPlugin;
import com.ks.trackingapp.client.util.AdmobUtil;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.util.Toaster;

public class BasicActivity extends MGWTAbstractActivity {
	
	protected ClientFactory clientFactory;
	protected EventBus eventBus;
	protected Place place = null;
	protected BasicView basicView;

	public BasicActivity(ClientFactory clientFactory, Place place) {
		this.clientFactory = clientFactory;
		this.place = place;
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		super.start(panel, eventBus);
		this.eventBus = eventBus;
		
	}
	
	public void start(AcceptsOneWidget panel, final EventBus eventBus, final BasicView basicView) { 
		this.eventBus = eventBus;
		this.basicView = basicView;
		loadData();
		handleEvent();
		if(basicView != null){
			addHandlerRegistration(basicView.getBhHeaderPanel().getButtonNavigation().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					if(TrackingApp.phoneGap.isPhoneGapDevice()) {
						CallPlugin.hideKeyBoard();
					}
					if(basicView.getSlidingMenu().isShowing()){
						basicView.getSlidingMenu().hide();
					}else{
						basicView.getSlidingMenu().show();
					}
				}
			}));
			//button back
			addHandlerRegistration(basicView.getBhHeaderPanel().getButtonBack().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					onBackPress();
				}
			}));
			//handle button new app
			addHandlerRegistration(basicView.getSlidingMenu().getButtonAddApp().getTouchPanel().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					Toaster.showToast("Add new app");
					NewAppPlace newPlace = new NewAppPlace(false);
					newPlace.setAppId(-1L);
					goTo(newPlace);
				}
			}));
			
			//handle  button all app
			addHandlerRegistration(basicView.getSlidingMenu().getButtonAllApp().getTouchPanel().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					goTo(new AllAppPlace());
				}
			}));
			
			//handle  button logout
			addHandlerRegistration(basicView.getSlidingMenu().getButtonLogout().getTouchPanel().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					goTo(new LoginPlace());
				}
			}));
			addHandlerRegistration(TrackingApp.phoneGap.getEvent().getBackButton().addBackButtonPressedHandler(new BackButtonPressedHandler() {
				@Override
				public void onBackButtonPressed(BackButtonPressedEvent event) {
					onBackPress();
				}
			}));
		}
		//create admob
		try{
			showAdmobBanner();
		} catch (Exception e) {
			
		}
	}
	
	protected void showAdmobBanner() {
		ClientUtils.log("Basic Activity- show admob");
		if (!TrackingApp.phoneGap.isPhoneGapDevice()) {
			return;
		}
		if(ClientUtils.isOnline()){
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					AdmobUtil.showInterstitialFunc();
				}
			});
		} else {
		}
	}
	protected void loadData(){
	}
	
	protected void handleEvent() {
	}
	protected void goTo(Place newPlace) {
		basicView.getSlidingMenu().hide();
		clientFactory.getPlaceController().goTo(newPlace);
	}
	protected void onBackPress(){
		basicView.getSlidingMenu().hide();
	}
}
