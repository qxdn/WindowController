package com.sunbest.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AppUtil {

    /**
     * 重启 杀进程强制重启
     * @param context
     */
    public static void reboot(Context context){
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.putExtra("REBOOT","reboot");
        PendingIntent restartIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
