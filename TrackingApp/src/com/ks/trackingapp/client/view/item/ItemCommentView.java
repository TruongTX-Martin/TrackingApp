package com.ks.trackingapp.client.view.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.ks.trackingapp.shared.model.ItemComment;

public class ItemCommentView extends Composite {

	private static ItemCommentViewUiBinder uiBinder = GWT
			.create(ItemCommentViewUiBinder.class);
	
	private DateTimeFormat formatDate = DateTimeFormat.getFormat("dd-MM-yyyy");

	interface ItemCommentViewUiBinder extends UiBinder<Widget, ItemCommentView> {
	}

	protected @UiField
	HTML htmlAppname, htmlDate, htmlPlatform, htmlComment;
	protected @UiField FlowPanel flowRating;
	public ItemCommentView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	public void showView(ItemComment comment) {
		htmlAppname.setText(comment.getAppname());
		htmlDate.setText(formatDate.format(comment.getDate()));
		StarRating rate = new StarRating(comment.getRating(), 5);
		flowRating.add(rate);
		htmlPlatform.setText(comment.getPlatform());
		htmlComment.setText(comment.getComment());
	}

}
