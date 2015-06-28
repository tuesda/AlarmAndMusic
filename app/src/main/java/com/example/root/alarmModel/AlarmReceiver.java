package com.example.root.alarmModel;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.root.alarms.AlarmAlertWakeLock;
import com.example.root.alarms.AlertActivity;
import com.example.root.main.alarmandmusic.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhanglei on 15/6/14.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /** If the alarm is older than STALE_WINDOW seconds, ignore.  It
     is probably the result of a time or timezone change */
    private final static int STALE_WINDOW = 60 * 30;


    @Override
    public void onReceive(Context context, Intent intent) {

        if (Alarms.CANCEL_SNOOZE.equals(intent.getAction())) {
            Alarms.saveSnoozeAlert(context, -1, -1);
            return;
        }


        AlarmItem alarmItem = null;
        int alarm_id = intent.getIntExtra(Alarms.AlARM_ID, -1);
        Log.v("***AlarmReceiver.onReceive()***" + " alarm id = " + alarm_id);
        alarmItem = Alarms.getAlarmWithId(context, alarm_id);
        if (alarmItem == null) {
            Log.v("AlarmReceiver failed to get alarmItem from the database");
            return;
        }

        // Intentionally verbose: always log the alarm time to provide useful
        // information in bug reports.
        long now = System.currentTimeMillis();
        SimpleDateFormat format =
                new SimpleDateFormat("HH:mm:ss.SSS aaa");
        Log.v("AlarmReceiver.onReceive() id " + alarmItem.getId() + " setFor "
                + format.format(new Date()));

        if (now > Alarms.calculateAlarm(alarmItem.getTimeInDay().getHour(), alarmItem.getTimeInDay().getMin(), alarmItem.getWeeks()).getTimeInMillis() + STALE_WINDOW * 1000) {
            if (Log.LOGV) {
                Log.v("AlarmReceiver ignoring stale alarm");
            }
            return;
        }

        // Maintain a cpu wake lock until the AlarmAlert and AlarmKlaxon can
        // pick it up.
        AlarmAlertWakeLock.acquireCpuWakeLock(context);

        /* Close dialogs and window shade */
        Intent closeDialogs = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(closeDialogs);


        // Decide which activity to start based on the state of the keyguard.
        Class c = AlertActivity.class;
        KeyguardManager km = (KeyguardManager) context.getSystemService(
                Context.KEYGUARD_SERVICE);

        /* launch UI, explicitly stating that this is not due to user action
         * so that the current app's notification management is not disturbed */
        Intent alarmAlert = new Intent(context, c);
        alarmAlert.putExtra("alarm_id", intent.getIntExtra("alarm_id", -1));
        alarmAlert.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        context.startActivity(alarmAlert);



        // Disable the snooze alert if this alarm is the snooze.
        Alarms.disableSnoozeAlert(context, alarmItem.getId());
        // Disable this alarm if it does not repeat.
        if (!Alarms.isWeeksRepeat(alarmItem.getWeeks())) {
            //Log.v("fuck:   I am in not repeat weeks weeks = " + alarmItem.getWeeks());
            Alarms.enableAlarm(context, alarmItem.getId(), false);
        } else {
            // Enable the next alert if there is one. The above call to
            // enableAlarm will call setNextAlert so avoid calling it twice.
            Alarms.setNextAlert(context);
        }





        // Play the alarm alert and vibrate the device.
        Intent playAlarm = new Intent(Alarms.ALARM_ALERT_ACTION);
        playAlarm.putExtra("alarm_id", alarm_id);
        context.startService(playAlarm);





    }
}
