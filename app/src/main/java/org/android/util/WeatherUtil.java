package org.android.util;

import android.content.Context;

import org.android.dao.OrmWeather;
import org.android.data.local.ILocalDataSource;
import org.android.data.model.Current;
import org.android.data.model.ForecastDay;
import org.android.data.model.ForecastWeather;
import org.android.data.model.Hour;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by mehdi on 11/12/2017.
 */
public class WeatherUtil {
    private boolean isMetric = true;
    private String selectedLang;
    private ILocalDataSource localDataSource;
    public static final int FORECAST_COUNT_DAYS = 7;
    private SimpleDateFormat fmt;
    //private FileManager fileManager;

    public WeatherUtil(Context ctx) {

        fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        //fileManager = FileManager.getInstance(ctx.getAssets());
        selectedLang = LocaleHelper.getLanguage(ctx);
    }

    public OrmWeather getCurrentOrmWeather(long cityId, ForecastWeather response) throws ParseException {
        Current current = response.getCurrent();
        ForecastDay forecastDay = response.getForecast().getForecastday().get(0);
        OrmWeather weather = new OrmWeather();
        weather.setCity_id(cityId);
        weather.setCity_name(response.getLocation().getName());
        weather.setDt(fmt.parse(current.getLastUpdated()));
        weather.setClouds(current.getCloud());
        weather.setHumidity(current.getHumidity());
        weather.setPressure(isMetric ? convertMbToMmHg(current.getPressureMb()) : current.getPressureIn());
        weather.setTemp(isMetric ? current.getTempC() : current.getTempF());
        weather.setTemp_min(isMetric ? forecastDay.getDay().getMintempC() : forecastDay.getDay().getMintempF());
        weather.setTemp_max(isMetric ? forecastDay.getDay().getMaxtempC() : forecastDay.getDay().getMaxtempF());
        weather.setIs_day(current.getIs_day() == 1);
        weather.setIcon(current.getCondition().getIcon());
        //String localizedCondition = fileManager.getCondition(current.getCondition().getCode(), selectedLang);
       // if (TextUtils.isEmpty(localizedCondition)) {
            //localizedCondition =;
       // }
        weather.setCondition_text( current.getCondition().getText());
        weather.setCondition_code(current.getCondition().getCode());
        weather.setWind_speed(isMetric ? convertKphToMps(current.getWindKph()) : current.getWindMph());
        weather.setWind_dir(response.getCurrent().getWindDir());
        return weather;
    }

    public void getHourlyOrmWeather(long cityId, ForecastWeather response, List<OrmWeather> ormWeatherList) throws ParseException {
        for (ForecastDay forecastDay : response.getForecast().getForecastday()) {
            for (Hour hour : forecastDay.getHours()) {
                if (fmt.parse(hour.getTime()).after(Calendar.getInstance().getTime())) {//no save old forecast
                    OrmWeather weather = new OrmWeather();
                    weather.setCity_id(cityId);
                    weather.setCity_name(response.getLocation().getName());
                    weather.setDt(fmt.parse(hour.getTime()));
                    weather.setClouds(hour.getCloud());
                    weather.setHumidity(hour.getHumidity());
                    weather.setPressure(isMetric ? convertMbToMmHg(hour.getPressureMb()) : hour.getPressureIn());
                    weather.setTemp(isMetric ? hour.getTempC() : hour.getTempF());
                    weather.setTemp_min(isMetric ? forecastDay.getDay().getMintempC() : forecastDay.getDay().getMintempF());
                    weather.setTemp_max(isMetric ? forecastDay.getDay().getMaxtempC() : forecastDay.getDay().getMaxtempF());
                    weather.setIcon(hour.getCondition().getIcon());
                    weather.setWind_speed(isMetric ? convertKphToMps(hour.getWindKph()) : hour.getWindMph());
                    weather.setWind_dir(hour.getWindDir());
                    weather.setRain(hour.getWillItRain());
                    weather.setSnow(hour.getWillItSnow());
                    //String localizedCondition = fileManager.getCondition(hour.getCondition().getCode(), selectedLang);
                   // if (TextUtils.isEmpty(localizedCondition)) {
                   //     localizedCondition = hour.getCondition().getText();
                   // }
                    weather.setCondition_text(hour.getCondition().getText());
                    weather.setCondition_code(hour.getCondition().getCode());
                    weather.setIs_day(hour.getIs_day() == 1);
                    ormWeatherList.add(weather);
                }
            }
        }
    }

    //convert MilliBars to mmHg.
    public Double convertMbToMmHg(Double pressureInMb) {
        return pressureInMb * 0.750062;
    }

    // convert Km per hour to m/s
    public Double convertKphToMps(Double windKph) {
        return windKph * 0.277778;
    }
}
