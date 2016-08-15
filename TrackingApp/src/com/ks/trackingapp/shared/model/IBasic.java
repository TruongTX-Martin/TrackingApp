package com.ks.trackingapp.shared.model;

import java.io.Serializable;
import com.google.gwt.user.client.rpc.IsSerializable;

public interface IBasic extends Serializable, IsSerializable {
	public Object getId();
	public void setId(Object id);
	public boolean isEditing();
	public void setEditing(boolean editing);
}
