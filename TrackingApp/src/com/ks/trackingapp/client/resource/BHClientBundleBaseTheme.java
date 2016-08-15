package com.ks.trackingapp.client.resource;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface BHClientBundleBaseTheme extends ClientBundle, BHClientBundle {
	public static final BHClientBundleBaseThemeImpl IMPL = new BHClientBundleBaseThemeImpl();
	
	@Override
	@Source("css/icon_menu.png")
	ImageResource menu();

	@Override
	@Source("css/icon_menu_active.png")
	ImageResource menu_active();
	
	@Override
	@Source("css/icon_back.png")
	ImageResource back();
	
	@Override
	@Source("css/play-icon.png")
	ImageResource playMedia();
	@Override
	@Source("css/pause-icon.png")
	ImageResource pauseMedia();
	
}