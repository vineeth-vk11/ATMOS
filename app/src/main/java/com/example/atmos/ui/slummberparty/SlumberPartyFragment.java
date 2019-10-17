package com.example.atmos.ui.slummberparty;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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
import com.example.atmos.ui.schedule.ScheduleEvent;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class SlumberPartyFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{

    private TextView mDayOneTextView;
    private CardView mDayOneCardView;
    private TextView mDayTwoTextView;
    private CardView mDayTwoCardView;
    private TextView mDayThreeTextView;
    private CardView mDayThrreeCardView;
    private TextView mDayFourTextView;
    private CardView mDayFourCardView;

    private ArrayList<PartyEvent> mPartyList;

    private RecyclerView mPartyRecycler;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton filterFab;
    private RapidFloatingActionHelper filterFabHelpler;

    private PartyAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slumber_party, container, false);

        mDayOneTextView = root.findViewById(R.id.header_day_one_text_view_party);
        mDayTwoTextView = root.findViewById(R.id.header_day_two_text_view_party);
        mDayThreeTextView = root.findViewById(R.id.header_day_three_text_view_party);
        mDayFourTextView = root.findViewById(R.id.header_day_four_text_view_party);
        mDayOneCardView = root.findViewById(R.id.day_one_header_card_view_party);
        mDayTwoCardView = root.findViewById(R.id.day_two_header_card_view_party);
        mDayThrreeCardView = root.findViewById(R.id.day_three_header_card_view_party);
        mDayFourCardView = root.findViewById(R.id.day_four_header_card_view_party);

        mPartyRecycler = root.findViewById(R.id.party_recycler_view);

        rfaLayout = root.findViewById(R.id.party_filter_kayout);
        filterFab = root.findViewById(R.id.party_filter_fab);

        mPartyList = new ArrayList<>();
        addDateToPartyList();

        displayFabOptions();
        setAdapter(FILTER_NONE);

        return root;
    }

    private static final int FILTER_ENGLISH = 0;
    private static final int FILTER_HINDI = 1;
    private static final int FILTER_TELUGU = 2;
    private static final int FILTER_BENGALI = 3;
    private static final int FILTER_NONE = 4;

    private void setAdapter(int filterBy) {

        mAdapter = new PartyAdapter(filterDetailList(mPartyList, filterBy));
        mPartyRecycler.setAdapter(mAdapter);
        mPartyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        setDayOne();

        final LinearLayoutManager manager = (LinearLayoutManager) mPartyRecycler.getLayoutManager();

        mPartyRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findFirstVisibleItemPosition()+1;

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
                        case "21":
                            setDayFour();
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
                smoothScroller.setTargetPosition(0);
                manager.startSmoothScroll(smoothScroller);
            }
        });

        mDayTwoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Set target scroll position for day 2
                smoothScroller.setTargetPosition(24);
                manager.startSmoothScroll(smoothScroller);
            }
        });

        mDayThrreeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Set target scroll position for day 3
                smoothScroller.setTargetPosition(44);
                manager.startSmoothScroll(smoothScroller);
            }
        });

    }


    private void displayFabOptions() {
        RapidFloatingActionContentLabelList fabContent = new RapidFloatingActionContentLabelList(getContext());
        fabContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> options = new ArrayList<>();
        options.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.english))
                .setWrapper(FILTER_ENGLISH)
        );
        options.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.hindi))
                .setWrapper(FILTER_HINDI)
        );
        options.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.telugu))
                .setWrapper(FILTER_TELUGU)
        );
        options.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.bengali))
                .setWrapper(FILTER_BENGALI)
        );
        options.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.none))
                .setWrapper(FILTER_NONE)
        );
        fabContent.setItems(options);
        filterFabHelpler = new RapidFloatingActionHelper(getContext(), rfaLayout, filterFab, fabContent).build();
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (position) {
            case FILTER_HINDI:
                setAdapter(FILTER_HINDI);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
                break;
            case FILTER_TELUGU:
                setAdapter(FILTER_TELUGU);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
                break;
            case FILTER_BENGALI:
                setAdapter(FILTER_BENGALI);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
                break;
            case FILTER_ENGLISH:
                setAdapter(FILTER_ENGLISH);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
                break;
            case FILTER_NONE:
            default:
                setAdapter(FILTER_NONE);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
        }
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        //do nothing since icons have been disabled
    }

    private String getLanguageFromInt(int lang) {
        String filterBy = getString(R.string.english);
        if(lang==FILTER_ENGLISH)
            filterBy = getString(R.string.english);
        else if(lang==FILTER_HINDI)
            filterBy = getString(R.string.hindi);
        else if(lang==FILTER_TELUGU)
            filterBy = getString(R.string.telugu);
        else if(lang==FILTER_BENGALI)
            filterBy = getString(R.string.bengali);
        return filterBy;
    }

    private ArrayList<PartyEvent> filterDetailList(ArrayList<PartyEvent> eventDetailsList, int filter) {
        if (filter == FILTER_NONE)
            return mPartyList;
        else {
            ArrayList<PartyEvent> filteredDetailList = new ArrayList<>();
            for (PartyEvent event : eventDetailsList) {
                String language = event.getLanguage();
                if (language.equalsIgnoreCase(getLanguageFromInt(filter))) {
                    filteredDetailList.add(event);
                }
            }
            return filteredDetailList;
        }
    }



    private void addDateToPartyList() {
        //TODO: Add data over here
        mPartyList.add(new PartyEvent("2019-10-18-22-00", "The Exorcist(1973)", "F102", "2hrs 15min", "English"));
        mPartyList.add(new PartyEvent("2019-10-19-00-15", "1408(2007)", "F102", "1hrs 52mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-19-2-10", "Mama(2013)", "F102", "1hrs 40mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-19-3-50", "Shutter(2004)", "F102", "1hrs 37mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-19-22-00", "Halloween(2018)", "F102", "1hrs 44mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-19-23-55", "Identity(2003)", "F102", "1hrs 31mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-20-1-35", "Ju-On: The Grudge(2002)", "F102", "1hrs 40mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-20-3-30", "The Strangers(2008)", "F102", "1hrs 31mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-20-22-00", "Hell Fest(2018)", "F102", "1hrs 30mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-20-23-40", "Us(2019)", "F102", "2hrs 1mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-21-1-50", "It Follows(2014)", "F102", "1hrs 47mins", "English"));

        mPartyList.add(new PartyEvent("2019-10-18-22-00", "Thief(1981)", "F103", "2hrs 6mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-19-00-20", "Autumn Tale(1998)", "F103", "2hrs", "English"));
        mPartyList.add(new PartyEvent("2019-10-19-2-30", "Harakiri", "F103", "2hrs 14mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-19-22-00", "Where is the Friend's Home?(1987)", "F103", "1hrs 27mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-19-23-40", "35 Shots of Rum(2008)", "F103", "1hrs 45mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-20-1-30", "All That Heaven Allows(1955)", "F103", "1hrs 29mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-20-3-10", "Rosetta(1999)", "F103", "1hrs 35mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-20-22-00", "Blow Out(1981)", "F103", "1hrs 53mins", "English"));
        mPartyList.add(new PartyEvent("2019-10-21-00-01", "Mahanagar(1963)", "F103", "2hrs 16mins", "Bengali"));

        mPartyList.add(new PartyEvent("2019-10-18-22-00", "Jersey(2019)", "F105", "2hr 37min", "Telugu"));
        mPartyList.add(new PartyEvent("2019-10-19-00-40", "Kabir Singh(2019)", "F105", "2hr 52min", "Hindi"));
        mPartyList.add(new PartyEvent("2019-10-19-3-35", "Gang Leader(2019)", "F105", "2hr 35min", "Telugu"));
        mPartyList.add(new PartyEvent("2019-10-19-22-00", "Agent Sai Srinivas Athreya(2019)", "F105", "1hr 50min", "Telugu"));
        mPartyList.add(new PartyEvent("2019-10-20-00-35", "Game Over(2019)", "F105", "1hr 30min", "Telugu"));
        mPartyList.add(new PartyEvent("2019-10-20-2-25", "Go Goa Gone(2013)", "F105", "None", "Hindi"));
        mPartyList.add(new PartyEvent("2019-10-20-22-00", "Brochevarevarura(2019)", "F105", "2hr 15min", "Telugu"));
        mPartyList.add(new PartyEvent("2019-10-21-00-30", "Super Deluxe(2019)", "F105", "2hr 56min", "Hindi"));

        mPartyList.add(new PartyEvent("2019-10-18-22-00", "Jaane Tu Ya Jaane Na(2008)", "F104", "2hr 35min", "Hindi"));
        mPartyList.add(new PartyEvent("2019-10-19-00-40", "Angry Birds 2(2019)", "F104", "1hr 37min", "Hindi"));
        mPartyList.add(new PartyEvent("2019-10-19-2-20", "Vicky Donor(2012)", "F104", "2hr 5min", "Hindi"));
        mPartyList.add(new PartyEvent("2019-10-19-22-00", "Delhi Belly(2011)", "F104", "1hr 43min", "Hindi"));
        mPartyList.add(new PartyEvent("2019-10-19-23-50", "Batla House(2019)", "F104", "2r 26min", "Hindi"));
        mPartyList.add(new PartyEvent("2019-10-20-2-20", "Top Gun(1996)", "F104", "1hr 50min", "Hindi"));
        mPartyList.add(new PartyEvent("2019-10-20-22-00", "Gangs of Wasseypur Part 1(2012)", "F104", "2hr 40min", "Hindi"));
        mPartyList.add(new PartyEvent("2019-10-21-00-40", "Gangs of Wasseypur Part 2(2012)", "F104", "2hr 40min", "Hindi"));


    }

    private void setDayOne() {
        mDayOneTextView.setTextColor(getContext().getColor(R.color.colorPrimary));
        mDayTwoTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayThreeTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayFourTextView.setTextColor(getContext().getColor(R.color.secondary));

        mDayOneCardView.setCardBackgroundColor(getContext().getColor(R.color.secondary));
        mDayTwoCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayThrreeCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayFourCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
    }

    private void setDayTwo() {
        mDayOneTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayTwoTextView.setTextColor(getContext().getColor(R.color.colorPrimary));
        mDayThreeTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayFourTextView.setTextColor(getContext().getColor(R.color.secondary));

        mDayOneCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayTwoCardView.setCardBackgroundColor(getContext().getColor(R.color.secondary));
        mDayThrreeCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayFourCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
    }

    private void setDayThree() {
        mDayOneTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayTwoTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayThreeTextView.setTextColor(getContext().getColor(R.color.colorPrimary));
        mDayFourTextView.setTextColor(getContext().getColor(R.color.secondary));

        mDayOneCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayTwoCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayThrreeCardView.setCardBackgroundColor(getContext().getColor(R.color.secondary));
        mDayFourCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
    }

    private void setDayFour() {
        mDayOneTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayTwoTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayThreeTextView.setTextColor(getContext().getColor(R.color.secondary));
        mDayFourTextView.setTextColor(getContext().getColor(R.color.colorPrimary));

        mDayOneCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayTwoCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayThrreeCardView.setCardBackgroundColor(getContext().getColor(R.color.colorPrimary));
        mDayFourCardView.setCardBackgroundColor(getContext().getColor(R.color.secondary));
    }

}