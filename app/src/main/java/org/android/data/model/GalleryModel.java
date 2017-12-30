package org.android.data.model;

import java.util.ArrayList;

/**
 * Created by mehdi on 30/12/2017.
 */

public class GalleryModel {

    public GalleryModel() {
    }

    public ArrayList<PictureModel> data;


    public ArrayList<PictureModel> getData() {
        return data;
    }


    public void setData(ArrayList<PictureModel> data) {
        this.data = data;
    }
}
