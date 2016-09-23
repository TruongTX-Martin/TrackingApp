package com.ks.trackingapp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ks.trackingapp.shared.interfacemodel.IBasic;

public class DeleteObjectEvent extends GwtEvent<DeleteObjectEventHandler>{
	
	public static Type<DeleteObjectEventHandler> TYPE = new Type<DeleteObjectEventHandler>();
	
	private IBasic basic;
	
	public DeleteObjectEvent(IBasic basic) {
		this.basic = basic;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DeleteObjectEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteObjectEventHandler handler) {
		handler.deleteObject(this);
	}
	
	public IBasic getBasic() {
		return basic;
	}

}
