package com.example.atmos.ui.schedule;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class EventRemainder {
    ScheduleEvent mevent;
    Context context;
    HashMap<String,String> hashMap;
    public EventRemainder(Context context)
    {
        this.context = context;

        hashMap = getHashMap("eventId");
    }
    public void createRemainder(ScheduleEvent mevent)
    {
        this.mevent = mevent;
        Calendar cal = Calendar.getInstance();
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = context.getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();
        String time = mevent.getStartTime();
        long millis = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
            Date dateObj = sdf.parse(time);
            millis = dateObj.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /** Inserting an event in calendar. */
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, "ATMOS : "+mevent.getName());
        values.put(CalendarContract.Events.DESCRIPTION, mevent.getName());
        values.put(CalendarContract.Events.ALL_DAY, 0);
        // event starts at 11 minutes from now
        values.put(CalendarContract.Events.DTSTART, millis - 15 * 60 * 1000);

        // ends 60 minutes from now
        values.put(CalendarContract.Events.DTEND, 0);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        Uri event = cr.insert(EVENTS_URI, values);
        Log.d("EventId",event.getLastPathSegment());
        // Display event id
        // Toast.makeText(getApplicationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();
        if(hashMap==null)
        {
            hashMap = new HashMap<>();
        }
        hashMap.put(mevent.getName(),event.getLastPathSegment());
        saveHashMap(hashMap,"eventId");
        /** Adding reminder for event added. */
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, 10);
        cr.insert(REMINDERS_URI, values);
    }
    public void removeRemainder(ScheduleEvent mevent)
    {
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + mevent.getName());
        String eventId = hashMap.get(mevent.getName());
        hashMap.remove(eventId);
        saveHashMap(hashMap,"eventId");
        ContentResolver cr = context.getContentResolver();
        long remainderId = checkIfReminderExist(cr,Long.parseLong(eventId));
        Log.d("RemainderId",String.valueOf(remainderId));
        deleteReminderOnEvent(remainderId,cr);

       // cr.delete(EVENTS_URI,CalendarContract.Events.DESCRIPTION=mevent.getName(),null);

    }
    public static void deleteReminderOnEvent(Long reminderId,ContentResolver contentResolver) {
        Uri reminderUri = ContentUris.withAppendedId(CalendarContract.Reminders.CONTENT_URI, reminderId);
        int rows = contentResolver.delete(reminderUri, null, null);
    }
    private static Long checkIfReminderExist(ContentResolver contentResolver, long eventId) {
        Long reminderId = null;

        String[] projection = new String[]{
                CalendarContract.Reminders._ID,
                CalendarContract.Reminders.METHOD,
                CalendarContract.Reminders.MINUTES
        };

        Cursor cursor = CalendarContract.Reminders.query(contentResolver, eventId, projection);

        while (cursor != null && cursor.moveToNext()) {
            reminderId = cursor.getLong(0);
        }

        cursor.close();

        return reminderId;
    }
    private String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                        .parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }
    public void saveHashMap(HashMap<String,String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }
    public HashMap<String,String> getHashMap(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<HashMap<String,String>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
