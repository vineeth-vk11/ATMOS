package com.example.atmos.ui.schedule;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmos.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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

            timePrimaryTextView = itemView.findViewById(R.id.schedule_event_time_primary_text_view);
            timeSecondaryTextView = itemView.findViewById(R.id.schedule_event_time_secondary_text_view);
            nameTextView = itemView.findViewById(R.id.schedule_event_name_text_view);
            locationTextView = itemView.findViewById(R.id.schedule_event_location_text_view);
            tagImageView = itemView.findViewById(R.id.schedule_event_tag_image_view);
            tagTextView = itemView.findViewById(R.id.schedule_event_tag_text_view);
            bookmarkedImageView = itemView.findViewById(R.id.event_bookmark_image_view);
        }

    }

    private ArrayList<ScheduleEvent> mEvents;

    ScheduleAdapter(ArrayList<ScheduleEvent> events) {
        mEvents = events;
    }

    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View contactView = inflater.inflate(R.layout.list_item_schedule, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ScheduleEvent event = mEvents.get(position);

        TextView timePrimaryTextView = holder.timePrimaryTextView;
        TextView timeSecondaryTextView = holder.timeSecondaryTextView;
        TextView nameTextView = holder.nameTextView;
        TextView locationTextView = holder.locationTextView;
        ImageView tagImageView = holder.tagImageView;
        TextView tagTextView = holder.tagTextView;
        final ImageView bookmarkedImageView = holder.bookmarkedImageView;

        String hours = "hh:mm";
        String aOp = "a";

        SimpleDateFormat sdfHours = new SimpleDateFormat(hours);
        SimpleDateFormat sdfAoP = new SimpleDateFormat(aOp);

        timePrimaryTextView.setText(sdfHours.format(event.getTime()));
        timeSecondaryTextView.setText(sdfAoP.format(event.getTime()));
        nameTextView.setText(event.getName());
        locationTextView.setText(event.getLocation());

        //TODO: Code proper recognition for tags
        if(event.isBookmarked()) {
            bookmarkedImageView.setImageResource(R.drawable.bookmark_filled);
        }

        //TODO: Set respective image sources
        if(event.getTag() == ScheduleEvent.SCHEDULE_EVENT_COMPETITION) {
            tagTextView.setText(mContext.getString(R.string.competition));
            tagImageView.setColorFilter(Color.RED);
        }
        else if(event.getTag() == ScheduleEvent.SCHEDULE_EVENT_WORKSHOP) {
            tagTextView.setText(mContext.getString(R.string.workshop));
            tagImageView.setColorFilter(Color.GREEN);
        }
        else {
            tagTextView.setText(mContext.getString(R.string.talk));
            tagImageView.setColorFilter(Color.YELLOW);
        }

        bookmarkedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add code to update boolean of event, update data into database, and notify recycler view of changes
                if(event.isBookmarked()) {
                    bookmarkedImageView.setImageResource(R.drawable.bookmark_outline);
                }
                else {
                    displayBookmarkConfirmationDialog();
                    bookmarkedImageView.setImageResource(R.drawable.bookmark_filled);
                }
            }
        });

    }

    private void displayBookmarkConfirmationDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View promptView = layoutInflater.inflate(R.layout.bookmark_confirmation_dialog, null);
        final AlertDialog alertD = new AlertDialog.Builder(mContext).create();

        alertD.setView(promptView);
        alertD.show();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                alertD.dismiss();
            }
        }, 2000);
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}
