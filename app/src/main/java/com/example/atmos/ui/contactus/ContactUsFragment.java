package com.example.atmos.ui.contactus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class ContactUsFragment extends Fragment {
    private int current_frag;
    private TechSenate techSenate;
    private OrganisingBody organisingBody;

    public ContactUsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        organisingBody = new OrganisingBody();
        techSenate = new TechSenate();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_contact_us, container, false);
        current_frag = 0;
        getChildFragmentManager().beginTransaction().replace(R.id.nestedFragHolder, organisingBody).commitAllowingStateLoss();
        rootview.findViewById(R.id.organisingBody).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_frag==0)
                    return;
                current_frag = 0;
                rootview.findViewById(R.id.organisingBody).setBackgroundResource(R.drawable.shapecontactus);
                rootview.findViewById(R.id.tvTechSenate).setBackgroundResource(R.drawable.shapecontactus1);
                ((TextView)rootview.findViewById(R.id.tvTechSenate)).setTextColor(getResources().getColor(R.color.secondary));
                ((TextView)rootview.findViewById(R.id.organisingBody)).setTextColor(getResources().getColor(R.color.background));
                getChildFragmentManager().beginTransaction().replace(R.id.nestedFragHolder, organisingBody).commitAllowingStateLoss();
            }
        });
        rootview.findViewById(R.id.tvTechSenate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_frag==1)
                    return;
                current_frag = 1;
                rootview.findViewById(R.id.organisingBody).setBackgroundResource(R.drawable.shapecontactus1);
                rootview.findViewById(R.id.tvTechSenate).setBackgroundResource(R.drawable.shapecontactus);
                ((TextView)rootview.findViewById(R.id.tvTechSenate)).setTextColor(getResources().getColor(R.color.background));
                ((TextView)rootview.findViewById(R.id.organisingBody)).setTextColor(getResources().getColor(R.color.secondary));
                getChildFragmentManager().beginTransaction().replace(R.id.nestedFragHolder, techSenate).commitAllowingStateLoss();
            }
        });

        return rootview;
    }

    public void updateLocFlag(boolean flag){
        organisingBody.setLocFlag(flag);
    }

}