package com.example.root.alarmModel;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by zhanglei on 15/6/1.
 */
public class AlarmsTable {

    // Database table
    public static final String TABLE_ALARMS = "alarms";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME_HOUR = "hour";
    public static final String COLUMN_TIME_MIN = "min";
    public static final String COLUMN_DAYS_OF_WEEK = "daysofweek";
    public static final String COLUMN_ALARM_TIME = "alarmtime";
    public static final String COLUMN_ENABLE = "enable";
    public static final String COLUMN_ALERT_TYPE = "alerttype";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SNOOZE = "snooze";
    public static final String COLUMN_ALERT = "alert";
    public static final String COLUMN_RING_NAME = "ringname";
    public static final String COLUMN_VOLUME = "volume";
    public static final String COLUMN_VIBRATE = "vibrate";
    public static final String COLUMN_BACK_GROUND = "background";

    public static final String[] ALARM_QUERY_COLUMNS = {
            COLUMN_ID, COLUMN_TIME_HOUR, COLUMN_TIME_MIN, COLUMN_DAYS_OF_WEEK, COLUMN_ALARM_TIME,
            COLUMN_ENABLE, COLUMN_ALERT_TYPE, COLUMN_TITLE, COLUMN_SNOOZE, COLUMN_ALERT,
            COLUMN_RING_NAME, COLUMN_VOLUME, COLUMN_VIBRATE, COLUMN_BACK_GROUND
    };

    public static final String WHERE_ENABLE = COLUMN_ENABLE + "=1";


    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ALARMS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TIME_HOUR + " integer not null, "
            + COLUMN_TIME_MIN + " integer not null, "
            + COLUMN_DAYS_OF_WEEK + " integer not null, "
            + COLUMN_ALARM_TIME + " integer not null, "
            + COLUMN_ENABLE + " integer not null, "
            + COLUMN_ALERT_TYPE + " integer not null, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_SNOOZE + " integer not null, "
            + COLUMN_ALERT + " text not null, "
            + COLUMN_RING_NAME + " text not null, "
            + COLUMN_VOLUME + " integer not null, "
            + COLUMN_VIBRATE + " integer not null, "
            + COLUMN_BACK_GROUND + " text"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(AlarmSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXITS " + TABLE_ALARMS);
        onCreate(database);
    }


    public static void main(String[] args) {
        System.out.println(DATABASE_CREATE);
    }

}
