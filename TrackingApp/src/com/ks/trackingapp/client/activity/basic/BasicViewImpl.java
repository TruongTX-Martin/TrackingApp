package com.ks.trackingapp.client.activity.basic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.flex.RootFlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent.Handler;
import com.ks.trackingapp.client.view.BhHeaderPanel;
import com.ks.trackingapp.client.view.leftmenu.LeftMenuSliding;

public class BasicViewImpl  implements BasicView{

	private static BasicViewImplUiBinder uiBinder = GWT
			.create(BasicViewImplUiBinder.class);

	interface BasicViewImplUiBinder extends UiBinder<Widget, LayoutBasic> {
	}

	protected LayoutBasic layoutBasic;
	protected LeftMenuSliding slidingMenu;
	
	public static class LayoutBasic{
		private BasicViewImpl basicView;
		@UiField
		protected RootFlexPanel mainPanel;
		@UiField
		protected BhHeaderPanel headerPanel;
		@UiField
		protected ScrollPanel scrollPanel;
		/**
		 * 
		 */
		public LayoutBasic(BasicViewImpl basicView) {
			this.basicView = basicView;
		}

		/**
		 * @return the mainPanel
		 */
		public RootFlexPanel getMainPanel() {
			return mainPanel;
		}

		/**
		 * @return the headerPanel
		 */
		public BhHeaderPanel getHeaderPanel() {
			return headerPanel;
		}

		/**
		 * @return the basicView
		 */
		public BasicViewImpl getBasicView() {
			return basicView;
		}
		
		public ScrollPanel getScrollPanel() {
			return scrollPanel;
		}
	}
	
	public BasicViewImpl() {
		this.layoutBasic = new LayoutBasic(this);
		uiBinder.createAndBindUi(this.layoutBasic);
		this.layoutBasic.getScrollPanel().setBounce(false);
		this.layoutBasic.getScrollPanel().addScrollEndHandler(new Handler() {
			@Override
			public void onScrollEnd(ScrollEndEvent event) {
				layoutBasic.getScrollPanel().refresh();
			}
		});
	}

	@Override
	public Widget asWidget() {
		return layoutBasic.getMainPanel();
	}

	@Override
	public LayoutBasic getLayoutBasic() {
		return layoutBasic;
	}

	@Override
	public LeftMenuSliding getSlidingMenu() {
		if(slidingMenu == null) {
			slidingMenu = new LeftMenuSliding();
			RootPanel.get().add(slidingMenu);
		}
		return slidingMenu;
	}

	@Override
	public BhHeaderPanel getBhHeaderPanel() {
		return layoutBasic.getHeaderPanel();
	}




}
