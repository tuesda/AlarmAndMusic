package com.example.root.alarms;

import android.content.Context;
import android.os.PowerManager;

import com.example.root.main.alarmandmusic.Log;

/**
 * Created by zhanglei on 15/6/22.
 */
public class AlarmAlertWakeLock {
    private static PowerManager.WakeLock sCpuWakeLock;

    public static void acquireCpuWakeLock(Context context) {
        Log.v("Acquiring cpu wake lock");
        if (sCpuWakeLock != null) {
            return;
        }
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        sCpuWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                                        | PowerManager.ACQUIRE_CAUSES_WAKEUP
                                        | PowerManager.ON_AFTER_RELEASE, Log.LOGTAG);
        sCpuWakeLock.acquire();
    }

    public static void releaseCpuLock() {
        Log.v("Releasing cpu wake lock");
        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
    }
}
