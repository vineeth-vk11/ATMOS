package com.example.atmos.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmos.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<Event> upcomingEvents = new ArrayList<>();
        upcomingEvents.add(new Event("Random event", "Oct 18, 2019"));
        //TODO: Get announcements from API call and store them in this array


        ArrayList<Event> announcements = new ArrayList<>();
        announcements.add(new Event("Random announcement", "October 15, 2019"));
        //TODO: Get announcements from API call and store them in this array


        RecyclerView upcomingEventRecycler = rootView.findViewById(R.id.upcoming_events_recycler_view);
        RecyclerView announcementRecycler = rootView.findViewById(R.id.announcements_recycler_view);

        EventAdapter upcomingEventsAdapter = new EventAdapter(upcomingEvents);
        EventAdapter announcementAdapter = new EventAdapter(announcements);

        upcomingEventRecycler.setAdapter(upcomingEventsAdapter);
        announcementRecycler.setAdapter(announcementAdapter);

        upcomingEventRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        announcementRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        upcomingEventRecycler.setNestedScrollingEnabled(false);
        announcementRecycler.setNestedScrollingEnabled(false);

        return rootView;
    }
}