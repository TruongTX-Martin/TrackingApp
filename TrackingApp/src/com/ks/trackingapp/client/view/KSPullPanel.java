/**
 * 
 */
package com.ks.trackingapp.client.view;

import com.googlecode.mgwt.ui.client.widget.panel.pull.PullPanel;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent.Handler;

/**
 * @author hungzombxd
 *
 */
public class KSPullPanel extends PullPanel{
	/**
	 * 
	 */
	public KSPullPanel() {
		getScrollPanel().addScrollEndHandler(new Handler() {
			
			@Override
			public void onScrollEnd(ScrollEndEvent event) {
				refresh();
			}
		});
	}
}
