package com.ks.trackingapp.client.view.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.client.util.ClientUtils;
import com.ks.trackingapp.client.view.BHTouchImage;
import com.ks.trackingapp.shared.model.ItemComment;

public class ItemCommentView extends Composite {

	private static ItemCommentViewUiBinder uiBinder = GWT
			.create(ItemCommentViewUiBinder.class);
	

	interface ItemCommentViewUiBinder extends UiBinder<Widget, ItemCommentView> {
	}
	private DateTimeFormat format = DateTimeFormat.getFormat("dd-MM-yyyy");
	protected @UiField
	HTML htmlAppname, htmlDate, htmlPlatform, htmlComment;
	protected @UiField FlowPanel flowSeparate,flowSeparateTop;
	protected @UiField FlowPanel flowRating;
	private BHTouchImage imageExtend = new BHTouchImage("images/ic_extend.png");
	protected @UiField FlowPanel flowLeft,flowPlatform;
	public ItemCommentView() {
		initWidget(uiBinder.createAndBindUi(this));
		flowSeparateTop.setVisible(false);
		imageExtend.setSize("15px", "15px");
		flowLeft.add(imageExtend);
	}
	
	
	public void showView(ItemComment comment) {
		
		if(ClientUtils.validate(comment.getUserName())){
			htmlAppname.setHTML(comment.getUserName() +  "<font color='#FFBB2B'> Rate:" + comment.getRating()+"/5 </font>" );
		}else{
			htmlAppname.setHTML("Guess " + "<font color='#FFBB2B'> Rate:" + comment.getRating()+"/5 </font>");
		}
		htmlDate.setText(format.format(comment.getDate()));
//		StarRating rate = new StarRating(comment.getRating(), 5);
		flowRating.setVisible(false);
		htmlPlatform.setText(comment.getPlatform());
		htmlComment.setText(comment.getComment());
	}
	public void showCommentApp(ItemComment comment){
		flowPlatform.setWidth("15%");
		flowLeft.setVisible(false);
		if(ClientUtils.validate(comment.getUserName())){
			htmlAppname.setHTML(comment.getUserName());
		}else{
			htmlAppname.setHTML("Guess");
		}
		htmlDate.setText(format.format(comment.getDate()));
		StarRating rate = new StarRating(comment.getRating(), 5);
		flowRating.add(rate);
		htmlPlatform.setText(comment.getPlatform());
		htmlComment.setText(comment.getComment());
	}
	public void hideSeparate(){
		flowSeparate.setVisible(false);
	}
	public void showTopSeparate(){
		flowSeparateTop.setVisible(true);
	}

}
