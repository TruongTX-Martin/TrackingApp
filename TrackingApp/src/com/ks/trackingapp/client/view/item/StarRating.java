package com.ks.trackingapp.client.view.item;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class StarRating extends Composite implements ClickHandler,
		MouseOverHandler, MouseOutHandler, HasValue<Integer> {

	private HorizontalPanel mainPanel = new HorizontalPanel();

	private int rating = 0;
	private int rating_max = 10;
	private int hover_index = 0;
	private boolean read_only = false;
	private String star_selected_left_path = "images/ic_star_selected.png";
	private String star_selected_right_path = "images/ic_star_selected.png";
	private String star_unselected_left_path = "images/ic_star_unselected.png";
	private String star_unselected_right_path = "images/ic_star_unselected.png";
	private String star_hover_left_path = "images/ic_star_selected.png";
	private String star_hover_right_path = "images/ic_star_selected.png";

	/**
	 * Star rating widget
	 */
	public StarRating() {
		this(0, 10, false);
	}

	/**
	 * Star rating widget
	 * 
	 * @param read_only
	 */
	public StarRating(boolean read_only) {
		this(0, 10, read_only);
	}

	/**
	 * Star rating widget
	 * 
	 * @param rating
	 * @param rating_max
	 */
	public StarRating(int rating, int rating_max) {
		this(rating, rating_max, false);
	}

	/**
	 * Star rating widget
	 * 
	 * @param rating
	 * @param rating_max
	 * @param read_only
	 */
	public StarRating(int rating, int rating_max, boolean read_only) {
		this.setRating(rating);
		this.setRatingMax(rating_max);
		this.setReadOnly(read_only);
		this.buildWidget();
	}

	/**
	 * Star rating widget
	 * 
	 * @param rating
	 * @param rating_max
	 * @param read_only
	 * @param star_selected_left
	 * @param star_selected_right
	 * @param star_unselected_left
	 * @param star_unselected_right
	 * @param star_hover_left
	 * @param star_hover_right
	 */
	public StarRating(int rating, int rating_max, boolean read_only,
			String star_selected_left, String star_selected_right,
			String star_unselected_left, String star_unselected_right,
			String star_hover_left, String star_hover_right) {
		this.setRating(rating);
		this.setRatingMax(rating_max);
		this.setReadOnly(read_only);
		this.setStarSelectedLeft(star_selected_left);
		this.setStarSelectedRight(star_selected_right);
		this.setStarUnselectedLeft(star_unselected_left);
		this.setStarUnselectedRight(star_unselected_right);
		this.setStarHoverLeft(star_hover_left);
		this.setStarHoverRight(star_hover_right);
		this.buildWidget();
	}

	/**
	 * Builds the widget
	 */
	private void buildWidget() {
		Image.prefetch(this.getStarSelectedLeftPath());
		Image.prefetch(this.getStarSelectedRightPath());
		Image.prefetch(this.getStarUnselectedLeftPath());
		Image.prefetch(this.getStarUnselectedRightPath());
		Image.prefetch(this.getStarHoverLeftPath());
		Image.prefetch(this.getStarHoverRightPath());

		// Initialize
		initWidget(mainPanel);
		mainPanel.setStyleName("starrating");
		this.addMouseOutHandler(this);

		// Stars
		for (int i = 0; i < this.getRatingMax(); i++) {
			Image image = new Image();
			image.setSize("20px", "20px");
			// Settings
			image.setStyleName("star");
			image.setTitle("" + (i + 1));
			image.addClickHandler(this);
			image.addMouseOverHandler(this);

			mainPanel.add(image);
		}

		// If not readonly
		if (!this.isReadOnly()) {
			mainPanel.getElement().getStyle().setCursor(Style.Cursor.POINTER);
		}

		// Set the star images
		this.setStarImages();
	}

	/**
	 * 
	 * @param handler
	 * @return
	 */
	private HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}

	/**
	 * Resets the button images
	 */
	private void setStarImages() {
		for (int i = 0; i < this.getRatingMax(); i++) {
			Image image = (Image) mainPanel.getWidget(i);
			image.setUrl(this.getImagePath(i));
		}
	}

	/**
	 * Gets the star image based on the index
	 * 
	 * @param index
	 * @return
	 */
	private String getImagePath(int index) {
		String path = "";

		if (index % 2 == 0) {
			if (index >= this.getHoverIndex()) {
				if (index >= this.getRating()) {
					path = this.getStarUnselectedLeftPath();
				} else {
					path = this.getStarSelectedLeftPath();
				}
			} else {
				path = this.getStarHoverLeftPath();
			}
		} else {
			if (index >= this.getHoverIndex()) {
				if (index >= this.getRating()) {
					path = this.getStarUnselectedRightPath();
				} else {
					path = this.getStarSelectedRightPath();
				}
			} else {
				path = this.getStarHoverRightPath();
			}
		}

		return path;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating_max
	 *            the rating_max to set
	 */
	public void setRatingMax(int rating_max) {
		this.rating_max = rating_max;
	}

	/**
	 * @return the rating_max
	 */
	public int getRatingMax() {
		return rating_max;
	}

	/**
	 * @param hover_index
	 *            the hover_index to set
	 */
	public void setHoverIndex(int hover_index) {
		this.hover_index = hover_index;
	}

	/**
	 * @return the hover_index
	 */
	public int getHoverIndex() {
		return hover_index;
	}

	/**
	 * @param read_only
	 *            the read_only to set
	 */
	public void setReadOnly(boolean read_only) {
		this.read_only = read_only;
	}

	/**
	 * @return the read_only
	 */
	public boolean isReadOnly() {
		return read_only;
	}

	/**
	 * @param star_selected_left
	 *            the star_selected_left to set
	 */
	public void setStarSelectedLeft(String star_selected_left) {
		this.star_selected_left_path = star_selected_left;
	}

	/**
	 * @return the star_selected_left
	 */
	public String getStarSelectedLeftPath() {
		return star_selected_left_path;
	}

	/**
	 * @param star_selected_right
	 *            the star_selected_right to set
	 */
	public void setStarSelectedRight(String star_selected_right) {
		this.star_selected_right_path = star_selected_right;
	}

	/**
	 * @return the star_selected_right
	 */
	public String getStarSelectedRightPath() {
		return star_selected_right_path;
	}

	/**
	 * @param star_unselected_left
	 *            the star_unselected_left to set
	 */
	public void setStarUnselectedLeft(String star_unselected_left) {
		this.star_unselected_left_path = star_unselected_left;
	}

	/**
	 * @return the star_unselected_left
	 */
	public String getStarUnselectedLeftPath() {
		return star_unselected_left_path;
	}

	/**
	 * @param star_unselected_right
	 *            the star_unselected_right to set
	 */
	public void setStarUnselectedRight(String star_unselected_right) {
		this.star_unselected_right_path = star_unselected_right;
	}

	/**
	 * @return the star_unselected_right
	 */
	public String getStarUnselectedRightPath() {
		return star_unselected_right_path;
	}

	/**
	 * @param star_hover_left
	 *            the star_hover_left to set
	 */
	public void setStarHoverLeft(String star_hover_left) {
		this.star_hover_left_path = star_hover_left;
	}

	/**
	 * @return the star_hover_left
	 */
	public String getStarHoverLeftPath() {
		return star_hover_left_path;
	}

	/**
	 * @param star_hover_right
	 *            the star_hover_right to set
	 */
	public void setStarHoverRight(String star_hover_right) {
		this.star_hover_right_path = star_hover_right;
	}

	/**
	 * @return the star_hover_right
	 */
	public String getStarHoverRightPath() {
		return star_hover_right_path;
	}

	/**
	 * On mouse over event
	 */
	public void onMouseOver(MouseOverEvent event) {
	}

	/**
	 * On click event
	 */
	public void onClick(ClickEvent event) {
	}

	/**
	 * On mouse out event
	 */
	public void onMouseOut(MouseOutEvent event) {
		this.setHoverIndex(0);
		this.setStarImages();
	}

	/**
	 * Adds a ValueChangehandler
	 */
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Integer> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	/**
	 * Get the rating value
	 */
	public Integer getValue() {
		return this.getRating();
	}

	/**
	 * Set the rating value
	 * 
	 * @param value
	 *            the rating to set
	 */
	public void setValue(Integer value) {
		this.setRating(value);
		this.setStarImages();
	}

	/**
	 * Set the rating value
	 * 
	 * @param value
	 *            the rating to set
	 * @param fireEvents
	 *            fire events
	 */
	public void setValue(Integer value, boolean fireEvents) {
		this.setValue(value);
		if (fireEvents)
			ValueChangeEvent.fire(this, value);
	}

}
