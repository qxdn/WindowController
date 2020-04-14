package com.sunbest.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetStatusUtil {

    public static boolean isNetConnect(Context context){
        if(context!=null){
            ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getActiveNetworkInfo()!=null){
                return connectivityManager.getActiveNetworkInfo().isAvailable();
            }
        }
        return false;
    }
}
