package com.ks.trackingapp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.ks.trackingapp.client.util.ClientUtils;

public abstract class RPCCall<T> implements AsyncCallback<T> {
	protected abstract void callService(AsyncCallback<T> cb);

	private void call(final int retriesLeft, final boolean isModal) {
		onRPCOut(isModal);
		callService(new AsyncCallback<T>() {
			@Override
			public void onSuccess(final T result) {
				onRPCIn();
				RPCCall.this.onSuccess(result);
				TrackingApp.getClientFactory().getLoadingDialog().hide();
			}

			@Override
			public void onFailure(Throwable caught) {
				onRPCIn();
				GWT.log(caught.toString(), caught);
				try {
					throw caught;
				} catch (InvocationException invocationException) {
					if (retriesLeft <= 0) {
						RPCCall.this.onFailure(invocationException);
					} else {
						call(retriesLeft - 1, isModal); // retry call
					}
				} catch (IncompatibleRemoteServiceException remoteServiceException) {
					// TODO: out of date
					// new BHDialogs("Warning",
					// "This app is out of date, please update to the newest version",
					// null, "Update", "Cancel", new ConfirmCallback() {
					// @Override
					// public void onOk() {
					// SuperFlashCard.getPurchaseManager().openMarketPlace();
					// }
					// @Override
					// public void onCancel() {
					// }
					// }).show();
				} catch (SerializationException serializationException) {
					ClientUtils.log("A serialization error occurred. Please try again later.");
					// } catch (ServerException e) {
					// User not logged in. Redirect to login page
				} catch (RequestTimeoutException e) {
					ClientUtils.log("This is taking too long, try again");
				} catch (Throwable e) {// application exception
					RPCCall.this.onFailure(e);
				}
			}
		});
	}

	private void onRPCIn() {
		TrackingApp.getClientFactory().getLoadingDialog().hide();
	}

	private void onRPCOut(boolean isModal) {
		TrackingApp.getClientFactory().getLoadingDialog().show();
	}

	public void retry(int retryCount) {
		retry(retryCount, true);
	}

	public void retry(int retryCount, boolean isModal) {
		if (ClientUtils.isOnline())
			call(retryCount, isModal);
		else {
			this.onSuccess(null);
		}
	}
}