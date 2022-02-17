package com.example.myapplication;

public class CardLayout {
    private int imageview;
    private String app_name;
    private String app_time;

    public CardLayout(String app_name, String app_time, int imageview){
        this.app_name = app_name;
        this.imageview = imageview;
        this.app_time = app_time;

    }

    public int getImageview() {
        return imageview;
    }

    public void setImageview(int imageview) {
        this.imageview = imageview;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_time(){return app_time;}

    public void setApp_time(String app_time){this.app_time = app_time;}




}
