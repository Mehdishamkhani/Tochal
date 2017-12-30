package org.android.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mehdi on 24/12/2017.
 */

public class WorkModel {


    public WorkModel() {}


    @SerializedName("day")
    public int day;
    @SerializedName("open_time")
    public String open_time;
    @SerializedName("close_time")
    public String close_time;
    @SerializedName("is_open")
    public int is_open;
    @SerializedName("day_title")
    public String day_title;
    @SerializedName("maintenance_description")
    public String maintenance_description;
    @SerializedName("maintenance_end")
    public String maintenance_end;
    @SerializedName("maintenance_start")
    public String maintenance_start;


}

