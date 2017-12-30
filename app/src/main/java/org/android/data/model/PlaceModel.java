package org.android.data.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehdi on 24/12/2017.
 */

public class PlaceModel {

    public PlaceModel() {}

    @SerializedName("id")
    public int id;

    @SerializedName("parent_id")
    public int parent_id;

    @SerializedName("place_type_id")
    public int place_type_id;

    @SerializedName("place_type")
    public String place_type;

    @SerializedName("place_type_label")
    public String place_type_label;

    @SerializedName("name")
    public String name;

    @SerializedName("phone_number")
    public String phone_number;

    @SerializedName("manager_name")
    public String manager_name;

    @SerializedName("lat")
    public String lat;

    @SerializedName("lng")
    public String lng;

    @SerializedName("description")
    public String description;

    @SerializedName("forecast_name")
    public String forecast_name;

    @SerializedName("forecast_id")
    public int forecast_id;

    @SerializedName("height")
    public String height;

    @SerializedName("work_times")
    public ArrayList<WorkModel> work_times;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setForecast_id(int forecast_id) {
        this.forecast_id = forecast_id;
    }

    public void setForecast_name(String forecast_name) {
        this.forecast_name = forecast_name;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPlace_type(String place_type) {
        this.place_type = place_type;
    }

    public void setPlace_type_id(int place_type_id) {
        this.place_type_id = place_type_id;
    }

    public void setPlace_type_label(String place_type_label) {
        this.place_type_label = place_type_label;
    }

    public void setWork_times(ArrayList<WorkModel> work_times) {
        this.work_times = work_times;
    }

    public int getId() {
        return id;
    }

    public ArrayList<WorkModel> getWork_times() {
        return work_times;
    }

    public int getForecast_id() {
        return forecast_id;
    }

    public String getHeight() {
        return height;
    }

    public int getParent_id() {
        return parent_id;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public int getPlace_type_id() {
        return place_type_id;
    }

    public String getDescription() {
        return description;
    }

    public String getForecast_name() {
        return forecast_name;
    }

    public String getManager_name() {
        return manager_name;
    }

    public String getName() {
        return name;
    }

    public String getPlace_type() {
        return place_type;
    }

    public String getPlace_type_label() {
        return place_type_label;
    }


}
