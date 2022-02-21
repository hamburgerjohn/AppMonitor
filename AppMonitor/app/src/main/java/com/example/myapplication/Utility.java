package com.example.myapplication;

import static android.app.AppOpsManager.MODE_ALLOWED;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Utility extends AppCompatActivity {

    private final Context main_act;
    private final List<AppContainer> app_rec;
    private SortedMap<Long, UsageStats> sorted_map;

    public Utility(Context main_act) {
        this.main_act = main_act;
        this.app_rec = new ArrayList<>();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            InitPackages();
//        }else{
//            act_mgr = (ActivityManager)main_act.getSystemService(Context.ACTIVITY_SERVICE);
//            tasks = act_mgr.getRunningAppProcesses();
//        }
    }
    @SuppressLint("NewApi")
    private void InitPackages(){
        UsageStatsManager stats_mgr = (UsageStatsManager) main_act.getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> app_list = stats_mgr.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, System.currentTimeMillis() - 1000 * 3600 * 1, System.currentTimeMillis());

        if(app_list != null && app_list.size() > 0) {
            this.sorted_map = new TreeMap<Long, UsageStats>();
            for (UsageStats usage_stats : app_list) {
                sorted_map.put(usage_stats.getTotalTimeInForeground(), usage_stats);
            }
        }
    }

    @SuppressLint("NewApi")
    public void SetRunningApplications(){
        InitPackages();
        app_rec.clear();
        ArrayList<String> this_is_so_bad = new ArrayList<>();

        if(sorted_map != null && !sorted_map.isEmpty()){
            for(SortedMap.Entry<Long,UsageStats> entry : sorted_map.entrySet()){
                if(!this_is_so_bad.contains(entry.getValue().getPackageName())){
                    app_rec.add(new AppContainer(entry.getValue().getPackageName(),entry.getKey()));
                    this_is_so_bad.add(entry.getValue().getPackageName());
                }

            }
        }
        this_is_so_bad.clear();

    }

    public void UpdatePackages(){
        InitPackages();
        for(SortedMap.Entry<Long,UsageStats> entry : sorted_map.entrySet()){
            for(int i = 0; i < app_rec.size(); i++){
                if(app_rec.get(i).GetName().contentEquals(entry.getValue().getPackageName())){
                    app_rec.get(i).SetFinalTime(entry.getKey());
                }

            }
        }
    }

    public ArrayList<CardLayout> GetAllPackages(ArrayList<CardLayout> cards, ArrayList<String> cards_clicked){
        cards.clear();

        for(int i = 0; i < app_rec.size(); i++){
            if(cards_clicked != null){
                if(cards_clicked.contains(app_rec.get(i).GetName()))
                {
                    cards.add(new CardLayout(FormatAppName(app_rec.get(i).GetName()), app_rec.get(i).GetName(), ""+app_rec.get(i).GetTimeSec()+"s", RetrieveIcons(app_rec.get(i).GetName())));
                }

            }

            else
                cards.add(new CardLayout(FormatAppName(app_rec.get(i).GetName()), app_rec.get(i).GetName(), ""+app_rec.get(i).GetTimeSec()+"s", RetrieveIcons(app_rec.get(i).GetName())));
        }
        return cards;
    }

    public boolean GetGrantStatus(){
        AppOpsManager app_ops = (AppOpsManager) main_act.getApplicationContext().getSystemService(Context.APP_OPS_SERVICE);
        int mode = app_ops.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), main_act.getApplicationContext().getPackageName());
        if(mode == AppOpsManager.MODE_DEFAULT)
            return (main_act.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        else
            return (mode == MODE_ALLOWED);

    }

    private Drawable RetrieveIcons(String app_name){
        Drawable icon;
        try {
            icon = main_act.getPackageManager().getApplicationIcon(app_name);
        } catch (PackageManager.NameNotFoundException e) {
            icon = main_act.getApplicationInfo().loadIcon(main_act.getPackageManager());
            e.printStackTrace();
        }
        return icon;
    }

    private String FormatAppName(String app_name){
        StringBuilder formatted = new StringBuilder("");

        for(int i = app_name.length()-1; i > 0; i--){
            if(app_name.charAt(i) == '.')
                break;
            formatted.append(app_name.charAt(i));
        }
        formatted.reverse();

        return formatted.toString().substring(0,1).toUpperCase() + formatted.toString().substring(1);
    }

    public void Reset(){
        for(int i = 0; i < app_rec.size(); i++)
        {
            app_rec.get(i).SetInitTime(app_rec.get(i).GetFinalTime());
        }
    }





}
