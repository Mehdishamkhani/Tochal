package org.android.rest;

/**
 * Created by mehdi on 24/12/2017.
 */

abstract class RestUrl {

    final static String FORECAST = "http://www.snow-forecast.com/resorts/Pal/6day/auth_feed.json";

    private final static String BaseUrl = "http://82.99.213.42:8080/api/v1/";
    final static String PLACES = BaseUrl + "places";
    final static String STATIONS = BaseUrl + "places?type=1";
    final static String CONFIG = BaseUrl + "config";


}
