package com.ks.trackingapp.client.activity.basic;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.allapp.AllAppPlace;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentPlace;
import com.ks.trackingapp.client.activity.login.LoginPlace;
import com.ks.trackingapp.client.activity.newapp.NewAppPlace;

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
					goTo(new NewAppPlace());
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
