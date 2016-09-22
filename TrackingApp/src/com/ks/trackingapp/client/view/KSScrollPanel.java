package com.ks.trackingapp.client.view;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Timer;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent.Handler;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;

public class KSScrollPanel extends ScrollPanel {
	
	private Postion postion = null;
	private String id = null;
	private Map<String, Postion> postionMaps = null;
	
	public class Postion {
		
		private int x = 0;
		private int y = 0;
		
		public Postion() {
		}
		
		public Postion(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public void setX(int x) {
			this.x = x;
		}
		
		public int getY() {
			return y;
		}
		
		public void setY(int y) {
			this.y = y;
		}
	}
	
	public KSScrollPanel() {
		super();
		postion = new Postion();
		postionMaps = new HashMap<String, KSScrollPanel.Postion>();
		setWidth("100%");
		setShowHorizontalScrollBar(false);
		setShowVerticalScrollBar(false);
		setMomentum(true);
		this.addScrollEndHandler(new Handler() {
			@Override
			public void onScrollEnd(ScrollEndEvent event) {
				try {
					postion.setX(getX());
					postion.setY(getY());
					if(id != null){
						postionMaps.put(id, new Postion(getX(), getY()));
					}
					KSScrollPanel.super.refresh();
				} catch (Exception e) {
				}
			}
		});
		this.setBounce(false);
	}
	
	public void scrollToPage(final int y) {
		super.refresh();
		new Timer() {
			@Override
			public void run() {
				double page_div = y / KSScrollPanel.this.getOffsetHeight();
				int page = 0;
				if(page_div < 1){
					page = 0;
				} else {
					page = (int)(page_div + 0.5);
				}
				if(page == 0){
					KSScrollPanel.super.scrollTo(0, 0);
				} else {
					KSScrollPanel.super.scrollToPage(0, page, 300);
				}
			}
		}.schedule(100);
	}
	
	@Override
	public void refresh() {
		super.refresh();
	}
	
	public void refresh(boolean isScrollToOldPostion) {
		super.refresh();
		if(isScrollToOldPostion && postion != null){
			scrollTo(postion.getX(), postion.getY());
		} else {
			super.scrollTo(0, 0);
		}
	}
	
	public void refresh(final String id) {
		super.refresh();
		new Timer() {
			
			@Override
			public void run() {
				Postion postion = postionMaps.get(id);
				if(postion != null){
					scrollTo(postion.getX(), postion.getY());
				} else {
					KSScrollPanel.super.scrollTo(0, 0);
				}
			}
		}.schedule(100);
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
