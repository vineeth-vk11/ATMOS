package com.example.atmos.ui.home;

class Event {

    Event(String eventName, String eventDateAndTime) {
        this.eventName = eventName;
        this.eventDateAndTime = eventDateAndTime;
    }

    private String eventName;
    private String eventDateAndTime;

    String getEventName() {
        return eventName;
    }

    String getEventDateAndTime() {
        return eventDateAndTime;
    }
}
