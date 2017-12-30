package org.android.data;

import org.android.data.model.MMMModel;
import org.android.data.model.PeriodsModel;
import org.jetbrains.annotations.Contract;

/**
 * Created by mehdi on 24/12/2017.
 */

public abstract class WeatherType {

    public static final String MIN = "min";
    public static final String MID = "mid";
    public static final String MAX = "max";


    public static MMMModel getMMM(String type, PeriodsModel periodsModel) {

        MMMModel wtype = periodsModel.min;

        switch (type) {
            case WeatherType.MIN:
                wtype = periodsModel.min;
                break;
            case WeatherType.MID:
                wtype = periodsModel.mid;
                break;
            case WeatherType.MAX:
                wtype = periodsModel.max;
                break;
        }

        return wtype;
    }
}
