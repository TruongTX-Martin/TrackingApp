package com.ks.trackingapp.client.activity.pulltorefresh;


import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowStandardHandler;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowStandardHandler.PullActionHandler;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.basic.BasicActivity;
import com.ks.trackingapp.client.util.Toaster;

public class PullToRefreshActivity extends BasicActivity{

	private PullToRefreshView refreshView;
	
	public PullToRefreshActivity(ClientFactory clientFactory, Place place) {
		super(clientFactory, place);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		refreshView = clientFactory.getPullToRefeshView();
		super.start(panel, eventBus,refreshView);
		panel.setWidget(refreshView);
	}
	
	@Override
	protected void handleEvent() {
		super.handleEvent();
		refreshView.getPullHeader().setHTML("Loading more");
		PullArrowStandardHandler  headerPanler = new PullArrowStandardHandler(refreshView.getPullHeader(), refreshView.getPullPanel());
		headerPanler.setPullActionHandler(new PullActionHandler() {
			
			@Override
			public void onPullAction(AsyncCallback<Void> callback) {
				new Timer() {

					@Override
					public void run() {
						Toaster.showToast("Loadding more ....");
					}
				}.schedule(1000);
			}
		});
		
	}

}
