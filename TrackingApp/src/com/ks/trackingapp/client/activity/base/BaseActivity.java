package com.ks.trackingapp.client.activity.base;

import org.gwtbootstrap3.client.ui.AnchorButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.ks.trackingapp.client.activity.ClientFactory;
import com.ks.trackingapp.client.activity.comment.CommentPlace;
import com.ks.trackingapp.client.activity.home.HomePlace;
import com.ks.trackingapp.client.util.ClientUtils;

public class BaseActivity extends MGWTAbstractActivity {

	protected final ClientFactory clientFactory;
	protected EventBus eventBus;
	protected Place place;
	private BaseView baseView;
	

	public BaseActivity(ClientFactory clientFactory, Place place) {
		this.clientFactory = clientFactory;
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		this.eventBus = eventBus;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus,
			BaseView basicView) {
		this.eventBus = eventBus;
		this.baseView = basicView;
		loadData();
		handleEvent();
		if (basicView != null) {
			basicView.refreshView();
		}
	}

	protected void loadData() {

	}
	
	protected void handleEvent(){
		if(baseView == null){
			ClientUtils.log("Baseview is null");
		}else {
			addHandlerRegistration(baseView.getLayout().getTapApps().addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					goTo(new HomePlace());
				}
			}));
			addHandlerRegistration(baseView.getLayout().getTapComments().addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					goTo(new CommentPlace());
				}
			}));
		}
	}

	protected void goTo(Place newPlace) {
		clientFactory.getPlaceController().goTo(newPlace);
	}
	
	protected void changeTapColor(AnchorButton button){
		if(button == baseView.getLayout().getTapApps()){
			baseView.getLayout().getTapApps().getElement().getStyle().setBackgroundColor("#568203");
			baseView.getLayout().getTapComments().getElement().getStyle().setBackgroundColor("#404042");
		}
		if(button == baseView.getLayout().getTapComments()) {
			baseView.getLayout().getTapApps().getElement().getStyle().setBackgroundColor("#404042");
			baseView.getLayout().getTapComments().getElement().getStyle().setBackgroundColor("#568203");
		}
	}
	public BaseView getBaseView() {
		return baseView;
	}
}
