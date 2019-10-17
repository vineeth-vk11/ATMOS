package com.example.atmos.ui.slummberparty;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmos.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;

public class SlumberPartyFragment extends Fragment {

    private TextView mDayOneTextView;
    private CardView mDayOneCardView;
    private TextView mDayTwoTextView;
    private CardView mDayTwoCardView;
    private TextView mDayThreeTextView;
    private CardView mDayThrreeCardView;

    private ArrayList<PartyEvent> mPartyList;

    private RecyclerView mPartyRecycler;

    private PartyAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slumber_party, container, false);

        mDayOneTextView = root.findViewById(R.id.header_day_one_text_view_party);
        mDayTwoTextView = root.findViewById(R.id.header_day_two_text_view_party);
        mDayThreeTextView = root.findViewById(R.id.header_day_three_text_view_party);
        mDayOneCardView = root.findViewById(R.id.day_one_header_card_view_party);
        mDayTwoCardView = root.findViewById(R.id.day_two_header_card_view_party);
        mDayThrreeCardView = root.findViewById(R.id.day_three_header_card_view_party);

        mPartyRecycler = root.findViewById(R.id.party_recycler_view);

        mPartyList = new ArrayList<>();
        addDateToPartyList();

        mAdapter = new PartyAdapter(mPartyList);
        mPartyRecycler.setAdapter(mAdapter);
        mPartyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        setDayOne();

        final LinearLayoutManager manager = (LinearLayoutManager) mPartyRecycler.getLayoutManager();

        mPartyRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findFirstVisibleItemPosition();
                if(mPartyList!=null && !mPartyList.isEmpty() && position!=0)
                {
                    String day = new SimpleDateFormat("dd").format(mPartyList.get(position).getPartyDate());
                    switch((day)) {
                        case "19":
                            setDayTwo();
                            break;
                        case "20":
                            setDayThree();
                            break;
                        case "18":
                        default:
                            setDayOne();
                    }
                }

            }
        });

        final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };


        mDayOneCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Set target scroll position for day 1
                setDayOne();
                smoothScroller.setTargetPosition(0);
                manager.startSmoothScroll(smoothScroller);
            }
        });

        mDayTwoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Set target scroll position for day 2
                //setDayTwo();
                smoothScroller.setTargetPosition(24);
                manager.startSmoothScroll(smoothScroller);
            }
        });

        mDayThrreeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Set target scroll position for day 3
                //setDayThree();
                smoothScroller.setTargetPosition(44);
                manager.startSmoothScroll(smoothScroller);
            }
        });

        return root;
    }

    private void addDateToPartyList() {
        //TODO: Add data over here
        mPartyList.add(new PartyEvent(new Date(), "The Exorcist", "F102", "2hrs 15min", "English"));
        mPartyList.add(new PartyEvent(new Date(), "1408(2007)", "F102", "2hrs 5mins", "English"));
        mPartyList.add(new PartyEvent(new Date(), "Mama(2013)", "F102", "2hrs 5mins", "English"));
        mPartyList.add(new PartyEvent(new Date(), "Shutter(2004)", "F102", "2hrs 5mins", "English"));
        mPartyList.add(new PartyEvent(new Date(), "Halloween(2018)", "F102", "2hrs 5mins", "English"));
        mPartyList.add(new PartyEvent(new Date(), "Identity(2003)", "F102", "2hrs 5mins", "English"));
        mPartyList.add(new PartyEvent(new Date(), "Ju-On: The Grudge(2002)", "F102", "2hrs 5mins", "English"));
        mPartyList.add(new PartyEvent(new Date(), "The Strangers(2008)", "F102", "2hrs 5mins", "English"));
        mPartyList.add(new PartyEvent(new Date(), "Hell Fest(2018)", "F102", "2hrs 5mins", "English"));
        mPartyList.add(new PartyEvent(new Date(), "Us(2019)", "F102", "2hrs 5mins", "English"));
        mPartyList.add(new PartyEvent(new Date(), "It Follows(2014)", "F102", "2hrs 5mins", "English"));

    }

    private void setDayOne() {
        mDayOneTextView.setTextColor(getContext().getColor(R.color.colorPrimary));
        mDayTwoTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayThreeTextView.setTextColor(getContext().getColor(R.color.secondary));

        mDayOneCardView.setCardBackgroundColor(getContext().getColor(R.color.secondary));
        mDayTwoCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayThrreeCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
    }

    private void setDayTwo() {
        mDayOneTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayTwoTextView.setTextColor(getContext().getColor(R.color.colorPrimary));
        mDayThreeTextView.setTextColor(getContext().getColor(R.color.secondary));

        mDayOneCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayTwoCardView.setCardBackgroundColor(getContext().getColor(R.color.secondary));
        mDayThrreeCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
    }

    private void setDayThree() {
        mDayOneTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayTwoTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayThreeTextView.setTextColor(getContext().getColor(R.color.colorPrimary));

        mDayOneCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayTwoCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayThrreeCardView.setCardBackgroundColor(getContext().getColor(R.color.secondary));
    }

}