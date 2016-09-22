package com.ks.trackingapp.client.activity;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.ks.trackingapp.client.activity.allapp.AllAppView;
import com.ks.trackingapp.client.activity.allapp.AllAppViewImpl;
import com.ks.trackingapp.client.activity.appcomment.AppCommentView;
import com.ks.trackingapp.client.activity.appcomment.AppCommentViewImpl;
import com.ks.trackingapp.client.activity.basic.BasicView;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.activity.home.HomeView;
import com.ks.trackingapp.client.activity.home.HomeViewImpl;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentView;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentViewImpl;
import com.ks.trackingapp.client.activity.login.LoginView;
import com.ks.trackingapp.client.activity.login.LoginViewImpl;
import com.ks.trackingapp.client.activity.newapp.NewAppView;
import com.ks.trackingapp.client.activity.newapp.NewAppViewImpl;
import com.ks.trackingapp.client.activity.pulltoload.PullToLoadView;
import com.ks.trackingapp.client.activity.pulltoload.PullToLoadViewImpl;
import com.ks.trackingapp.client.activity.register.RegisterView;
import com.ks.trackingapp.client.activity.register.RegisterViewImpl;
import com.ks.trackingapp.client.view.dialog.LoadingDialog;

public class ClientFactoryImpl implements ClientFactory{

	
	private SimpleEventBus eventBus;
	private PlaceController placeController;
	
	private BasicView basicView;
	private HomeCommentView homeCommentView;
	private LoginView loginView;
	private RegisterView registerView;
	private AllAppView allAppView;
	private NewAppView newAppView;
	private AppCommentView appCommentView;
	private LoadingDialog loadingDialog;
	private PullToLoadView pullToLoadView;
	private HomeView homeView;
	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();
		placeController = new PlaceController(eventBus);
	}
	
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}


	@Override
	public BasicView getBasicView() {
		if(basicView == null) {
			basicView = new BasicViewImpl();
		}
		return basicView;
	}

	@Override
	public HomeCommentView getHomeCommentView() {
		if(homeCommentView == null) {
			homeCommentView = new HomeCommentViewImpl();
		}
		return homeCommentView;
	}

	@Override
	public LoginView getLoginView() {
		if(loginView == null){
			loginView = new LoginViewImpl();
		}
		return loginView;
	}

	@Override
	public RegisterView getRegisterView() {
		if(registerView == null) {
			registerView = new RegisterViewImpl();
		}
		return registerView;
	}

	@Override
	public AllAppView getAllAppView() {
		if (allAppView == null) {
			allAppView = new AllAppViewImpl();
		}
		return allAppView;
	}

	@Override
	public NewAppView getNewAppView() {
		if(newAppView == null) {
			newAppView = new NewAppViewImpl();
		}
		return newAppView;
	}

	@Override
	public AppCommentView getAppCommentView() {
		if(appCommentView == null){
			appCommentView = new AppCommentViewImpl();
		}
		return appCommentView;
	}

	@Override
	public LoadingDialog getLoadingDialog() {
		if(loadingDialog == null) {
			loadingDialog = new LoadingDialog();
		}
		return loadingDialog;
	}

	@Override
	public PullToLoadView getPullToLoadView() {
		if(pullToLoadView == null) {
			pullToLoadView = new PullToLoadViewImpl();
		}
		return pullToLoadView;
	}

	@Override
	public HomeView getHomeView() {
		if(homeView == null){
			homeView = new HomeViewImpl();
		}
		return homeView;
	}

}
