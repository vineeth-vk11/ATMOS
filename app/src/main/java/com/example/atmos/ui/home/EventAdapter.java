package com.example.atmos.ui.home;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmos.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private ArrayList<Event> mEvents;

    EventAdapter(ArrayList<Event> events) {
        mEvents = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View eventView = inflater.inflate(R.layout.list_item_upcomming_events, parent, false);

        ViewHolder viewHolder = new ViewHolder(eventView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event curEvent = mEvents.get(position);
        TextView eventName = holder.eventName;
        TextView eventDateAndTime = holder.eventDateAndTime;
        eventName.setText(curEvent.getEventName());
        eventDateAndTime.setText(curEvent.getEventDateAndTime());
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventName;
        TextView eventDateAndTime;

        public ViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_primary_header_text_view);
            eventDateAndTime = itemView.findViewById(R.id.event_secondary_header_text_view);
        }

    }

}
