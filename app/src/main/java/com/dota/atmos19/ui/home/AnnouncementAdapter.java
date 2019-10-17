package com.dota.atmos19.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dota.atmos19.R;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.FeedViewHolder> {
    Context context;
    LayoutInflater mInflater;
    ArrayList<String> timeArray = new ArrayList<>();
    ArrayList<String> deptArray = new ArrayList<>();
    ArrayList<String> descArray = new ArrayList<>();
    int i;

    public AnnouncementAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        Log.v("Feed Adapter", "timeArray is created..!!!!");
        System.out.print(i);
    }

    public AnnouncementAdapter(Context context, ArrayList<String> timeArray, ArrayList<String> deptArray, ArrayList<String> descArray, int i) {
        this.context = context;
        this.timeArray = timeArray;
        this.deptArray = deptArray;
        this.descArray = descArray;
        this.i = i;
    }

    @NonNull
    @Override
    public AnnouncementAdapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_announcements, parent, false);
        return new AnnouncementAdapter.FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, final int position) {
        if (timeArray.get(position) != null) {
            if (timeArray.get(position).equals("01-01 05:30")) {
                holder.time.setVisibility(View.GONE);
            } else {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setText(timeArray.get(position));

            }
        } else {
            holder.time.setText("");
        }
        if (deptArray.get(position) != null && deptArray.get(position) != "") {
            holder.dept.setText(deptArray.get(position));
        } else {
            holder.dept.setText("");
        }
        if (descArray.get(position) != null && descArray.get(position) != "") {
            holder.desc.setText(descArray.get(position));
        } else {
            holder.desc.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return i;
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView dept;
        TextView desc;

        public FeedViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            dept = itemView.findViewById(R.id.dept);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}