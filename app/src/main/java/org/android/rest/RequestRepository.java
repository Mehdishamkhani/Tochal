package org.android.rest;

import android.content.Context;

import com.android.volley.Request;

import org.android.data.model.Config;
import org.android.data.model.ForecastModel;
import org.android.data.model.GalleryModel;
import org.android.data.model.PlacesModel;
import org.android.rest.models.NoResponse;

import java.util.HashMap;
import java.util.Map;


public class RequestRepository {

    private Context mcontext;
    private String volley_tag_Request;

    public RequestRepository() {

    }

    public RequestRepository(Context mcontext, String volley_tag_Rsequest) {
        this.mcontext = mcontext;
        this.volley_tag_Request = volley_tag_Request;
        NetworkManager.getInstance();
    }

    public RequestRepository(Context mcontext) {
        this.mcontext = mcontext;
        this.volley_tag_Request = "a";
    }


    public void getWeathers(final MyNetworkListener<ForecastModel> listener) {

        CustomGsonRequest sr = new CustomGsonRequest<ForecastModel>(RestUrl.FORECAST, Request.Method.GET, ForecastModel.class, null, listener);
        sr.setTag(volley_tag_Request);
        sr.setAuth("XML57", "M.P@tochal.org", mcontext);
        NetworkManager.getInstance().requestQueue.add(sr);
    }


    public void getPlaces(final MyNetworkListener<PlacesModel> listener) {

        CustomGsonRequest sr = new CustomGsonRequest<PlacesModel>(RestUrl.PLACES, Request.Method.GET, PlacesModel.class, null, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);
    }


    public void getStations(final MyNetworkListener<PlacesModel> listener) {

        CustomGsonRequest sr = new CustomGsonRequest<PlacesModel>(RestUrl.STATIONS, Request.Method.GET, PlacesModel.class, null, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);
    }


    public void getGallery(int id, final MyNetworkListener<GalleryModel> listener) {


        String url = RestUrl.PLACES + "/" + id + "/images";
        CustomGsonRequest sr = new CustomGsonRequest<GalleryModel>(url, Request.Method.GET, GalleryModel.class, null, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);
    }

    public void Get_Config(final MyNetworkListener<Config> listener) {

        CustomGsonRequest sr = new CustomGsonRequest<Config>(RestUrl.CONFIG, Request.Method.GET, Config.class, null, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);

    }

    public void sendFeedback(String phone, String subject, String message, final MyNetworkListener<NoResponse> listener) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("phone_number", phone);
        params.put("subject", subject);
        params.put("description", message);

        CustomGsonRequest sr = new CustomGsonRequest<NoResponse>(RestUrl.FEEDBACK, Request.Method.POST, NoResponse.class, params, listener);
        sr.setTag(volley_tag_Request);
        NetworkManager.getInstance().requestQueue.add(sr);

    }
}

