package com.ks.trackingapp.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ReloadAppEventHandler extends EventHandler{
	
	void reloadListApp(ReloadAppEvent event);
}
