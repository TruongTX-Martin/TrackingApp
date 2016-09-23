package com.ks.trackingapp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ReloadCommentEvent extends GwtEvent<ReloadCommentEventHandler> {

	public static Type<ReloadCommentEventHandler> TYPE = new Type<ReloadCommentEventHandler>();

	public ReloadCommentEvent() {
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ReloadCommentEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReloadCommentEventHandler handler) {
		handler.reloadComment(this);
	}

}
