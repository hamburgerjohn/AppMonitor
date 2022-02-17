package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentSecondBinding;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<CardLayout> cards = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private Button button1, button3;;
    Utility util;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        util = new Utility(getActivity());
        setHasOptionsMenu(true);
        //button1 = (Button)getActivity().findViewById(R.id.show);
        //button3 = (Button)getActivity().findViewById(R.id.all);
        //button1.setOnClickListener(v -> {
            //util.SetRunningApplications();
        //});
        //button3.setOnClickListener(v->{
            //initData();
        //});

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            util.SetRunningApplications();
            return true;
        }
        if(id == R.id.display){
            initData();
            return true;
        }

        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initData(){
        cards = util.GetAllPackages(cards);
        CreateRecyclerView();
    }

    private void CreateRecyclerView(){


        recyclerView = getActivity().findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(cards, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}