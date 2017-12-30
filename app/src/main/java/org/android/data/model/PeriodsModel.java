package org.android.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mehdi on 22/12/2017.
 */

public class PeriodsModel implements Parcelable {

    public String pdayname;
    public String plcdayname;
    public int pdom;
    public String pdomend;
    public String ptstampstartutc;
    public String ptstampendutc;
    public String pname;
    public String pename;
    public String plcname;
    public String psunset;
    public String psunrise;
    public String pgrey;
    public float pflevel;
    public String psnod;
    public MMMModel min;
    public MMMModel mid;
    public MMMModel max;


    protected PeriodsModel(Parcel in) {
        pdayname = in.readString();
        plcdayname = in.readString();
        pdom = in.readInt();
        pdomend = in.readString();
        ptstampstartutc = in.readString();
        ptstampendutc = in.readString();
        pname = in.readString();
        pename = in.readString();
        plcname = in.readString();
        psunset = in.readString();
        psunrise = in.readString();
        pgrey = in.readString();
        pflevel = in.readFloat();
        psnod = in.readString();
    }

    public static final Creator<PeriodsModel> CREATOR = new Creator<PeriodsModel>() {
        @Override
        public PeriodsModel createFromParcel(Parcel in) {
            return new PeriodsModel(in);
        }

        @Override
        public PeriodsModel[] newArray(int size) {
            return new PeriodsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pdayname);
        parcel.writeString(plcdayname);
        parcel.writeInt(pdom);
        parcel.writeString(pdomend);
        parcel.writeString(ptstampstartutc);
        parcel.writeString(ptstampendutc);
        parcel.writeString(pname);
        parcel.writeString(pename);
        parcel.writeString(plcname);
        parcel.writeString(psunset);
        parcel.writeString(psunrise);
        parcel.writeString(pgrey);
        parcel.writeFloat(pflevel);
        parcel.writeString(psnod);
    }
}
