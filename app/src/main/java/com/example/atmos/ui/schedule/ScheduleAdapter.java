package com.example.atmos.ui.schedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmos.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView timePrimaryTextView;
        public TextView timeSecondaryTextView;
        public TextView nameTextView;
        public TextView locationTextView;
        public ImageView tagImageView;
        public TextView tagTextView;
        public ImageView bookmarkedImageView;

        ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            timePrimaryTextView = itemView.findViewById(R.id.party_time_primary_text_view);
            timeSecondaryTextView = itemView.findViewById(R.id.party_time_secondary_text_view);
            nameTextView = itemView.findViewById(R.id.party_name_text_view);
            locationTextView = itemView.findViewById(R.id.party_location_text_view);
            tagImageView = itemView.findViewById(R.id.schedule_event_tag_image_view);
            tagTextView = itemView.findViewById(R.id.party_duration_text_view);
            bookmarkedImageView = itemView.findViewById(R.id.event_bookmark_image_view);
        }

    }

    private ArrayList<ScheduleEvent> mEvents;
    public ArrayList<String> bookMarked = new ArrayList<>();
    ScheduleAdapter(ArrayList<ScheduleEvent> events) {
        mEvents = events;
    }
    EventRemainder eventRemainder;
    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        eventRemainder = new EventRemainder(mContext);
        View contactView = inflater.inflate(R.layout.list_item_schedule, parent, false);
        bookMarked = getArrayList("bookMarked");
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        final ScheduleEvent event = mEvents.get(position);

        TextView timePrimaryTextView = holder.timePrimaryTextView;
        TextView timeSecondaryTextView = holder.timeSecondaryTextView;
        TextView nameTextView = holder.nameTextView;
        TextView locationTextView = holder.locationTextView;
        ImageView tagImageView = holder.tagImageView;
        TextView tagTextView = holder.tagTextView;
        final ImageView bookmarkedImageView = holder.bookmarkedImageView;

        String time = event.getStartTime();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
            Date dateObj = sdf.parse(time);
            timePrimaryTextView.setText(new SimpleDateFormat("hh:mm").format(dateObj));
            timeSecondaryTextView.setText(new SimpleDateFormat("aa").format(dateObj));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //timePrimaryTextView.setText((event.getStartTime()));
        Log.d("eventStartTime",event.getStartTime());
        //timeSecondaryTextView.setText(sdfAoP.format(event.getTime()));
       // timeSecondaryTextView.setText("A.M");

        nameTextView.setText(event.getName());
        locationTextView.setText(event.getVenue());

        if(bookMarked!=null&&!bookMarked.isEmpty()
                &&bookMarked.contains(event.getName()))
        {

                bookmarkedImageView.setImageResource(R.drawable.bookmark_filled);

        }
        //TODO: Set respective image sources
        Log.d("Type",event.getType());
        if(event.getType().equals("Competition"))
        {
            tagTextView.setText("Competition");
            tagImageView.setColorFilter(Color.RED);
        }
        else if(event.getType().equals("Workshop")) {
            tagTextView.setText("Workshop");
            tagImageView.setColorFilter(Color.GREEN);
        }
        else {
            tagTextView.setText(mContext.getString(R.string.Talk));
            tagImageView.setColorFilter(Color.YELLOW);
        }
        bookmarkedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add code to update boolean and save data into database

                if(bookMarked!=null&&!bookMarked.isEmpty()&&bookMarked.contains(event.getName())) {
                    bookMarked.remove(event.getName());
                    saveArrayList(bookMarked,"bookMarked");
                    bookMarked.add(event.getName());
                    eventRemainder.removeRemainder(event);
                    bookmarkedImageView.setImageResource(R.drawable.bookmark_outline);
                }
                else {
                    if(bookMarked==null||bookMarked.isEmpty()) {
                        bookMarked = new ArrayList<>();
                    }
                    bookMarked.add(event.getName());
                    eventRemainder.createRemainder(event);
                    saveArrayList(bookMarked,"bookMarked");
                    //saveHashMap(hashMap,"position");
                    bookmarkedImageView.setImageResource(R.drawable.bookmark_filled);
                }
            }
        });
    }
    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }
    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}
