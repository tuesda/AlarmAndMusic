package com.example.root.alarms;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.alarmModel.AlarmItem;
import com.example.root.alarmModel.AlarmReceiver;
import com.example.root.alarmModel.Alarms;
import com.example.root.alarmModel.AlarmsContentProvider;
import com.example.root.alarmModel.AlarmsTable;
import com.example.root.main.alarmandmusic.Log;
import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;

import java.util.Calendar;

/**
 * Created by zhanglei on 15/6/14.
 */
public class AlertActivity extends Activity {


    private Button closeAlert;
    private Button denyAlert;

    private AlarmItem mAlarmItem;

    private static final String DEFAULT_SNOOZE = "5";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_activity);
        int alarmId = getIntent().getIntExtra("alarm_id", -1);
        mAlarmItem = Alarms.getAlarmWithId(this, alarmId);
        initViews();


    }

    private void initViews() {
        closeAlert = (Button)findViewById(R.id.alert_close);
        denyAlert = (Button)findViewById(R.id.alert_deny);

        closeAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss(false);
            }
        });

        denyAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snooze();

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    // Dismiss the alarm.
    private void dismiss(boolean killed) {
        // The service told us that the alarm has been killed, do not modify
        // the notification or stop the service.
        if (!killed) {
            // Cancel the notification and stop playing the alarm
            stopService(new Intent(Alarms.ALARM_ALERT_ACTION));
        }
        finish();
    }

    // Attempt to snooze this alert.
    private void snooze() {
        final String snooze =
                PreferenceManager.getDefaultSharedPreferences(this)
                        .getString(MainActivity.KEY_ALARM_SNOOZE, DEFAULT_SNOOZE);
        int snoozeMinutes = Integer.parseInt(snooze);

        final long snoozeTime = System.currentTimeMillis()
                + (1000 * 60 * snoozeMinutes);
        Alarms.saveSnoozeAlert(AlertActivity.this, mAlarmItem.getId(),
                snoozeTime);

        // Get the display time for the snooze and update the notification.
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(snoozeTime);


//        // Append (snoozed) to the label.
//        String label = mAlarm.getLabelOrDefault(this);
//        label = getString(R.string.alarm_notify_snooze_label, label);
//
//        // Notify the user that the alarm has been snoozed.
//        Intent cancelSnooze = new Intent(this, AlarmReceiver.class);
//        cancelSnooze.setAction(Alarms.CANCEL_SNOOZE);
//        cancelSnooze.putExtra(Alarms.ALARM_ID, mAlarm.id);
//
//        PendingIntent broadcast =
//                PendingIntent.getBroadcast(this, mAlarmItem.getId(), cancelSnooze, 0);
//        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification n = new Notification(R.drawable.stat_notify_alarm,
//                label, 0);
//        n.setLatestEventInfo(this, label,
//                getString(R.string.alarm_notify_snooze_text,
//                        Alarms.formatTime(this, c)), broadcast);
//        n.flags |= Notification.FLAG_AUTO_CANCEL
//                | Notification.FLAG_ONGOING_EVENT;
//        nm.notify(mAlarm.id, n);




        String displayTime = getString(R.string.alarm_alert_snooze_set,
                snoozeMinutes);
        // Intentionally log the snooze time for debugging.
        Log.v(displayTime);

        // Display the snooze minutes in a toast.
        Toast.makeText(AlertActivity.this, displayTime,
                Toast.LENGTH_LONG).show();
        stopService(new Intent(Alarms.ALARM_ALERT_ACTION));
        finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
