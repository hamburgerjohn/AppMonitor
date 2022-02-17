package com.example.myapplication;

import static android.app.AppOpsManager.MODE_ALLOWED;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Utility extends AppCompatActivity{

    private Context main_act;
    private boolean begin = true;
    private long visible_time = 0, init_time = 0;
    private UsageStatsManager stats_mgr;
    private ActivityManager act_mgr;
    private List<ActivityManager.RunningAppProcessInfo> tasks;
    private List<AppContainer> app_rec;
    private List<UsageStats> app_list;
    private List<String> app_names;
    private SortedMap<Long, UsageStats> sorted_map;

    public Utility(Context main_act) {
        this.main_act = main_act;
        this.app_rec = new ArrayList<>();
        this.app_names = new ArrayList<>();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            InitPackages();
        }else{
            act_mgr = (ActivityManager)main_act.getSystemService(Context.ACTIVITY_SERVICE);
            tasks = act_mgr.getRunningAppProcesses();
        }
    }
    @SuppressLint("NewApi")
    public void InitPackages(){
        this.stats_mgr = (UsageStatsManager)main_act.getSystemService(USAGE_STATS_SERVICE);
        this.app_list = stats_mgr.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, System.currentTimeMillis() - 1000*3600*24, System.currentTimeMillis());
        this.sorted_map = new TreeMap<Long, UsageStats>();
        if(app_list != null && app_list.size() > 0) {
            for (UsageStats usage_stats : app_list) {
                sorted_map.put(usage_stats.getTotalTimeVisible(), usage_stats);
            }
        }
    }

    public void GetAllPackages(){
//        InitPackages();
//        for(SortedMap.Entry<Long,UsageStats> entry : sorted_map.entrySet()){
//            app_names.add(entry.getValue().getPackageName());
//        }
        for(int i = 0; i < app_rec.size(); i++){
            Log.d("Package Name: ", app_rec.get(i).GetName() + " Time Used: " + app_rec.get(i).GetTimeSec());
        }
    }
    public ArrayList<CardLayout> GetAllPackages(ArrayList<CardLayout> cards){
//
        ArrayList<String> idk = new ArrayList<>();
        for(int i = 0; i < app_rec.size(); i++){
            Log.d("Package Name: ", app_rec.get(i).GetName() + " Time Used: " + app_rec.get(i).GetTimeSec());
            cards.add(new CardLayout(app_rec.get(i).GetName(), ""+app_rec.get(i).GetTimeSec(), R.mipmap.ic_launcher));
        }
        return cards;
    }

    @SuppressLint("NewApi")
    public void SetRunningApplications(){
        InitPackages();
        if(sorted_map != null && !sorted_map.isEmpty()){
            for(SortedMap.Entry<Long,UsageStats> entry : sorted_map.entrySet()){

                boolean exist = false;
                for(int i = 0; i < app_rec.size(); i++){
                    if(app_rec.get(i).GetName().contentEquals(entry.getValue().getPackageName())){
                        app_rec.get(i).SetFinalTime(entry.getKey());
                        exist = true;
                        break;
                    }

                }
                if(!exist)
                    app_rec.add(new AppContainer(entry.getValue().getPackageName(),entry.getKey()));



            }


        }
    }

    public boolean GetGrantStatus(){
        AppOpsManager app_ops = (AppOpsManager) main_act.getApplicationContext().getSystemService(Context.APP_OPS_SERVICE);
        int mode = app_ops.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), main_act.getApplicationContext().getPackageName());
        if(mode == AppOpsManager.MODE_DEFAULT)
            return (main_act.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        else
            return (mode == MODE_ALLOWED);

    }


}
