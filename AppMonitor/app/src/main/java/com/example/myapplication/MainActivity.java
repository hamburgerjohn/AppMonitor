package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {

    private Utility util;
    private ArrayList<CardLayout> cards = new ArrayList<>();
    private ArrayList<String> cards_clicked;

    @Override
    protected void onStart(){
        super.onStart();
        Button b_auth = (Button) findViewById(R.id.b_auth);
        util = new Utility(this);
        if(!util.GetGrantStatus()){
            b_auth.setEnabled(true);
            b_auth.setVisibility(View.VISIBLE);
            b_auth.setOnClickListener(v->{
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            });
        }
        else{
            b_auth.setEnabled(false);
            b_auth.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cards_clicked = (ArrayList<String>) getIntent().getSerializableExtra("Cards");

        Button b_add = (Button) findViewById(R.id.b_add);
        b_add.setOnClickListener(v->{
            startActivity(new Intent(this, AppList.class));

        });

        //util.Reset();
        //cards.clear();
        //util.SetRunningApplications();
        //initData();

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
            //util.Reset();
            return true;
        }
        if(id == R.id.display){
            //util.Reset();
            cards.clear();
            util.SetRunningApplications();
            initData();
            return true;
        }

        return true;
    }

    private void initData(){
        cards = util.GetAllPackages(cards, cards_clicked);
        CreateRecyclerView();
    }

    private void CreateRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(cards, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}