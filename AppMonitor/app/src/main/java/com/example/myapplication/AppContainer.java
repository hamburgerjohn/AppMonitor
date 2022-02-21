package com.example.myapplication;

import android.util.Log;

public class AppContainer {

    private String name;
    private long init_time=0, final_time=0, ret_time=0;
    public AppContainer(String name, long init_time){
        this.init_time = init_time;
        this.name = name;
    }

    public long GetInitTime(){
        return this.init_time;
    }
    public void SetInitTime(long init_time) {this.init_time = init_time;}
    public long GetFinalTime(){
        return this.final_time;
    }
    public long GetTimeSec(){

        ret_time = ((this.final_time-this.init_time)/1000);

        return ret_time;
    }

    public String GetName(){
        return this.name;
    }
    public void SetFinalTime(long final_time){
        this.final_time = final_time;
    }
    public void SetRetTime(long time){
        this.ret_time = time;
    }

}

