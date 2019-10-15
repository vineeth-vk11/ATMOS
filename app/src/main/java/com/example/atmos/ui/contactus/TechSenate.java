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
public class TechSenate extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ContactUs> ContactUs;
    public TechSenate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_tech_senate, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ContactUs = new ArrayList<>();
        ContactUs.add(new ContactUs("Shreya Agarwal","Association of Chemical Engineers", "+918109090699"));
        ContactUs.add(new ContactUs("Archan Desai","Axiom", "+919537388866"));
        ContactUs.add(new ContactUs("Abhay Gupta","Synapsis", "+917389862404"));
        ContactUs.add(new ContactUs("Karnica Parasher","Spectrum", "+918650914445"));
        ContactUs.add(new ContactUs("Prabhmeet Singh","PHoEnix ", "+918802806955"));
        ContactUs.add(new ContactUs("Vishesh Jain","Esports Club", "+918890783834"));
        ContactUs.add(new ContactUs("Sruthi Tata ","Economics Association ", "+918125744473"));
        ContactUs.add(new ContactUs("Priyanka Reddy","MEA", "+917093485874"));
        ContactUs.add(new ContactUs("Aditya Gayatri Sastry Kaipa","Alchemy", "+919515378525"));
        ContactUs.add(new ContactUs("Prakhar Sinha","Quiz Club", "+919099002830"));
        ContactUs.add(new ContactUs("Aditya Agarwal","BITS Embryo", "+919079635401"));
        ContactUs.add(new ContactUs("Prathmesh Mahalle","ARC", "+919422920299"));
        ContactUs.add(new ContactUs("Uddeshya Chaudhary","Wall street club", "+919971629256"));
        ContactUs.add(new ContactUs("Vithun Athreya","E-Cell", "+919819822977"));
        ContactUs.add(new ContactUs("Yashas","Ad Astra", "+919663949463"));
        ContactUs.add(new ContactUs("Syed Abdullah Naeem ","Panacea", "+917095819827"));
        ContactUs.add(new ContactUs("Toshit Jain","CSA", "+917997000293"));
        ContactUs.add(new ContactUs("Ujjwal Raizada","Crux", "+917897745889"));
        ContactUs.add(new ContactUs("Tota Gautham Reddy","SAE", "+919100562917"));
        ContactUs.add(new ContactUs("Kshitij Choughule ","Ieee", "+919082038271"));


        myAdapter = new ContactUsAdapter(this.getActivity(),ContactUs);
        recyclerView.setAdapter(myAdapter);

        return root;
    }

}
