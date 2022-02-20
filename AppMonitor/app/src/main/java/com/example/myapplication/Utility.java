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

    private Context main_act;
    private UsageStatsManager stats_mgr;
    private ActivityManager act_mgr;
    private List<ActivityManager.RunningAppProcessInfo> tasks;
    private final List<AppContainer> app_rec;
    private List<UsageStats> app_list;
    private SortedMap<Long, UsageStats> sorted_map;

    public Utility(Context main_act) {
        this.main_act = main_act;
        this.app_rec = new ArrayList<>();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            InitPackages();
        }else{
            act_mgr = (ActivityManager)main_act.getSystemService(Context.ACTIVITY_SERVICE);
            tasks = act_mgr.getRunningAppProcesses();
        }
    }
    @SuppressLint("NewApi")
    private void InitPackages(){
        this.stats_mgr = (UsageStatsManager)main_act.getSystemService(USAGE_STATS_SERVICE);
        this.app_list = stats_mgr.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, System.currentTimeMillis() - 1000*3600*24, System.currentTimeMillis());
        this.sorted_map = new TreeMap<Long, UsageStats>();
        if(app_list != null && app_list.size() > 0) {
            for (UsageStats usage_stats : app_list) {
                sorted_map.put(usage_stats.getTotalTimeVisible(), usage_stats);
            }
        }
    }

    public ArrayList<CardLayout> GetAllPackages(ArrayList<CardLayout> cards, ArrayList<String> cards_clicked){
        for(int i = 0; i < app_rec.size(); i++){
            if(cards_clicked != null){
                if(cards_clicked.contains(app_rec.get(i).GetName()))
                {
                    Log.d("Final Time", "-------------------"+app_rec.get(i).GetFinalTime() + "---------------------------");
                    cards.add(new CardLayout(FormatAppName(app_rec.get(i).GetName()), app_rec.get(i).GetName(), ""+app_rec.get(i).GetTimeSec()+"s", RetrieveIcons(app_rec.get(i).GetName())));
                }

            }

            else
                cards.add(new CardLayout(FormatAppName(app_rec.get(i).GetName()), app_rec.get(i).GetName(), ""+app_rec.get(i).GetTimeSec()+"s", RetrieveIcons(app_rec.get(i).GetName())));
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
        app_rec.clear();
    }


}
