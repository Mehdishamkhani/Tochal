package org.android.data.model;


import org.android.dao.OrmWeather;

import java.util.List;


public class DayPager {

    private List<PeriodsModel> hours;
    private String type;

    public DayPager() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PeriodsModel> getHours() {
        return hours;
    }

    public void setHours(List<PeriodsModel> hours) {
        this.hours = hours;
    }



}
