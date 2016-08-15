package com.ks.trackingapp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ReloadAppEvent extends GwtEvent<ReloadAppEventHandler>{
	
	public static Type<ReloadAppEventHandler> TYPE = new Type<ReloadAppEventHandler>();

	
	public ReloadAppEvent() {
	}
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ReloadAppEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReloadAppEventHandler handler) {
		handler.reloadListApp(this);
	}

}
