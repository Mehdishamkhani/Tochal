package org.android.data.model;


import org.android.dao.OrmCity;
import org.android.dao.OrmWeather;

/**
 * Created by Igor Havrylyuk on 11.02.2017.
 */

public class CityWithWeather {

    private OrmCity mCity;
    private OrmWeather mWeather;

    public CityWithWeather() {
    }

    public CityWithWeather(OrmCity city, OrmWeather weather) {
        mCity = city;
        mWeather = weather;
    }

    public OrmCity getCity() {
        return mCity;
    }

    public void setCity(OrmCity city) {
        this.mCity = city;
    }

    public OrmWeather getWeather() {
        return mWeather;
    }

    public void setWeather(OrmWeather weather) {
        this.mWeather = weather;
    }
}
