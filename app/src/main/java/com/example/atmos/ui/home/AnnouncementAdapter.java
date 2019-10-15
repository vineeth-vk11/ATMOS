package com.example.atmos.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmos.R;

import java.util.ArrayList;

class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    private ArrayList<Announcement> mAnnouncements;

    AnnouncementAdapter(ArrayList<Announcement> announcements) {
        mAnnouncements = announcements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View eventView = inflater.inflate(R.layout.list_item_announcements, parent, false);

        ViewHolder viewHolder = new ViewHolder(eventView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Announcement curEvent = mAnnouncements.get(position);
        TextView announcementName = holder.announcementName;
        TextView announcementDateAndTime = holder.announcementDateAndTime;
        announcementName.setText(curEvent.getAnnouncementName());
        announcementDateAndTime.setText(curEvent.getAnnouncementDate());
    }

    @Override
    public int getItemCount() {
        return mAnnouncements.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView announcementName;
        TextView announcementDateAndTime;

        public ViewHolder(View itemView) {
            super(itemView);
            announcementName = itemView.findViewById(R.id.event_primary_header_text_view);
            announcementDateAndTime = itemView.findViewById(R.id.event_secondary_header_text_view);
        }

    }

}
