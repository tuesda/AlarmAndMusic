package com.example.root.alarmModel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhanglei on 15/6/1.
 */
public class AlarmSQLiteHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "alarms.db";
    private static final int DATABASE_VERSION = 1;



    public AlarmSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Method is called during creation of database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        AlarmsTable.onCreate(sqLiteDatabase);
    }

    // Method is called during upgrade of the database
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        AlarmsTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

}
