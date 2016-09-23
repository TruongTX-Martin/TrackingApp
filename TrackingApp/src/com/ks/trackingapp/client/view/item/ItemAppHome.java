package com.ks.trackingapp.client.view.item;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.view.BHTouchImage;
import com.ks.trackingapp.client.view.VerticalTouchPanel;
import com.ks.trackingapp.shared.model.ItemApp;
import com.ks.trackingapp.shared.model.ItemComment;

public class ItemAppHome extends Composite {

	private static ItemAppHomeUiBinder uiBinder = GWT
			.create(ItemAppHomeUiBinder.class);

	interface ItemAppHomeUiBinder extends UiBinder<Widget, ItemAppHome> {
	}

	protected @UiField HTML htmlTitleApp;
	protected @UiField FlowPanel flowStar, flowComment;
	protected @UiField VerticalTouchPanel touchDetail, touchExtend;
	protected BHTouchImage imgExtend;
	public ItemAppHome() {
		initWidget(uiBinder.createAndBindUi(this));
		flowComment.setVisible(false);
		imgExtend = new BHTouchImage("images/ic_expand.png");
		imgExtend.setSize("30px", "30px");
		imgExtend.getElement().getStyle().setProperty("float", "right");
		imgExtend.getElement().getStyle().setProperty("marginRight", "20px");
		touchExtend.add(imgExtend);
	}

	public void showData(ItemApp itemApp, List<ItemComment> list) {
		htmlTitleApp.setText(itemApp.getAppName());
		StarRating rating = new StarRating((int) itemApp.getRating(), 5);
		flowStar.add(rating);
		flowComment.clear();
		if(list.size() > 0){
			int max  = list.size() > 10 ? 10: list.size();
			for(int i=0; i< max; i++) {
				ItemCommentView commentView = new ItemCommentView();
				commentView.showView(list.get(i));
				if(i == 0){
					commentView.showTopSeparate();
				}
				if(i == max -1){
					commentView.hideSeparate();
				}
				flowComment.add(commentView);
			}
		}
	}
	public FlowPanel getFlowComment(){
		return flowComment;
	}
	public VerticalTouchPanel getTouchExpand(){
		return touchExtend;
	}
	public VerticalTouchPanel getTouchDetail(){
		return touchDetail;
	}
	public BHTouchImage getImageExpand(){
		return imgExtend;
	}

}
