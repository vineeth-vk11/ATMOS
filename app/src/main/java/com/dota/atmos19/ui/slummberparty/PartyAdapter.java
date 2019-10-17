package com.dota.atmos19.ui.slummberparty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dota.atmos19.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView timePrimaryTextView;
        TextView timeSecondaryTextView;
        TextView nameTextView;
        TextView locationTextView;
        TextView durationTextView;
        TextView language;
        TextView date;
        ViewHolder(View itemView) {
            super(itemView);
            timePrimaryTextView = itemView.findViewById(R.id.party_time_primary_text_view);
            timeSecondaryTextView = itemView.findViewById(R.id.party_time_secondary_text_view);
            nameTextView = itemView.findViewById(R.id.party_name_text_view);
            locationTextView = itemView.findViewById(R.id.party_location_text_view);
            durationTextView = itemView.findViewById(R.id.party_duration_text_view);
            language = itemView.findViewById(R.id.language);
            date = itemView.findViewById(R.id.date);
        }

    }

    private ArrayList<PartyEvent> mPartyEvents;

    public PartyAdapter(ArrayList<PartyEvent> events) {
        Collections.sort(events);
        mPartyEvents = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View partyView = inflater.inflate(R.layout.list_item_slumber_party, parent, false);
        ViewHolder viewHolder = new ViewHolder(partyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PartyEvent event = mPartyEvents.get(position);

        TextView timePrimaryTextView = holder.timePrimaryTextView;
        TextView timeSecondaryTextView = holder.timeSecondaryTextView;
        TextView nameTextView = holder.nameTextView;
        TextView locationTextView = holder.locationTextView;
        TextView durationTextView = holder.durationTextView;
        TextView language = holder.language;
        TextView date = holder.date;
        Date time = event.getPartyDate();
        timePrimaryTextView.setText(new SimpleDateFormat("hh:mm").format(time));
        timeSecondaryTextView.setText(new SimpleDateFormat("aa").format(time));
        date.setText(new SimpleDateFormat("dd/MM").format(time));
        nameTextView.setText(event.getPartyName());
        locationTextView.setText(event.getPartyLocation());
        durationTextView.setText(event.getPartyDuration());
        language.setText(event.getLanguage());
    }

    @Override
    public int getItemCount() {
        return mPartyEvents.size();
    }
}
