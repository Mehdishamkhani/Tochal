package org.android.data.model;

/**
 * Created by mehdi on 25/12/2017.
 */

public  class TimeModel {

    private long remainingTime;
    private int type = 0;


    public TimeModel() {


    }


    public static TimeModel getInstance() {

        return new TimeModel();
    }

    public int getType() {
        return type;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }
}
