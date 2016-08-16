/**
 * 
 */
package com.ks.trackingapp.client.view;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.googlecode.mgwt.ui.client.widget.input.MPasswordTextBox;
import com.ks.trackingapp.client.util.ClientUtils;

/**
 * @author hungzombxd
 *
 */
public class KSMPasswordTextBox extends MPasswordTextBox {
	public KSMPasswordTextBox() {
		super();
//		this.setStyleName(CssToken.INPUT_PRIMARY_CENTER, true);
		sinkEvents(Event.ONKEYDOWN);
		sinkEvents(Event.ONBLUR);
		sinkEvents(Event.ONFOCUS);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		switch (event.getTypeInt()) {
		case Event.ONBLUR:
			ClientUtils.hideKeyboard();
			break;
		case Event.ONKEYDOWN: 
			box.getElement().getStyle().setProperty("backgroundColor", "transparent");
			if(event.getKeyCode() == KeyCodes.KEY_ENTER){
				ClientUtils.hideKeyboard();
			} 
			break;
		case Event.ONFOCUS: 
			box.getElement().getStyle().setProperty("backgroundColor", "transparent");
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		super.setText(text);
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase#setPlaceHolder(java.lang.String)
	 */
	@Override
	public void setPlaceHolder(String value) {
		super.setPlaceHolder(value);
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase#getPlaceHolder()
	 */
	@Override
	public String getPlaceHolder() {
		return super.getPlaceHolder();
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setHeight(java.lang.String)
	 */
	@Override
	public void setHeight(String height) {
		super.setHeight(height);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		super.setWidth(width);
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean b) {
		super.setFocus(b);
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(String value) {
		super.setValue(value);
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.widget.base.MValueBoxBase#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
	}
	
	public void showInValid() {
		box.getElement().getStyle().setProperty("backgroundColor","rgba(255, 0, 0, 0.2)");
	}
}
