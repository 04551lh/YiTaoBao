package com.feicui.android.yitaobao;

import android.app.ActivityManager;
import android.app.Application;
import android.util.Log;

import com.feicui.android.yitaobao.Model.CachePrefereces;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/12/3.
 */
public class ShopApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
//        if(isAppProcess()){
            DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .resetViewBeforeLoading(true)
                    .build();
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                    .diskCacheSize(4 * 1024 * 1024)
                    .defaultDisplayImageOptions(displayImageOptions)
                    .build();
            ImageLoader.getInstance().init(configuration);

            CachePrefereces.init(this);
            initEaseUI();
//        }
    }

    private void initEaseUI(){
//        EMOptions options = new EMOptions();
//        options.setAutoLogin(false);
//        options.setAcceptInvitationAlways(false);
//        EMClient.getInstance().init(this, options);
//        EMClient.getInstance().setDebugMode(false);
    }
    private boolean isAppProcess(){
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if(processAppName == null || !processAppName.equalsIgnoreCase(this.getPackageName())){
            Log.e("TAG", "enter the service process!");
            return false;
        }
        return true;
    }

    private String getAppName(int pID){
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
//        PackageManager pm = this.getPackageManager();
        while (i.hasNext()){
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)(i.next());
            try {
                if(info.pid == pID){
                    processName = info.processName;
                    return  processName;
                }
            }
            catch (Exception e){
                Log.d("Process", "Error >> :" + e.toString());
            }
        }
        return processName;
    }
}



