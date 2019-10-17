package com.example.atmos19.api;

import com.example.atmos19.ui.schedule.ScheduleEvent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventsInterface {
    @GET("events")
    Call<ArrayList<ScheduleEvent>> getEvents();

    @GET("events?fields=name,startTime,endTime,tagline,venue,price,prize")
    Call<ArrayList<ScheduleEvent>> getEventSchedule();

    @GET("events/{id}")
    Call<ScheduleEvent> getEventDetails(@Path("id") String id);
}
