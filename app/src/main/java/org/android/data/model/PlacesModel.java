package org.android.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mehdi on 24/12/2017.
 */

public class PlacesModel {

    public PlacesModel() {

    }

    @SerializedName("data")
    public ArrayList<PlaceModel> data;


    public void setData(ArrayList<PlaceModel> data) {
        this.data = data;
    }

    public ArrayList<PlaceModel> getData() {
        return data;
    }
}
