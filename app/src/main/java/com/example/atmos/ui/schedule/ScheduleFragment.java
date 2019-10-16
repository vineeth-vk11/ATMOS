package com.example.atmos.ui.schedule;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.atmos.api.ApiClient;
import com.example.atmos.api.EventsInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ScheduleFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    private ScheduleViewModel scheduleViewModel;
    private Realm realm;
    private TextView mDayOneTextView;
    private CardView mDayOneCardView;
    private TextView mDayTwoTextView;
    private CardView mDayTwoCardView;
    private TextView mDayThreeTextView;
    private CardView mDayThrreeCardView;
    private ProgressBar progressBar;
    private ArrayList<ScheduleEvent> eventDetailsList;
    private ArrayList<ScheduleEvent> realmList;
    private Context context;
    private boolean isnetwork = false;
    private RecyclerView recyclerView;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton filterFab;
    private RapidFloatingActionHelper filterFabHelpler;

    private ScheduleAdapter mAdapter;

    private boolean filterSet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        scheduleViewModel =
                ViewModelProviders.of(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        filterSet = false;

        recyclerView = root.findViewById(R.id.schedule_recycler_view);
        mDayOneTextView = root.findViewById(R.id.header_day_one_text_view);
        mDayTwoTextView = root.findViewById(R.id.header_day_two_text_view);
        mDayThreeTextView = root.findViewById(R.id.header_day_three_text_view);
        progressBar = root.findViewById(R.id.progressBar);

        mDayOneCardView = root.findViewById(R.id.day_one_header_card_view);
        mDayTwoCardView = root.findViewById(R.id.day_two_header_card_view);
        mDayThrreeCardView = root.findViewById(R.id.day_three_header_card_view);

        rfaLayout = root.findViewById(R.id.schedule_filter_kayout);
        filterFab = root.findViewById(R.id.schedule_filter_fab);
        displayFabOptions();

        Realm.init(context);
        realm = Realm.getDefaultInstance();
        getDatafromRealm(realm);
        Log.d("Calling Api:", "callApi()");
        callApi();
        progressBar.setVisibility(View.VISIBLE);
        //TODO: Get scheduled events and store them in ArrayList


        return root;
    }

    private final int FILTER_COMPETITION = 0;
    private final int FILTER_WORKSHOP = 1;
    private final int FILTER_TALK = 2;
    private final int NO_FILTER = 3;

    private void displayFabOptions() {
        RapidFloatingActionContentLabelList fabContent = new RapidFloatingActionContentLabelList(context);
        fabContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> options = new ArrayList<>();
        options.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.Competition))
                .setWrapper(FILTER_COMPETITION)
        );
        options.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.Workshop))
                .setWrapper(FILTER_WORKSHOP)
        );
        options.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.Talk))
                .setWrapper(FILTER_TALK)
        );
        options.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.none))
                .setWrapper(NO_FILTER)
        );

        fabContent.setItems(options);
        filterFabHelpler = new RapidFloatingActionHelper(context, rfaLayout, filterFab, fabContent).build();
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (position) {
            case FILTER_COMPETITION:
                filterSet = true;
                filterRecyclerView(FILTER_COMPETITION);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
                break;
            case FILTER_WORKSHOP:
                filterSet = true;
                filterRecyclerView(FILTER_WORKSHOP);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
                break;
            case FILTER_TALK:
                filterSet = true;
                filterRecyclerView(FILTER_TALK);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
                break;
            case NO_FILTER:
            default:
                filterSet = false;
                filterRecyclerView(NO_FILTER);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
        }
    }

    private void filterRecyclerView(int filterBy) {
        String filterTag;
        if (filterBy == FILTER_COMPETITION)
            filterTag = "competition";
        else if (filterBy == FILTER_WORKSHOP)
            filterTag = "workshop";
        else if (filterBy == FILTER_TALK)
            filterTag = "talk";
        else
            filterTag = "none";
        setAdapter(filterDetailList(eventDetailsList, filterTag));
    }


    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        //do nothing since icons have been disabled
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

    private void callApi() {
        EventsInterface apiservice = ApiClient.getClient().create(EventsInterface.class);
        Call<ArrayList<ScheduleEvent>> call = apiservice.getEvents();
        call.enqueue(new Callback<ArrayList<ScheduleEvent>>() {
            @Override
            public void onResponse(Call<ArrayList<ScheduleEvent>> call, Response<ArrayList<ScheduleEvent>> response) {
                eventDetailsList = response.body();
                System.out.println("Api Response:\n");
                // System.out.println(response.body());
                Log.d("Success Response", String.valueOf(response.body()));
                try {
                    for (int i = 0; i < eventDetailsList.size(); i++) {
                        // Log.d("Api response",eventDetailsList.get(i));
                        //System.out.println(eventDetailsList.get(i));
                        addDatatoRealm(eventDetailsList.get(i));
                    }
                    isnetwork = true;
                } catch (Exception e) {
                    Toast.makeText(context, "There was a problem fetching the data. Try again later", Toast.LENGTH_SHORT).show();
                }
                getDatafromRealm(realm);
                //swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<ScheduleEvent>> call, Throwable t) {
                Log.d("Api failure", "No Internet 1");
                getDatafromRealm(realm);
                //swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void addDatatoRealm(ScheduleEvent details) {
        //System.out.println(details);
        realm.beginTransaction();
        ScheduleEvent model = realm.where(ScheduleEvent.class).equalTo("id", details.getId()).findFirst();
        if (model == null) {
            ScheduleEvent event = realm.createObject(ScheduleEvent.class);
            event.setId(details.getId());
            event.setName(details.getName());
            event.setAbout(details.getAbout());
            event.setTagline(details.getTagline());
            event.setPrize(details.getPrize());
            event.setPrice(details.getPrice());
            event.setVenue(details.getVenue());
            event.setType(details.getType());
            event.setStartTime(getEventTime(details.getStartTime())[3] + ":" + getEventTime(details.getStartTime())[4]);
            event.setEndTime(details.getEndTime());
            event.setRoute(details.getRoute());
        } else {
            model.setName(details.getName());
            model.setAbout(details.getAbout());
            model.setTagline(details.getTagline());
            model.setPrize(details.getPrize());
            model.setPrice(details.getPrice());
            model.setVenue(details.getVenue());
            model.setType(details.getType());
            model.setStartTime(getEventTime(details.getStartTime())[3] + ":" + getEventTime(details.getStartTime())[4]);
            model.setEndTime(details.getEndTime());
            model.setRoute(details.getRoute());
        }
        realm.commitTransaction();
    }

    private void getDatafromRealm(Realm realm1) {
        if (realm1 != null) {
            realmList = new ArrayList<>();

            RealmResults<ScheduleEvent> results = realm1.where(ScheduleEvent.class).findAll();
            if (results.size() == 0) {
                Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
            } else {
                if (!isnetwork) {

                }
                for (int i = 0; i < results.size(); i++) {
                    if (results.get(i).getRoute() == null || results.get(i).getRoute().equals("")) {
                        Log.e(TAG, "No Route found");
                    } else {
                        realmList.add(results.get(i));
                        Log.e(TAG, results.get(i).getRoute());
                        Log.e("Type: ", results.get(i).getType());
                    }
                }
                Log.e(TAG, String.valueOf(realmList.size()) + " " + String.valueOf(results.size()));
            }
            setAdapter(realmList);
        } else {
            Log.e(TAG, "realm is null");
        }
    }

    private ArrayList<ScheduleEvent> filterDetailList(ArrayList<ScheduleEvent> eventDetailsList, String filterBy) {
        if (filterBy.equalsIgnoreCase("none"))
            return eventDetailsList;
        else {
            ArrayList<ScheduleEvent> filteredDetailList = new ArrayList<>();
            for (ScheduleEvent event : eventDetailsList) {
                String eventType = event.getType();
                if (eventType.equalsIgnoreCase(filterBy)) {
                    filteredDetailList.add(event);
                }
            }
            return filteredDetailList;
        }
    }

    private void setAdapter(final ArrayList<ScheduleEvent> eventDetailsList) {
        mAdapter = new ScheduleAdapter(eventDetailsList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setDayOne();

        final LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findFirstVisibleItemPosition();
                Log.e("Position is", Integer.toString(position));
//                String day = eventDetailsList.get(position).getDate();
//                switch(day) {
//                    case 2:
//                        setDayTwo();
//                        break;
//                    case 3:
//                        setDayThree();
//                        break;
//                    case 1:
//                    default:
//                        setDayOne();
//                }
//                Log.d("Day:\n\n\n",day);
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
                smoothScroller.setTargetPosition(6);
                manager.startSmoothScroll(smoothScroller);
            }
        });

        mDayThrreeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Set target scroll position for day 3
                smoothScroller.setTargetPosition(19);
                manager.startSmoothScroll(smoothScroller);
            }
        });
    }

    public String[] getEventTime(String time) {

        // The format of the startTime string is yyyy-MM-dd-HH-mm
        // HH-mm is the time in 24 hour format. Use this after conversion to 12 hour format.

        String pattern = "\\d{4}(-\\d{2}){4}";
        String[] parts = {"", "", "", "", ""};
        // testdate corresponds to 10:05 AM (10:05 hours), 11th August 2018
        String testdate = "2018-08-11-10-05"; // replace with details.getStartTime()

        // validation condition. If false, do not parse the time, and have a default fallback option
        if (time.matches(pattern)) {
            // Split the testdate String, to obtain the various parts of the time
            parts = time.split("-");
            // wrt to testdate
            // parts[0] => yyyy => 2018
            // parts[1] => MM => 08
            // parts[2] => DD => 11
            // parts[3] => HH => 10
            // parts[4] => mm => 5
            return parts;
        }

        return parts;

    }

}