package com.example.atmos.ui.schedule;

import java.util.Date;

public class ScheduleEvent {

    private Date time;
    private String name;
    private String location;
    private int day;
    private int tag;
    private boolean bookmarked;

    public static final int SCHEDULE_EVENT_COMPETITION = 1;
    public static final int SCHEDULE_EVENT_WORKSHOP = 2;
    public static final int SCHEDULE_EVENT_TALK = 3;


    public ScheduleEvent(Date timeOfEvent, String nameOfEvent, String locationOfEvent, int dayOfEvent,int tagOfEvent, boolean eventIsBookmarked) {
        time = timeOfEvent;
        name = nameOfEvent;
        location = locationOfEvent;
        day = dayOfEvent;
        tag = tagOfEvent;
        bookmarked = eventIsBookmarked;
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

    public boolean isBookmarked() {
        return bookmarked;
    }
}
