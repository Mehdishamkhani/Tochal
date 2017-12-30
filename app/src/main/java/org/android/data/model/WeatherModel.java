package org.android.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mehdi on 22/12/2017.
 */

public class WeatherModel {
    @SerializedName("issueddate")
    public String issueddate;
    @SerializedName("issuedhr")
    public String issuedhr;
    @SerializedName("issueddow")
    public String issueddow;
    @SerializedName("gmtissued")
    public String gmtissued;
    @SerializedName("issuedmonth")
    public String issuedmonth;
    @SerializedName("issuedampm")
    public String issuedampm;
    @SerializedName("issuedyear")
    public String issuedyear;
    @SerializedName("periods")
    public ArrayList<PeriodsModel> periods;


    public WeatherModel() {

    }


    public void setGmtissued(String gmtissued) {
        this.gmtissued = gmtissued;
    }

    public void setIssuedampm(String issuedampm) {
        this.issuedampm = issuedampm;
    }

    public void setIssueddate(String issueddate) {
        this.issueddate = issueddate;
    }

    public void setIssueddow(String issueddow) {
        this.issueddow = issueddow;
    }

    public void setIssuedhr(String issuedhr) {
        this.issuedhr = issuedhr;
    }

    public void setIssuedmonth(String issuedmonth) {
        this.issuedmonth = issuedmonth;
    }

    public void setIssuedyear(String issuedyear) {
        this.issuedyear = issuedyear;
    }

    public void setPeriods(ArrayList<PeriodsModel> periods) {
        this.periods = periods;
    }

    public String getIssuedhr() {
        return issuedhr;
    }

    public String getGmtissued() {
        return gmtissued;
    }

    public String getIssuedampm() {
        return issuedampm;
    }

    public ArrayList<PeriodsModel> getPeriods() {
        return periods;
    }

    public String getIssueddate() {
        return issueddate;
    }

    public String getIssueddow() {
        return issueddow;
    }

    public String getIssuedmonth() {
        return issuedmonth;
    }

    public String getIssuedyear() {
        return issuedyear;
    }


}
