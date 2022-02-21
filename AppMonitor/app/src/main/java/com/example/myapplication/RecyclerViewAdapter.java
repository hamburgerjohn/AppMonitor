package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private final ArrayList<CardLayout> cards;
    private final ArrayList<String> cards_clicked;



    public RecyclerViewAdapter(ArrayList<CardLayout> cards){
        this.cards = cards;
        this.cards_clicked = new ArrayList<>();
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = cards.get(position).getApp_name();
        String time = cards.get(position).getApp_time();
        Drawable icon = cards.get(position).getApp_icon();

        holder.setData(name, time, icon);

        holder.linear.setOnClickListener(v->{
            cards_clicked.add(cards.get(position).getRaw_app_name());
            holder.linear.setBackgroundColor(R.color.black);

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

        public void setData(String name, String time, Drawable icon) {

            image.setImageDrawable(icon);
            application_name.setText(name);
            if(!(Long.parseLong(time.substring(0, time.length() -1))<0))
                application_time.setText(time);
        }

    }
    public ArrayList<String> getCardsClicked(){
        return this.cards_clicked;
    }
}
