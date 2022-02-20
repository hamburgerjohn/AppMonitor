package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class CardLayout{
    private String app_name;
    private String app_time;
    private String raw_app_name;
    private Drawable app_icon;
    private String app_icon1;

    public CardLayout(String app_name, String raw_app_name, String app_time, Drawable app_icon){
        this.app_name = app_name;
        this.app_time = app_time;
        this.app_icon = app_icon;
        this.raw_app_name = raw_app_name;
    }


    public String getRaw_app_name(){return raw_app_name;}

    public void setRaw_app_name(String raw_app_name){this.raw_app_name = raw_app_name;}

    public Drawable getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(Drawable app_icon) {
        this.app_icon = app_icon;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_time() {
        return app_time;
    }

    public void setApp_time(String app_time) {
        this.app_time = app_time;
    }



}