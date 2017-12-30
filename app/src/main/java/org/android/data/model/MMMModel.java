package org.android.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mehdi on 22/12/2017.
 */

public class MMMModel {

    @SerializedName("psnow")
    public String psnow;

    @SerializedName("pprec")
    public String pprec;

    @SerializedName("pphrase")
    public String pphrase;

    @SerializedName("plcphrase")
    public String plcphrase;

    @SerializedName("psymbol")
    public String psymbol;

    @SerializedName("pwphrase")
    public String pwphrase;

    @SerializedName("plcwphrase")
    public String plcwphrase;

    @SerializedName("pwsymbol")
    public String pwsymbol;

    @SerializedName("pmax")
    public String pmax;

    @SerializedName("pmin")
    public String pmin;

    @SerializedName("pwind")
    public float pwind;

    @SerializedName("prh")
    public String prh;

    @SerializedName("pminchill")
    public String pminchill;


}
