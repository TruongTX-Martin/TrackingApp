package com.ks.trackingapp.client.activity.basic;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class BasicPlace extends Place {
	protected String token = "";
	protected Place previousPlace = null;

	public BasicPlace(Place previousPlace) {
		this.previousPlace = previousPlace;
	}

	public BasicPlace() {
	}

	public Place getPreviousPlace() {
		return previousPlace;
	}
	public void setPreviousPlace(Place previousPlace) {
		this.previousPlace = previousPlace;
	}

	public String getToken() {
		return this.token;
	}

	public static class Tokenizer implements PlaceTokenizer<BasicPlace> {
		@Override
		public String getToken(BasicPlace place) {
			return place.getToken();
		}

		@Override
		public BasicPlace getPlace(String token) {
			return new BasicPlace();
		}
	}
}
