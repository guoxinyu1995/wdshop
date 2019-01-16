package com.example.wdshop.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.wdshop.application.MyApplication;

public class NetUtil {
    //判断是否有网络
    public static boolean hasNetWork(){
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isAvailable();
    }


}
