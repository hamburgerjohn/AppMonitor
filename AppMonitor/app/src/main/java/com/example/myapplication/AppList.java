package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AppList extends AppCompatActivity{

    Utility util;
    private ArrayList<CardLayout> cards = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private Intent intent;

    public AppList(){
        super(R.layout.activity_applist);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Utility(this);
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(v->{
            intent = new Intent(this.getApplicationContext(), MainActivity.class);
            intent.putExtra("Cards", adapter.getCardsClicked());
            startActivity(intent);
            this.finish();
        });

        util.Reset();
        cards.clear();
        util.SetRunningApplications();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
           // util.Reset();
            cards.clear();
            util.SetRunningApplications();
            initData();
            return true;
        }

        return true;
    }

    private void initData(){
        cards = util.GetAllPackages(cards, null);
        CreateRecyclerView();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void CreateRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(cards);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
