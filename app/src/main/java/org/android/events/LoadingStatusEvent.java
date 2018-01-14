package org.android.events;


public class LoadingStatusEvent {

    private int status;

    public LoadingStatusEvent(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
