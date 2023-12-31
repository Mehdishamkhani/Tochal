package org.android.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "ORM_WEATHER".
 */
@Entity
public class OrmWeather {

    @Id
    private Long id;
    private Long city_id;
    private String city_name;
    private java.util.Date dt;
    private Double temp;
    private Double temp_min;
    private Double temp_max;
    private Double pressure;
    private Integer humidity;
    private Integer clouds;
    private Double wind_speed;
    private String wind_dir;
    private Integer rain;
    private Integer snow;
    private String icon;
    private Integer condition_code;
    private String condition_text;
    private Boolean is_day;

    @Generated
    public OrmWeather() {
    }

    public OrmWeather(Long id) {
        this.id = id;
    }

    @Generated
    public OrmWeather(Long id, Long city_id, String city_name, java.util.Date dt, Double temp, Double temp_min, Double temp_max, Double pressure, Integer humidity, Integer clouds, Double wind_speed, String wind_dir, Integer rain, Integer snow, String icon, Integer condition_code, String condition_text, Boolean is_day) {
        this.id = id;
        this.city_id = city_id;
        this.city_name = city_name;
        this.dt = dt;
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
        this.clouds = clouds;
        this.wind_speed = wind_speed;
        this.wind_dir = wind_dir;
        this.rain = rain;
        this.snow = snow;
        this.icon = icon;
        this.condition_code = condition_code;
        this.condition_text = condition_text;
        this.is_day = is_day;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCity_id() {
        return city_id;
    }

    public void setCity_id(Long city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public java.util.Date getDt() {
        return dt;
    }

    public void setDt(java.util.Date dt) {
        this.dt = dt;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public Integer getRain() {
        return rain;
    }

    public void setRain(Integer rain) {
        this.rain = rain;
    }

    public Integer getSnow() {
        return snow;
    }

    public void setSnow(Integer snow) {
        this.snow = snow;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getCondition_code() {
        return condition_code;
    }

    public void setCondition_code(Integer condition_code) {
        this.condition_code = condition_code;
    }

    public String getCondition_text() {
        return condition_text;
    }

    public void setCondition_text(String condition_text) {
        this.condition_text = condition_text;
    }

    public Boolean getIs_day() {
        return is_day;
    }

    public void setIs_day(Boolean is_day) {
        this.is_day = is_day;
    }


}
