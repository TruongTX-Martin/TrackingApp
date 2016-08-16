package com.ks.trackingapp.client.util;

public class CipherDES {
	
	public static native String encryptByDES(final String message,
			final String key) /*-{
		var keyHex = $wnd.CryptoJS.enc.Utf8.parse(key);
		var encrypted = $wnd.CryptoJS.DES.encrypt(message, keyHex, {
			mode : $wnd.CryptoJS.mode.ECB,
			padding : $wnd.CryptoJS.pad.Pkcs7
		});
		return encrypted.toString();
	}-*/;

	public static native String decryptByDES(final String ciphertext,
			final String key) /*-{
		var keyHex = $wnd.CryptoJS.enc.Utf8.parse(key);
		// direct decrypt ciphertext
		var decrypted = $wnd.CryptoJS.DES.decrypt({
			ciphertext : $wnd.CryptoJS.enc.Base64.parse(ciphertext)
		}, keyHex, {
			mode : $wnd.CryptoJS.mode.ECB,
			padding : $wnd.CryptoJS.pad.Pkcs7
		});
		return decrypted.toString($wnd.CryptoJS.enc.Utf8);
	}-*/;
}
