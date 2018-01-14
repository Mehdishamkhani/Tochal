package org.android.events;



public class ChangeEvent {

    public static final int CHANGE_CONTENT = 1;
    public static final int CHANGE_LANGUAGE = 2;

    private final int eventId;

    public ChangeEvent(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }
}
