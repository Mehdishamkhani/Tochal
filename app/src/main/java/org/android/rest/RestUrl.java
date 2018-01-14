package org.android.rest;


abstract class RestUrl {

    final static String FORECAST = "http://www.snow-forecast.com/resorts/Tochal/6day/auth_feed.json";
    final static String BASE = "http://82.99.213.42:8080/api/v1/";
    final static String PLACES = BASE + "places";
    final static String STATIONS = BASE + "places?type=1";
    final static String CONFIG = BASE + "config";
    final static String FEEDBACK = BASE + "tickets";

}
