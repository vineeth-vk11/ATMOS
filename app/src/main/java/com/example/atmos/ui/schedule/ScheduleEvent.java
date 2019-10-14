package com.example.atmos.ui.schedule;

import java.util.Date;

public class ScheduleEvent {

    private Date time;
    private String name;
    private String location;
    private int day;
    private int tag;

    public ScheduleEvent(Date timeOfEvent, String nameOfEvent, String locationOfEvent, int dayOfEvent,int tagOfEvent) {
        time = timeOfEvent;
        name = nameOfEvent;
        location = locationOfEvent;
        day = dayOfEvent;
        tag = tagOfEvent;
    }

    public Date getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getTag() {
        return tag;
    }

    public int getDay() {
        return day;
    }
}
