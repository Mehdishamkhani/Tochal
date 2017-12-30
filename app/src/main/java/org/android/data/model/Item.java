package org.android.data.model;


import com.google.gson.annotations.SerializedName;

public class Item {


    public static final int NO_RADIUS = 0;
    public static final int TOP_RADIUS = 1;
    public static final int BOT_RADIUS = 2;
    public static final int RADIUS = 3;


    @SerializedName("radius")
    private int radius = NO_RADIUS;

    @SerializedName("type")
    private String type;

    @SerializedName("wmodel")
    private WorkModel wmodel;

    @SerializedName("model")
    private PlaceModel model;

    @SerializedName("title")
    private String title;

    @SerializedName("icon")
    private int icon;

    @SerializedName("selected")
    private boolean selected;

    @SerializedName("isHeader")
    private boolean isHeader;


    public Item() {
    }

    public Item(String title, int icon, boolean isHeader) {
        this.icon = icon;
        this.title = title;
        this.isHeader = isHeader;
    }

    public Item(String title, int icon, String type, int radius) {
        this.icon = icon;
        this.title = title;
        this.type = type;
        this.radius = radius;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Item(PlaceModel model, boolean isHeader) {
        this.model = model;
        this.isHeader = isHeader;
    }

    public Item(PlaceModel model, int icon, boolean isHeader) {
        this.model = model;
        this.isHeader = isHeader;
        this.icon = icon;

    }

    public Item(PlaceModel model, int icon, boolean isHeader, int radius) {
        this.model = model;
        this.isHeader = isHeader;
        this.icon = icon;
        this.radius = radius;

    }

    public Item(WorkModel wmodel, boolean isHeader) {
        this.wmodel = wmodel;
        this.isHeader = isHeader;
    }

    public WorkModel getWmodel() {
        return wmodel;
    }

    public void setWmodel(WorkModel wmodel) {
        this.wmodel = wmodel;
    }

    public PlaceModel getPlaceModel() {
        return this.model;
    }

    public void setPlaceModel(PlaceModel model) {
        this.model = model;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return this.icon;
    }

    public String getTitle() {
        return this.title;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public int getRadius() {
        return radius;
    }
}
