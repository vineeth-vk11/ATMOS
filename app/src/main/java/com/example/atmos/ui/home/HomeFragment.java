package com.example.atmos.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmos.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView mAnnouncementRecycler;
    private LinearLayout mNoAnnouncementTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mAnnouncementRecycler = rootView.findViewById(R.id.announcements_recycler_view);
        mNoAnnouncementTextView = rootView.findViewById(R.id.announcements_empty_view);

        ArrayList<Announcement> announcements = new ArrayList<>();
        //TODO: Get announcements from API call and store them in this array

        if(announcements.isEmpty())
            setEmptyView();
        else {
            AnnouncementAdapter announcementAdapter = new AnnouncementAdapter(announcements);
            mAnnouncementRecycler.setAdapter(announcementAdapter);
            mAnnouncementRecycler.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mAnnouncementRecycler.setNestedScrollingEnabled(false);
            setRecyclerView();
        }


        return rootView;
    }

    private void setRecyclerView() {
        mAnnouncementRecycler.setVisibility(View.VISIBLE);
        mNoAnnouncementTextView.setVisibility(View.INVISIBLE);
    }

    private void setEmptyView() {
        mAnnouncementRecycler.setVisibility(View.INVISIBLE);
        mNoAnnouncementTextView.setVisibility(View.VISIBLE);
    }
}