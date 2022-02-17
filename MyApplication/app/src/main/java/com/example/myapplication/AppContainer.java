package com.example.myapplication;


import android.util.Pair;

import java.util.Map;

public class AppContainer {

    private String name;
    private long init_time, final_time=0;
    public AppContainer(String name, long init_time){
        this.name = name;
        this.init_time = init_time;
    }

    public long GetInitTime(){
        return this.init_time;
    }

    public long GetFinalTime(){
        return this.final_time;
    }
    public long GetTimeSec(){
        if(this.final_time == 0)
            return 0;

        return (this.final_time-this.init_time)/1000;
    }
    public String GetName(){
        return this.name;
    }
    public void SetFinalTime(long final_time){
        this.final_time = final_time;
    }

}
