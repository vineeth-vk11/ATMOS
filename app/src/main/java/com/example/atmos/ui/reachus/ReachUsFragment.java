package com.example.atmos.ui.reachus;

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

import com.example.atmos.R;

public class ReachUsFragment extends Fragment {

    private ReachUsViewModel reachUsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reachUsViewModel =
                ViewModelProviders.of(this).get(ReachUsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reach_us, container, false);

        return root;
    }
}