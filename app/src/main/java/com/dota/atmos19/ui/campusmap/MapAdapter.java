package com.dota.atmos19.ui.campusmap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dota.atmos19.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.MyViewHolder> {

    Context context;

    String[] placeName = {

            "Main Gate",

            "Bus Stop",

            "Cafeteria",

            "Auditorium",

            "Stage 1",

            "Stage 2",

            "Connaught Place",

            "SBI ATM",

            "EURO Cafe",

            "More Shopping Mall",

            "Mess1",

            "Mess2",

            "Rock Garden",

            "F-Block",

            "Student Activity Center",

            "G-Block",

            "B-Block",

            "D-Block",

            "Library"};

    public static double[] latitudes = {

            17.547152,

            17.547400,

            17.544982,

            17.545510,

            17.545019,

            17.544013,

            17.542024,

            17.542241,

            17.541928,

            17.542021,

            17.542428,

            17.544771,

            17.544141,

            17.544772,

            17.540799,

            17.545665,

            17.5453394,

            17.5443279,

            17.5456641};


    public static double[] longitudes = {

            78.572481,

            78.572387,

            78.570834,

            78.570511,

            78.571561,

            78.573877,

            78.575848,

            78.575974,

            78.575802,

            78.576085,

            78.574010,

            78.575194,

            78.572706,

            78.571040,

            78.575273,

            78.571507,

            78.5723279,

            78.571846,

            78.571647};

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.place_text);
        }
    }

    public MapAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_map, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.textView.setText(placeName[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + latitudes[position] + "," + longitudes[position]));
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                Log.e("", "Selected" + position);
                context.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeName.length;
    }
}
