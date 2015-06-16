package com.example.root.main.alarmandmusic;

import android.os.SystemClock;
import android.util.Config;

/**
 * Created by zhanglei on 15/6/16.
 */
public class Log {
    public static String LOGTAG = "AlarmAndMusic";

    public static final boolean LOGV = MainActivity.DEBUG ? true : false;


    public static void v(String logMe) {
        android.util.Log.v(LOGTAG, SystemClock.uptimeMillis() + ": " + logMe);
    }

    public static void e(String logMe) {
        android.util.Log.e(LOGTAG, SystemClock.uptimeMillis() + ": " + logMe);
    }

    public static void e(String logMe, Exception e) {
        android.util.Log.e(LOGTAG, SystemClock.uptimeMillis() + ": " + logMe, e);
    }
}
