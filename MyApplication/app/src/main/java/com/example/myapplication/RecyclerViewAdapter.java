package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<CardLayout> cards;
    private Context context;


    public RecyclerViewAdapter(ArrayList<CardLayout> cards, Context context){
        this.context = context;
        this.cards = cards;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        int resource = cards.get(position).getImageview();
        String name = cards.get(position).getApp_name();
        String time = cards.get(position).getApp_time();

        holder.setData(resource, name, time);
        holder.linear.setOnClickListener(v->{
            Log.d(TAG, "onClick: clicked on: " + cards.get(position).getApp_name());
            Toast.makeText(context, cards.get(position).getApp_name(), Toast.LENGTH_SHORT).show();
       });

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView application_name, application_time;
        RelativeLayout linear;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            application_name = itemView.findViewById(R.id.application_name);
            application_time = itemView.findViewById(R.id.application_time);
            linear = itemView.findViewById(R.id.layout_listitem);

        }

        public void setData(int resource, String name, String time) {
            image.setImageResource(R.mipmap.ic_launcher);
            application_name.setText(name);
            application_time.setText(time);
        }
    }
}
