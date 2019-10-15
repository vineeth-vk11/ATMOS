package com.example.atmos.ui.contactus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmos.R;

import java.util.ArrayList;

public class ContactUsFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private TextView mOrganizingBodyTextView;
    private CardView mOrganizingBodyCardView;
    private TextView mTechSenateTextView;
    private CardView mTechSenateCardView;

    private ArrayList<ContactUs> mContactUses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_contact_us, container, false);

        mRecyclerView = rootview.findViewById(R.id.contact_us_recycler_view);

        mOrganizingBodyTextView = rootview.findViewById(R.id.organizing_body_text_view);
        mOrganizingBodyCardView = rootview.findViewById(R.id.organizing_body_card_view);
        mTechSenateTextView = rootview.findViewById(R.id.tech_senate_text_view);
        mTechSenateCardView = rootview.findViewById(R.id.tech_senate_header_card_view);

        mContactUses = new ArrayList<>();
        addDataToArray();

        ContactUsAdapter adapter = new ContactUsAdapter(getContext(), mContactUses);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setOrganizingBody();

        final LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager() ;

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findFirstVisibleItemPosition();
                int type = mContactUses.get(position).getPublicityType();
                switch(type) {
                    case 2:
                        setTechSenate();
                        break;
                    case 1:
                    default:
                        setOrganizingBody();
                }
            }
        });

        final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        mOrganizingBodyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smoothScroller.setTargetPosition(0);
                manager.startSmoothScroll(smoothScroller);
            }
        });

        mTechSenateCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smoothScroller.setTargetPosition(13);
                manager.startSmoothScroll(smoothScroller);
            }
        });

        return rootview;
    }

    private void addDataToArray() {
        //For organizing body members:
        mContactUses.add(new ContactUs("Nikhil Peruri","President", "+917981720729", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Vaishnavi Reddy","General Secratary", "+918919644180", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Shaaban Karim","Technical Secretary", "+917358325253", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Balpreet Singh","Department of Photography", "+919887422827", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Nikita Gohel ","Department of Technical Arts ", "+919920961821", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Amrutha Dude","Department of Sponsorship and Marketing", "+919182967515", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Harshal Mahajan ","Department of Security and Hospitality", "+918698772326", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Viswa Sai Teja","Department of Arts and Deco", "+917036369797", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Nikhil Bhasin","Department of Professional Events", "+918130559519", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Pranek Gupta","Department of Recreational Activities", "+919867622542", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Niral Khambhati ","Department of Visual Effects", "+919727896468", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("N S S TEJA","Controlz", "+917093155599", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));
        mContactUses.add(new ContactUs("Abhishek Bhardwaj","LSD", "+919205440192", ContactUs.PUBLICITY_TYPE_ORGANIZING_BODY));

        //For tech senate members:
        mContactUses.add(new ContactUs("Shreya Agarwal","Association of Chemical Engineers", "+918109090699", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Archan Desai","Axiom", "+919537388866", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Abhay Gupta","Synapsis", "+917389862404", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Karnica Parasher","Spectrum", "+918650914445", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Prabhmeet Singh","PHoEnix ", "+918802806955", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Vishesh Jain","Esports Club", "+918890783834", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Sruthi Tata ","Economics Association ", "+918125744473", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Priyanka Reddy","MEA", "+917093485874", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Aditya Gayatri Sastry Kaipa","Alchemy", "+919515378525", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Prakhar Sinha","Quiz Club", "+919099002830", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Aditya Agarwal","BITS Embryo", "+919079635401", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Prathmesh Mahalle","ARC", "+919422920299", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Uddeshya Chaudhary","Wall street club", "+919971629256", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Vithun Athreya","E-Cell", "+919819822977", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Yashas","Ad Astra", "+919663949463", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Syed Abdullah Naeem ","Panacea", "+917095819827", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Toshit Jain","CSA", "+917997000293", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Ujjwal Raizada","Crux", "+917897745889", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Tota Gautham Reddy","SAE", "+919100562917", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
        mContactUses.add(new ContactUs("Kshitij Choughule ","Ieee", "+919082038271", ContactUs.PUBLICITY_TYPE_TECH_SENATE));
    }

    private void setOrganizingBody() {
        mOrganizingBodyTextView.setTextColor(getContext().getColor(R.color.colorPrimary));
        mOrganizingBodyCardView.setCardBackgroundColor(getContext().getColor(R.color.secondary));

        mTechSenateTextView.setTextColor(getContext().getColor(R.color.textSecondary));
        mTechSenateCardView.setCardBackgroundColor(getContext().getColor(R.color.background));
    }

    private void setTechSenate() {
        mOrganizingBodyTextView.setTextColor(getContext().getColor(R.color.textSecondary));
        mOrganizingBodyCardView.setCardBackgroundColor(getContext().getColor(R.color.background));

        mTechSenateTextView.setTextColor(getContext().getColor(R.color.colorPrimary));
        mTechSenateCardView.setCardBackgroundColor(getContext().getColor(R.color.secondary));
    }

}