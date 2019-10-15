package com.example.atmos.ui.contactus;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atmos.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrganisingBody extends Fragment {

    private boolean locFlag;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ContactUs> contactUs;
    public OrganisingBody() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_organising_body, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        contactUs = new ArrayList<>();
        contactUs.add(new ContactUs("Nikhil Peruri","President", "+917981720729"));
        contactUs.add(new ContactUs("Vaishnavi Reddy","General Secratary", "+918919644180"));
        contactUs.add(new ContactUs("Shaaban Karim","Technical Secretary", "+917358325253"));
        contactUs.add(new ContactUs("Balpreet Singh","Department of Photography", "+919887422827"));
        contactUs.add(new ContactUs("Nikita Gohel ","Department of Technical Arts ", "+919920961821"));
        contactUs.add(new ContactUs("Amrutha Dude","Department of Sponsorship and Marketing", "+919182967515"));
        contactUs.add(new ContactUs("Harshal Mahajan ","Department of Security and Hospitality", "+918698772326"));
        contactUs.add(new ContactUs("Viswa Sai Teja","Department of Arts and Deco", "+917036369797"));
        contactUs.add(new ContactUs("Nikhil Bhasin","Department of Professional Events", "+918130559519"));
        contactUs.add(new ContactUs("Pranek Gupta","Department of Recreational Activities", "+919867622542"));
        contactUs.add(new ContactUs("Niral Khambhati ","Department of Visual Effects", "+919727896468"));
        contactUs.add(new ContactUs("N S S TEJA","Controlz", "+917093155599"));
        contactUs.add(new ContactUs("Abhishek Bhardwaj","LSD", "+919205440192"));

        myAdapter = new ContactUsAdapter(this.getActivity(),contactUs);
        recyclerView.setAdapter(myAdapter);

        return root;

    }
    public void setLocFlag(boolean locFlag) {
        this.locFlag = locFlag;
    }
}
