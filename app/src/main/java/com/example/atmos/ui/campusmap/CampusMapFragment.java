package com.example.atmos.ui.campusmap;

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

public class CampusMapFragment extends Fragment {

    private RecyclerView rv_map;
    private CampusMapViewModel campusMapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        campusMapViewModel =
                ViewModelProviders.of(this).get(CampusMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_campus_map, container, false);

        rv_map = root.findViewById(R.id.rvCampusMap);
        rv_map.setLayoutManager(new LinearLayoutManager(getActivity()));
        MapAdapter adapter = new MapAdapter(getActivity());
        rv_map.setAdapter(adapter);

        return root;
    }
}