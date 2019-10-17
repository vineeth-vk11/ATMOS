package com.example.atmos.ui.schedule;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.example.atmos.R;
import com.example.atmos.api.ApiClient;
import com.example.atmos.api.EventsInterface;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.content.ContentValues.TAG;

public class ScheduleFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

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
    private RecyclerView recyclerView;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton filterFab;
    private RapidFloatingActionHelper filterFabHelpler;

    private ScheduleAdapter mAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

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

        noDayOne = 0;
        noDayTwo = 0;
        noDayThree = 0;

        displayFabOptions();

        progressBar.setVisibility(View.VISIBLE);
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        getDatafromRealm(realm);
        Log.d("Calling Api:", "callApi()");
        callApi();

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
                filterRecyclerView(FILTER_COMPETITION);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
                break;
            case FILTER_WORKSHOP:
                filterRecyclerView(FILTER_WORKSHOP);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
                break;
            case FILTER_TALK:
                filterRecyclerView(FILTER_TALK);
                mAdapter.notifyDataSetChanged();
                filterFabHelpler.toggleContent();
                break;
            case NO_FILTER:
            default:
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

    private int noDayOne, noDayTwo, noDayThree;

    private void enterDayNoFromDate(String date) {
        String dayNo = getDay(date);
        if(dayNo.equals("19"))
            noDayTwo++;
        else if(dayNo.equals("20"))
            noDayThree++;
        else
            noDayOne++;
    }

    private String getDay(String date) {
        String dayNo;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
            Date dateObj = sdf.parse(date);
            dayNo = new SimpleDateFormat("dd").format(dateObj);
            return dayNo;
        } catch (ParseException e) {
            e.printStackTrace();
            return "18";
        }
    }

    private void addDatatoRealm(ScheduleEvent details) {
        //System.out.println(details);
        realm.beginTransaction();
        ScheduleEvent model = realm.where(ScheduleEvent.class).equalTo("id", details.getId()).findFirst();
        if (model == null) {
            ScheduleEvent event = realm.createObject(ScheduleEvent.class);
            Log.d("StartTime1",details.getStartTime());
            event.setId(details.getId());
            event.setName(details.getName());
            event.setAbout(details.getAbout());
            event.setTagline(details.getTagline());
            event.setPrize(details.getPrize());
            event.setPrice(details.getPrice());
            event.setVenue(details.getVenue());
            event.setType(details.getType());
            event.setStartTime(details.getStartTime());
            enterDayNoFromDate(event.getStartTime());
            event.setEndTime(details.getEndTime());
            event.setRoute(details.getRoute());
        } else {
            Log.d("StartTime2",details.getStartTime());
            model.setName(details.getName());
            model.setAbout(details.getAbout());
            model.setTagline(details.getTagline());
            model.setPrize(details.getPrize());
            model.setPrice(details.getPrice());
            model.setVenue(details.getVenue());
            model.setType(details.getType());
            model.setStartTime(details.getStartTime());
            enterDayNoFromDate(details.getStartTime());
            model.setEndTime(details.getEndTime());
            model.setRoute(details.getRoute());
        }
        realm.commitTransaction();
    }

    private void getDatafromRealm(Realm realm1) {
        if (realm1 != null) {
            realmList = new ArrayList<>();
            RealmResults<ScheduleEvent> results = realm1.where(ScheduleEvent.class).findAll();
            results = results.sort("startTime");
     
            if (results.size() == 0) {
                Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
            } else {
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
        Collections.sort(eventDetailsList);
        mAdapter = new ScheduleAdapter(eventDetailsList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setDayOne();

        final LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findFirstVisibleItemPosition() + 1;
                Log.d("Position", Integer.toString(position));
                if(eventDetailsList!=null&&!eventDetailsList.isEmpty()&&position!=0)
                {
                    String day = getDay(eventDetailsList.get(position).getStartTime());
                    Log.d("Day", day);
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
    }
}