package com.example.atmos.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

        ArrayList<Announcement> announcements = new ArrayList<>();
        announcements.add(new Announcement("Random announcement 1", "October 16, 2019"));
        announcements.add(new Announcement("Random announcement 2", "October 15, 2019"));
        announcements.add(new Announcement("Random announcement 1", "October 16, 2019"));
        announcements.add(new Announcement("Random announcement 2", "October 15, 2019"));
        announcements.add(new Announcement("Random announcement 1", "October 16, 2019"));
        announcements.add(new Announcement("Random announcement 2", "October 15, 2019"));
        announcements.add(new Announcement("Random announcement 1", "October 16, 2019"));
        announcements.add(new Announcement("Random announcement 2", "October 15, 2019"));
        announcements.add(new Announcement("Random announcement 1", "October 16, 2019"));
        announcements.add(new Announcement("Random announcement 2", "October 15, 2019"));
        announcements.add(new Announcement("Random announcement 1", "October 16, 2019"));
        announcements.add(new Announcement("Random announcement 2", "October 15, 2019"));
        announcements.add(new Announcement("Random announcement 1", "October 16, 2019"));
        announcements.add(new Announcement("Random announcement 2", "October 15, 2019"));
        announcements.add(new Announcement("Random announcement 1", "October 16, 2019"));
        announcements.add(new Announcement("Random announcement 2", "October 15, 2019"));
        announcements.add(new Announcement("Random announcement 1", "October 16, 2019"));
        announcements.add(new Announcement("Random announcement 2", "October 15, 2019"));

        //TODO: Get announcements from API call and store them in this array

        RecyclerView announcementRecycler = rootView.findViewById(R.id.announcements_recycler_view);
        AnnouncementAdapter announcementAdapter = new AnnouncementAdapter(announcements);
        announcementRecycler.setAdapter(announcementAdapter);
        announcementRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        announcementRecycler.setNestedScrollingEnabled(false);
        return rootView;
    }
}