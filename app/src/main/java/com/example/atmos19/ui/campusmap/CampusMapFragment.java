package com.example.atmos19.ui.campusmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmos19.R;

public class CampusMapFragment extends Fragment {

    private RecyclerView rv_map;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_campus_map, container, false);

        rv_map = root.findViewById(R.id.rvCampusMap);
        rv_map.setLayoutManager(new LinearLayoutManager(getActivity()));
        MapAdapter adapter = new MapAdapter(getActivity());
        rv_map.setAdapter(adapter);

        return root;
    }
}