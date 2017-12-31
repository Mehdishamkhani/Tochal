package org.android.rest;

import android.content.Context;

import com.android.volley.Request;

import org.android.BuildConfig;
import org.android.TochalApp;
import org.android.data.model.Config;
import org.android.data.model.ForecastModel;
import org.android.data.model.ForecastWeather;
import org.android.data.model.GalleryModel;
import org.android.data.model.PlaceModel;
import org.android.data.model.PlacesModel;

import java.util.HashMap;
import java.util.Map;


public class RequestRepository {
    public final static int Event_Invite = 0;
    public final static int Event_Join = 1;
    public final static int Event_MayBe = 2;
    public final static int Event_Not_Going = 3;
    Context mcontext;
    private String volley_tag_Request;

    public RequestRepository() {

    }

    public RequestRepository(Context mcontext, String volley_tag_Request) {
        this.mcontext = mcontext;
        this.volley_tag_Request = volley_tag_Request;
        NetworkManager.getInstance();
    }

    public RequestRepository(Context mcontext) {
        this.mcontext = mcontext;
        this.volley_tag_Request = "a";
    }


    public void getWeather(String latLng, int days, final MyNetworkListener<ForecastWeather> listener) {

        String url = BuildConfig.BASE_WEATHER_URL + "forecast.json?key=" + BuildConfig.WEATHER_API_KEY + "&q=" + latLng + "&days=" + days;
        Map<String, String> params = new HashMap<String, String>();
        //params.put("username", username);
        //params.put("password", password);
        CustomGsonRequest sr = new CustomGsonRequest<ForecastWeather>(url, Request.Method.GET, ForecastWeather.class, params, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);
    }


    public void getWeathers(final MyNetworkListener<ForecastModel> listener) {


        Map<String, String> params = new HashMap<String, String>();
        //params.put("username", username);
        //params.put("password", password);
        CustomGsonRequest sr = new CustomGsonRequest<ForecastModel>(RestUrl.FORECAST, Request.Method.GET, ForecastModel.class, params, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);
    }


    public void getPlaces(final MyNetworkListener<PlacesModel> listener) {


        Map<String, String> params = new HashMap<String, String>();
        //params.put("username", username);
        //params.put("password", password);
        CustomGsonRequest sr = new CustomGsonRequest<PlacesModel>(RestUrl.PLACES, Request.Method.GET, PlacesModel.class, params, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);
    }


    public void getStations(final MyNetworkListener<PlacesModel> listener) {


        Map<String, String> params = new HashMap<String, String>();
        //params.put("username", username);
        //params.put("password", password);
        CustomGsonRequest sr = new CustomGsonRequest<PlacesModel>(RestUrl.STATIONS, Request.Method.GET, PlacesModel.class, params, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);
    }


    public void getGallery(int id, final MyNetworkListener<GalleryModel> listener) {


        Map<String, String> params = new HashMap<String, String>();
        //params.put("username", username);
        //params.put("password", password);
        String url = RestUrl.PLACES + "/" + id + "/images";
        CustomGsonRequest sr = new CustomGsonRequest<GalleryModel>(url, Request.Method.GET, GalleryModel.class, params, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);
    }

    public void Get_Config(final MyNetworkListener<Config> listener) {

        Map<String, String> params = new HashMap<String, String>();
        //params.put("username", username);
        //params.put("password", password);

        CustomGsonRequest sr = new CustomGsonRequest<Config>(RestUrl.CONFIG, Request.Method.GET, Config.class, params, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);

    }
}

