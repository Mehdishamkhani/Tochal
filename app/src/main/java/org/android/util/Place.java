package org.android.util;

import android.util.Log;
import android.view.View;

import org.android.data.model.PlaceStatus;
import org.android.data.model.WorkModel;

/**
 * Created by mehdi on 28/12/2017.
 */

public class Place {


    public static PlaceStatus getPlaceStatus(WorkModel placeWorktime) {

        if (placeWorktime.maintenance_start != null && placeWorktime.maintenance_end != null) {

            if (TimeHelper.getStationStatus(placeWorktime.maintenance_start, placeWorktime.maintenance_end)) {

                Log.d("maintenance",placeWorktime.maintenance_description);

                return new PlaceStatus(false, true, placeWorktime.maintenance_description);

            }

        }

        return new PlaceStatus(TimeHelper.getStationStatus(placeWorktime.open_time, placeWorktime.close_time), false, "");

    }


}
