package com.ks.trackingapp.client.util;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.googlecode.mgwt.ui.client.MGWT;
import com.ks.trackingapp.client.TrackingApp;
import com.ks.trackingapp.shared.Config;

public class AdmobUtil {
	public static void prepareAds() {
		ClientUtils.log("Prepare admob=============================++>");
		if (!TrackingApp.phoneGap.isPhoneGapDevice()
				|| !ClientUtils.isOnline()) {
			return;
		}
		String url = Config.APP_ADMOB_DOMAIN + "admob?platform="
				+ (MGWT.getOsDetection().isIOs() ? Config.PLATFORM_IOS : Config.PLATFORM_ANDROID);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request,
						Response response) {
					if (response.getStatusCode() == 200) {
						String adsJson = response.getText();
						ClientUtils.log("Reponse Admob=============>" + adsJson);
						try {
							JSONObject object = (JSONObject)JSONParser.parseStrict(adsJson);
							if(object.containsKey("idAdmob")) {
								String idAdmob = object.get("idAdmob").toString();
								ClientUtils.log("Id Admob=============>" + idAdmob);
								initAds(idAdmob);
							}
						} catch (Exception e) {
							ClientUtils.log("Error while parser string admob");
						}
					} else {
						ClientUtils.log("adMob error 1");
					}
				}
				@Override
				public void onError(Request request, Throwable exception) {
					ClientUtils.log("adMob error 2");
				}
			});
		} catch (RequestException e) {
			Toaster.showToast("Có lỗi xảy ra.");
		}
	}
	
	
	protected static native void initAds(String bannerId) /*-{
		//$wnd.console.log("initAds: bannerId: " + bannerId + " -- fullId: " + fullId);
		 if ( $wnd.plugins && $wnd.plugins.AdMob ) {
            $wnd.plugins.AdMob.setOptions( {
                publisherId: bannerId,
                interstitialAdId: bannerId,
                adSize: window.plugins.AdMob.AD_SIZE.SMART_BANNER,  //use SMART_BANNER, BANNER, IAB_MRECT, IAB_BANNER, IAB_LEADERBOARD
                bannerAtTop: false, // set to true, to put banner at top
                overlap: true, // banner will overlap webview 
                offsetTopBar: false, // set to true to avoid ios7 status bar overlap
                isTesting: false, // receiving test ad
                autoShow: false // auto show interstitial ad when loaded
            });
           $wnd.plugins.AdMob.createInterstitialView();  //get the interstitials ready to be shown
           $wnd.plugins.AdMob.requestInterstitialAd();

        } else {
            //alert( 'admob plugin not ready' );
        }
		
		
//		if ($wnd.AdMob) {
//			var interstitialId = "";
//			var isAdMobReady = 0;
//			var model = $wnd.device.model;
//			var deviceOSVersion = $wnd.device.version;
//			var deviceOS = $wnd.device.platform;
//			var deviceOSVersionNumber = parseFloat(deviceOSVersion, 10);
//			//$wnd.console.log("deviceOSVersion: " + deviceOSVersion + " deviceOS: " + deviceOS + " deviceOSVersionNumber: " + deviceOSVersionNumber);
//			$wnd.AdMob
//						.createBanner({
//							adId : bannerId,
//							isTesting : !@com.ks.trackingapp.shared.AppConfig::ENABLE_ADMOB,
//							overlap : false,
//							offsetTopBar : true,
//							position : $wnd.AdMob.AD_POSITION.BOTTOM_CENTER,
//							bgColor : 'white',
//							autoShow : true
//						});
//			isAdMobReady = 1;
//		} else {
//			console.log('admob plugin not ready');
//		}
	}-*/;
	
	public static native void showInterstitialFunc()/*-{
		 $wnd.plugins.AdMob.showInterstitialAd();
	}-*/;

	private static native void closeAds() /*-{
		return $wnd.AdMob.removeBanner();
	}-*/;

	public static native void showBanner() /*-{
		return $wnd.AdMob.showBanner($wnd.AdMob.AD_POSITION.BOTTOM_CENTER);
	}-*/;

	private static native void jsShowAdsFull() /*-{
		return $wnd.AdMob.showInterstitial();
	}-*/;

	public static void closeBannerAds() {
		if (TrackingApp.phoneGap.isPhoneGapDevice()) {
			closeAds();
		}
	}



	public static void onRemoveAds() {
		AdmobUtil.closeAds();
	}

}
