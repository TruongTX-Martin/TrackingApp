/**
 * 
 */
package com.ks.trackingapp.client.view;

import org.gwtbootstrap3.client.ui.Button;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author hungzombxd
 *
 */
public class KSButtonLoading extends Button {

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	  *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public enum BUTTONLOADING_STATE {
		NONE, RUNNING, SUCCESS, FAIL;
	}
	
	private HTML textButtonHTML = new HTML();
	private HTML statusHTML = new HTML();
	private BUTTONLOADING_STATE state = BUTTONLOADING_STATE.NONE;
	private String defaultStyle = "";
	
	public KSButtonLoading() {
		this.add(textButtonHTML);
		this.add(statusHTML);
		this.setStyleName("buttonLoading");
		textButtonHTML.setStyleName("buttonLoading-text", true);
		statusHTML.setStyleName("buttonLoading-status", true);
		updateState(state);
//		addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				state = 3;
//				updateState(state);
//			}
//		});
	}
	
	public KSButtonLoading(String text) {
		this();
		setText(text);
	}
	
	@Override
	public void setText(String text) {
		textButtonHTML.setHTML(text);
	}
	
	@Override
	public String getText() {
		return textButtonHTML.getHTML();
	}
	
	public BUTTONLOADING_STATE getState() {
		return state;
	}

	public void setState(BUTTONLOADING_STATE state) {
		this.state = state;
	}

	public void updateState(BUTTONLOADING_STATE state) {
		this.state= state;
		if(state == BUTTONLOADING_STATE.NONE) {
			setEnabled(true);
			this.setStyleName("buttonLoading");
		} else if(state == BUTTONLOADING_STATE.RUNNING) {
			setEnabled(false);
			this.setStyleName("buttonLoading buttonLoading-running");
		} else if(state == BUTTONLOADING_STATE.SUCCESS) {
			this.setStyleName("buttonLoading buttonLoading-success");
			setEnabled(false);
			new Timer() {
				
				@Override
				public void run() {
					updateState(BUTTONLOADING_STATE.NONE);
				}
			}.schedule(2000);
		} else if(state == BUTTONLOADING_STATE.FAIL) {
			setEnabled(false);
			this.setStyleName("buttonLoading buttonLoading-fail");
			new Timer() {
				
				@Override
				public void run() {
					updateState(BUTTONLOADING_STATE.NONE);
				}
			}.schedule(1500);
		}
		if(!defaultStyle.isEmpty()) 
			this.setStyleName(defaultStyle, true);
	}
	
	public boolean isEnableClick() {
		return state !=BUTTONLOADING_STATE.RUNNING;
	}

	public String getDefaultStyle() {
		return defaultStyle;
	}

	public void setDefaultStyle(String defaultStyle) {
		this.defaultStyle = defaultStyle;
	}
}
