package org.android.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mehdi on 22/12/2017.
 */

public class ForecastModel {

    @SerializedName("forecast")
    public WeatherModel forecast;


    public ForecastModel() {

    }


    public void setForecast(WeatherModel forecast) {
        this.forecast = forecast;
    }

    public WeatherModel getForecast() {
        return forecast;
    }
}
