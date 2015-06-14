package com.example.root.alarms;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.os.Bundle;

import com.example.root.alarmModel.Alarms;
import com.example.root.alarmModel.AlarmsContentProvider;
import com.example.root.alarmModel.AlarmsTable;
import com.example.root.main.alarmandmusic.R;

/**
 * Created by zhanglei on 15/6/14.
 */
public class AlertActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_activity);
        int alarmId = getIntent().getIntExtra("alarm_id", -1);
        ContentValues value = new ContentValues();
        value.put(AlarmsTable.COLUMN_ENABLE, 0);

        getContentResolver().update(ContentUris.withAppendedId(AlarmsContentProvider.CONTENT_URI, alarmId), value, null, null);



    }

    @Override
    protected void onPause() {
        super.onPause();
        Alarms.setNextAlert(getApplication());
    }
}
