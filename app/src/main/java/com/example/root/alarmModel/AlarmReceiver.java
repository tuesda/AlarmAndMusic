package com.example.root.alarmModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.root.alarms.AlertActivity;

/**
 * Created by zhanglei on 15/6/14.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentAlert = new Intent(context, AlertActivity.class);
        intentAlert.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentAlert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentAlert.putExtra("alarm_id", intent.getIntExtra("alarm_id", -1));
        context.startActivity(intentAlert);
    }
}
