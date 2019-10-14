package com.example.atmos.ui.schedule;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmos.R;

import java.util.ArrayList;
import java.util.Date;

public class ScheduleFragment extends Fragment {

    private ScheduleViewModel scheduleViewModel;

    private TextView mDayOneTextView;
    private CardView mDayOneCardView;
    private TextView mDayTwoTextView;
    private CardView mDayTwoCardView;
    private TextView mDayThreeTextView;
    private CardView mDayThrreeCardView;

    private ArrayList<ScheduleEvent> events;

    private int scrollPosition;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel =
                ViewModelProviders.of(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        final RecyclerView recyclerView = root.findViewById(R.id.schedule_recycler_view);
        mDayOneTextView = root.findViewById(R.id.header_day_one_text_view);
        mDayTwoTextView = root.findViewById(R.id.header_day_two_text_view);
        mDayThreeTextView = root.findViewById(R.id.header_day_three_text_view);

        mDayOneCardView = root.findViewById(R.id.day_one_header_card_view);
        mDayTwoCardView = root.findViewById(R.id.day_two_header_card_view);
        mDayThrreeCardView = root.findViewById(R.id.day_three_header_card_view);

        events = new ArrayList<>();
        events.add(new ScheduleEvent(new Date(), "Algomaniac", "G-101", 1, 1));
        events.add(new ScheduleEvent(new Date(), "Algomaniac", "G-101", 1, 1));
        events.add(new ScheduleEvent(new Date(), "Algomaniac", "G-101", 1, 1));
        events.add(new ScheduleEvent(new Date(), "Algomaniac", "G-101", 1, 1));
        events.add(new ScheduleEvent(new Date(), "Algomaniac", "G-101", 1, 1));
        events.add(new ScheduleEvent(new Date(), "Algomaniac", "G-101", 1, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Panel discussion", "Auditorium", 2, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        events.add(new ScheduleEvent(new Date(), "Competitive coding wrokshop", "F-204", 3, 1));
        //TODO: Get scheduled events and store them in ArrayList

        ScheduleAdapter adapter = new ScheduleAdapter(events);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setDayOne();

        final LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager() ;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findFirstVisibleItemPosition();
                Log.d("First visible item pos", Integer.toString(position));
                int day = events.get(position).getDay();
                switch(day) {
                    case 1:
                        setDayOne();
                        break;
                    case 2:
                        setDayTwo();
                        break;
                    case 3:
                        setDayThree();
                        break;
                    default:
                        setDayOne();
                }
            }
        });
        return root;
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