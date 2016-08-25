package com.ks.trackingapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableEvent;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutEvent;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
import com.googlecode.gwtphonegap.client.event.OnlineEvent;
import com.googlecode.gwtphonegap.client.event.OnlineHandler;
import com.googlecode.gwtphonegap.client.event.PauseEvent;
import com.googlecode.gwtphonegap.client.event.PauseHandler;
import com.googlecode.gwtphonegap.client.event.ResumeEvent;
import com.googlecode.gwtphonegap.client.event.ResumeHandler;
import com.googlecode.gwtphonegap.client.util.PhonegapUtil;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;
import com.googlecode.mgwt.ui.client.widget.animation.AnimationWidget;
import com.ks.trackingapp.client.activity.AppPlaceHistoryMapper;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.ClientFactoryImpl;
import com.ks.trackingapp.client.activity.PhoneActivityMapper;
import com.ks.trackingapp.client.activity.PhoneAnimationMapper;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentPlace;
import com.ks.trackingapp.client.activity.login.LoginPlace;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.shared.Config;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TrackingApp implements EntryPoint {

	
	public static PhoneGap phoneGap = GWT.create(PhoneGap.class);
	public static ClientFactory clientFactory = new ClientFactoryImpl();
	public static final DataServiceAsync dataService = GWT.create(DataService.class);
	
	@Override
	public void onModuleLoad() {
		phoneGap.addHandler(new PhoneGapAvailableHandler() {
			@Override
			public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
				startApp();
			}
		});

		phoneGap.addHandler(new PhoneGapTimeoutHandler() {
			@Override
			public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
				ClientUtils.log("Time out");
			}
		});

		phoneGap.getEvent().getOnlineHandler()
				.addOnlineHandler(new OnlineHandler() {

					@Override
					public void onOnlineEvent(OnlineEvent event) {
					}
				});
		phoneGap.getEvent().getResumeHandler()
				.addResumeHandler(new ResumeHandler() {
					@Override
					public void onResumeEvent(ResumeEvent event) {
						ClientUtils.log("Resume event");
					}
				});
		phoneGap.getEvent().getPauseHandler()
				.addPauseHandler(new PauseHandler() {
					@Override
					public void onPause(PauseEvent event) {
						ClientUtils.log("Pause event");
					}
				});

		phoneGap.initializePhoneGap();
	}
	
	public static ClientFactory getClientFactory(){
		return clientFactory;
	}
	private void startApp() {
		if(phoneGap.isPhoneGapDevice()) {
			PhonegapUtil.prepareService((ServiceDefTarget)dataService, Config.APP_HOST_DOMAIN , "trackingapp/dataservice");
		}else {
			
		}
		
		ViewPort viewPort = new MGWTSettings.ViewPort();
		MGWTSettings settings = new MGWTSettings();
		settings.setViewPort(viewPort);
		viewPort.setUserScaleAble(false).setMinimumScale(1.0)
				.setMinimumScale(1.0).setMaximumScale(1.0);
		settings.setFullscreen(true);
		settings.setPreventScrolling(true);
		settings.setViewPort(viewPort);
		MGWT.applySettings(settings);
		createDisplay(clientFactory);
		goToHome();
	}
	public void goToHome() {
		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(clientFactory.getPlaceController(),
				clientFactory.getEventBus(), new LoginPlace());
		historyHandler.handleCurrentHistory();
	}
	private void createDisplay(ClientFactory clientFactory) {
		AnimationWidget display = new AnimationWidget();
		PhoneActivityMapper activityMapper = new PhoneActivityMapper(
				clientFactory);
		PhoneAnimationMapper animationMapper = new PhoneAnimationMapper();
		AnimatingActivityManager activityManager = new AnimatingActivityManager(
				activityMapper, animationMapper, clientFactory.getEventBus());
		activityManager.setDisplay(display);
		RootPanel.get().add(display);
	}
}
