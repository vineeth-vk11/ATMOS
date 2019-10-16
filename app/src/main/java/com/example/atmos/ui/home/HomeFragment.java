package com.example.atmos.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.atmos.R;
import com.example.atmos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static java.util.Collections.reverse;

public class HomeFragment extends Fragment {
    RecyclerView mRecyclerView;
    AnnouncementAdapter mAnnouncementAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> timeArray = new ArrayList<>();
    ArrayList<String> deptArray = new ArrayList<>();
    ArrayList<String> descArray = new ArrayList<>();
    int i = 0;
    private ProgressBar progressBar;
    private Context context;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("Feed");
        progressBar = getActivity().findViewById(R.id.progressBar2);
        //swipeRefreshLayout = getActivity().findViewById(R.id.swipe_to_refresh_feed);

        mRecyclerView = view.findViewById(R.id.announcements_recycler_view);

        progressBar.setVisibility(View.VISIBLE);
        callFireBase();

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                callFireBase();
//            }
//        });
    }

    private void callFireBase() {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference notification = database.child("notifications");
        Log.v("Feed Adapter", "timeArray is created..!!!!");
        notification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("bjbhkjhni","In notif");
                timeArray = new ArrayList<String>();
                descArray = new ArrayList<String>();
                deptArray = new ArrayList<String>();
                i = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.e("Feed Fragment", ds.getKey());
                    Long timestamp = Long.parseLong(ds.getKey());
                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(timestamp);
                    String date = DateFormat.format("dd-MM HH:mm", cal).toString();

                    timeArray.add(date);
                    descArray.add(ds.child("content").getValue(String.class));
                    deptArray.add(ds.child("title").getValue(String.class));
                    i++;
                }

                reverse(timeArray);
                reverse(descArray);
                reverse(deptArray);

                Log.e("FEED FRAGMENT !", Integer.toString(deptArray.size()));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAnnouncementAdapter = new AnnouncementAdapter(getActivity(), timeArray, deptArray, descArray, i);
                //mFeedAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAnnouncementAdapter);
                progressBar.setVisibility(View.GONE);
                //swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });

        if (!isOnline()) {
            progressBar.setVisibility(View.GONE);
            //swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(context, "No Network....Get connected & Swipe to Refresh", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        progressBar.setVisibility(View.GONE);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
