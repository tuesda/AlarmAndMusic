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
    public static final String COLUMN_TITLE = "title";


    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ALARMS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TIME_HOUR + " integer not null, "
            + COLUMN_TIME_MIN + " integer not null, "
            + COLUMN_TITLE + " text not null"
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


}
