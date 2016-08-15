package com.ks.trackingapp.client.activity.base;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class BasePlace extends Place {
	protected String token = "";
	protected Place previousPlace = null;

	public BasePlace(Place previousPlace) {
		this.previousPlace = previousPlace;
	}

	public BasePlace() {
	}

	public Place getPreviousPlace() {
		return previousPlace;
	}

	public String getToken() {
		return this.token;
	}

	public static class Tokenizer implements PlaceTokenizer<BasePlace> {
		@Override
		public String getToken(BasePlace place) {
			return place.getToken();
		}

		@Override
		public BasePlace getPlace(String token) {
			return new BasePlace();
		}
	}
}
