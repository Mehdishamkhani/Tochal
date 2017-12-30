package org.android.data.model;

/**
 * Created by mehdi on 30/12/2017.
 */

public class PictureModel {


    public PictureModel(){}

    public int id;
    public String url_image;

    public int getId() {
        return id;
    }

    public String getUrl_image() {
        return url_image;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
