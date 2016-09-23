package com.ks.trackingapp.client.activity.pulltoload;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowStandardHandler;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowStandardHandler.PullActionHandler;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.activity.homecomment.HomeCommentPlace;
import com.ks.trackingapp.client.util.ClientUtils;

public class PullToLoadActivity extends BasicActivity {

	private PullToLoadView view;
	
	public PullToLoadActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getPullToLoadView();
		super.start(panel, eventBus,view);
		panel.setWidget(view);
	}
	
	@Override
	protected void handleEvent() {
		view.getPullHeader().setHTML("Load more");
		PullArrowStandardHandler  headerPanler = new PullArrowStandardHandler(view.getPullHeader(), view.getPullToRefreshPlay());
		headerPanler.setPullActionHandler(new PullActionHandler() {
			
			@Override
			public void onPullAction(AsyncCallback<Void> callback) {
				ClientUtils.log("Fuc");
				view.getPullToRefreshPlay().refresh();
				new Timer() {

					@Override
					public void run() {
						ClientUtils.log("Load more");
						view.getPullToRefreshPlay().refresh();
					}
				}.schedule(1000);
			}
		});
		view.getPullToRefreshPlay().setHeaderPullHandler(headerPanler);
		
	}
	@Override
	protected void onBackPress() {
		goTo(new HomeCommentPlace());
	}

}
