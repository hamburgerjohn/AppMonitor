package com.example.myapplication;

import android.util.Log;

public class AppContainer {

    private String name;
    private boolean idk = false;
    private long init_time=0, final_time=0, ret_time=0;
    public AppContainer(String name, long init_time){
        this.init_time = init_time;
        this.name = name;
    }

    public long GetInitTime(){
        return this.init_time;
    }

    public long GetFinalTime(){
        return this.final_time;
    }
    public long GetTimeSec(){
        ret_time = ((this.init_time-this.final_time)/1000);
        if(this.init_time > this.final_time)
            ret_time -= ret_time*2;

        return ret_time;
    }

    private boolean RetardCheck(){
        return (this.init_time < this.final_time);
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

