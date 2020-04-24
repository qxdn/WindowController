package com.sunbest.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

public class IDUtil {

    private static final String TAG="IDUtil";

    public static String getDeviceID(Context context){
        String android_id= Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        //StackOverFlow上说可能会返回null因此给一个默认值
       if(android_id==null){
            android_id="AndroidId";
        }
        return android_id;
    }

    public static String getAppVersion(Context context){
        String version="";
        try {
            PackageManager manager=context.getPackageManager();
            PackageInfo info= null;
            info = manager.getPackageInfo(context.getPackageName(),0);
            version=info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getAppVersion: ", e);
        }
        return version;
    }
}
