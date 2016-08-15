package com.ks.trackingapp.client.activity;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.ks.trackingapp.client.activity.comment.CommentPlace;
import com.ks.trackingapp.client.activity.home.HomePlace;

@WithTokenizers({HomePlace.Tokenizer.class,CommentPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper{

}
