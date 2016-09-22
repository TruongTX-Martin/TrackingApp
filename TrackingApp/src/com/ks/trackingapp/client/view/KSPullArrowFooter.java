/**
 * 
 */
package com.ks.trackingapp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.pull.PullArrowWidget;

/**
 * @author hungzombxd
 *
 */
public class KSPullArrowFooter extends Composite implements PullArrowWidget {
	
	/**
	 * 
	 */
	private static KSPullArrowFooterUiBinder uiBinder = GWT.create(KSPullArrowFooterUiBinder.class);

	interface KSPullArrowFooterUiBinder extends UiBinder<Widget, KSPullArrowFooter> {
	}
	
	@UiField protected FlowPanel loadingPanel;
	@UiField protected HTML htmlLoading;
	
	public KSPullArrowFooter() {
		initWidget(uiBinder.createAndBindUi(this));
		loadingPanel.setVisible(false);
	}
	
	  @Override
	  public Widget asWidget() {
	    return this;
	  }

	/* (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel.PullWidget#onScroll(int)
	 */
	@Override
	public void onScroll(int positionY) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel.PullWidget#getHeight()
	 */
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel.PullWidget#getStateSwitchPosition()
	 */
	@Override
	public int getStateSwitchPosition() {
		return 0;
	}

	@Override
	public void setHTML(String html) {
		 String htmlToSet = html;
		    if (html == null) {
		      htmlToSet = "";
		    }
		htmlLoading.setHTML(htmlToSet);
	}

	@Override
	public void showArrow() {
		loadingPanel.setVisible(false);
		this.setVisible(false);
	}

	@Override
	public void showLoadingIndicator() {
		loadingPanel.setVisible(true);
		this.setVisible(true);
	}

	@Override
	public void showError() {
		htmlLoading.setHTML("Load Error");
		loadingPanel.setVisible(false);
		this.setVisible(true);
	}

	public FlowPanel getLoadingPanel() {
		return loadingPanel;
	}

}
