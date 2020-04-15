package com.sunbest.util;

import android.content.Context;
import android.provider.Settings;

public class IDUtil {

    public static String getDeviceID(Context context){
        String android_id= Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        //StackOverFlow上说可能会返回null因此给一个默认值
       if(android_id==null){
            android_id="AndroidId";
        }
        return android_id;
    }
}
