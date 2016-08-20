package com.ks.trackingapp.client.activity.allapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.input.search.MSearchBox;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.ks.trackingapp.client.activity.basic.BasicViewImpl;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.view.BHTouchImage;
import com.ks.trackingapp.client.view.item.ItemAppView;
import com.ks.trackingapp.shared.Config;
import com.ks.trackingapp.shared.model.ItemApp;

public class AllAppViewImpl extends BasicViewImpl implements AllAppView {

	private static AllAppViewImplUiBinder uiBinder = GWT
			.create(AllAppViewImplUiBinder.class);

	interface AllAppViewImplUiBinder extends UiBinder<Widget, AllAppViewImpl> {
	}

	protected @UiField MSearchBox textbox;
	protected @UiField ScrollPanel scrollPanel;
	protected @UiField FlowPanel panelApps;
	private BHTouchImage btnAdd = new BHTouchImage("images/ic_addnew.png");
	private int HEIGHT_TEXTBOX = 50;
	private Map<Long, ItemAppView> mapItemApp = new HashMap<Long, ItemAppView>();
	public AllAppViewImpl() {
		this.layoutBasic.getScrollPanel().add(uiBinder.createAndBindUi(this));
		this.getBhHeaderPanel().showNavigation(false);
		this.layoutBasic.getHeaderPanel().setCenter(Config.ITEMSCREEN_ALLAPP);
		btnAdd.setSize("35px", "35px");
		btnAdd.getElement().getStyle().setMarginRight(10, Unit.PX);
		this.layoutBasic.getHeaderPanel().getRightPanel().add(btnAdd);
		refreshScrollView();
	}
	
	private void refreshScrollView() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				scrollPanel.setHeight((ClientUtils.getScreenHeight() - HEIGHT_TEXTBOX)
						+ "px");
			}
		});
	}

	@Override
	public void showItemApp(ArrayList<ItemApp> list) {
		panelApps.clear();
		if(list == null || list.size() == 0){
			HTML html = new HTML("You don't have any app.");
			html.getElement().getStyle().setTextAlign(TextAlign.CENTER);
			html.getElement().getStyle().setPadding(10, Unit.PX);
			html.getElement().getStyle().setFontSize(1.4, Unit.EM);
			html.getElement().getStyle().setColor("#ffffff");
			panelApps.add(html);
			return;
		}else{
			for (int i=0; i < list.size(); i++) {
				ItemAppView appView = new ItemAppView();
				appView.showView(list.get(i));
				panelApps.add(appView);
				mapItemApp.put(list.get(i).getId(), appView);
			}
		}
	}

	@Override
	public MSearchBox getTextBoxSearch() {
		return textbox;
	}

	@Override
	public Map<Long, ItemAppView> getMapItemApp() {
		return mapItemApp;
	}

	@Override
	public BHTouchImage getButtonAddNew() {
		return btnAdd;
	}

}
