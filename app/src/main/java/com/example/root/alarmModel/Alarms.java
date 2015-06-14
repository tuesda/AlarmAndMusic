package com.example.root.alarmModel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.example.root.scroll.TimeInDay;

import java.util.Calendar;

/**
 * Created by zhanglei on 15/6/14.
 * This is a tool class responsible for alarms related operations
 */
public class Alarms {

    // This action triggers the AlarmdReceiver as well as the AlarmKlaxon. It
    // is a public action used in the manifest for receiving Alarm broadcasts
    // from the alarm manager.
    public static final String ALARM_ALERT_ACTION = "com.zhanglei.alarmandmusic.ALARM_ALERT";

    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 4;
    private static final int THURSDAY = 8;
    private static final int FRIDAY = 16;
    private static final int SATURDAY = 32;
    private static final int SUNDAY = 64;
    private static final int DAYS_MASK = 127;

    public static final int[] DAYS_OF_WEEK = {
            MONDAY,
            TUESDAY,
            WEDNESDAY,
            THURSDAY,
            FRIDAY,
            SATURDAY,
            SUNDAY
    };


    public static void setNextAlert(final Context context) {
        AlarmItem alarmItem = calculateNextAlert(context);
        if (alarmItem!=null) {


            enableAlert(context, alarmItem, calculateAlarm(alarmItem.getTimeInDay().getHour(), alarmItem.getTimeInDay().getMin(), alarmItem.getWeeks()).getTimeInMillis());
        }
    }


    private static void enableAlert(Context context, final AlarmItem alarmItem, final long atTimeInMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(atTimeInMillis);
        Log.i("alarms_fuck", " " + c.toString());
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ALARM_ALERT_ACTION);
        intent.putExtra("alarm_id", alarmItem.getId());


        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, atTimeInMillis, sender);
    }

    public static AlarmItem calculateNextAlert(final Context context) {
        AlarmItem alarm = null;
        long minTime = Long.MAX_VALUE;
        long now = System.currentTimeMillis();
        Cursor cursor  = getFilterAlarmsCursor(context.getContentResolver());

        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    AlarmItem alarmItem = getAlarmFromCursor(cursor);
                    long nextTime = calculateAlarm(alarmItem.getTimeInDay().getHour(), alarmItem.getTimeInDay().getMin(), alarmItem.getWeeks()).getTimeInMillis();
                    if (nextTime < now) {
                        enableAlarmInternal(context, alarmItem, false);
                        continue;
                    }
                    if (nextTime < minTime) {
                        minTime = nextTime;
                        alarm = alarmItem;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return alarm;
    }


    public static Calendar calculateAlarm (int hour, int min, int daysInWeek) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        int addDays = getNextDaysOfWeek(daysInWeek, c);

        Log.i("alarms_fuck", "addDays: " + addDays);
        // when user didn't choose the days of week
        if (addDays==-1) {
            if (c.getTimeInMillis() < System.currentTimeMillis()) {
                addDays = 1;
            } else {
                addDays = 0;
            }
        }


        if (addDays > 0) {
            c.add(Calendar.DAY_OF_WEEK, addDays);
        }



        return c;
    }

    public static int getNextDaysOfWeek(int daysOfWeek, Calendar current) {
        int addDays = -1;
        Calendar tmpC = Calendar.getInstance();
        tmpC.setTimeInMillis(current.getTimeInMillis());
        int currentDay = tmpC.get(Calendar.DAY_OF_WEEK);
        for (int i=0; i < 7; i++) {
            if ((daysOfWeek & maskFromDay(tmpC.get(Calendar.DAY_OF_WEEK))) == maskFromDay(tmpC.get(Calendar.DAY_OF_WEEK))) {
                addDays = tmpC.get(Calendar.DAY_OF_WEEK) - currentDay;
                break;
            }
            tmpC.add(Calendar.DAY_OF_WEEK, 1);
        }
        return addDays;
    }

    private static int maskFromDay(int day) {
        switch (day) {
            case Calendar.MONDAY:
                return MONDAY;
            case Calendar.TUESDAY:
                return TUESDAY;
            case Calendar.WEDNESDAY:
                return WEDNESDAY;
            case Calendar.THURSDAY:
                return THURSDAY;
            case Calendar.FRIDAY:
                return FRIDAY;
            case Calendar.SATURDAY:
                return SATURDAY;
            case Calendar.SUNDAY:
                return SUNDAY;
        }
        return -1;
    }


    private static void enableAlarmInternal(final Context context,
                                            final AlarmItem alarm, boolean enabled) {
        ContentResolver resolver = context.getContentResolver();

        ContentValues values = new ContentValues();
        values.put(AlarmsTable.COLUMN_ENABLE, enabled ? 1 : 0);




        resolver.update(ContentUris.withAppendedId(AlarmsContentProvider.CONTENT_URI, alarm.getId()), values, null, null);
    }



    public static AlarmItem getAlarmFromCursor(Cursor alarmsCursor) {
        AlarmItem alarmItem = null;
        int id = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_ID));
        int hour = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_TIME_HOUR));
        int min = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_TIME_MIN));
        int weeks = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_DAYS_OF_WEEK));
        int alertTime = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_ALARM_TIME));
        int enable = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_ENABLE));
        int alertType = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_ALERT_TYPE));
        String title = alarmsCursor.getString(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_TITLE));
        int snooze = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_SNOOZE));
        String alert = alarmsCursor.getString(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_ALERT));
        String ringName = alarmsCursor.getString(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_RING_NAME));
        int volume = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_VOLUME));
        int vibrate = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_VIBRATE));
        String backGround = alarmsCursor.getString(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_BACK_GROUND));
        if (hour>=0 && hour<24 && min>=0 && min<60) {
            TimeInDay time = new TimeInDay(hour, min);
            alarmItem = new AlarmItem(
                    id,
                    time,
                    weeks,
                    alertTime,
                    enable,
                    alertType,
                    title,
                    snooze,
                    alert,
                    ringName,
                    volume,
                    vibrate,
                    backGround
            );

        } else {
            Log.i(Alarms.class.getName(), "Hour or Min from database is invalid hour=" + hour + " min=" + min);
        }

        return alarmItem;


    }


    //private method to get a more limited set of alarms from the database
    private static Cursor getFilterAlarmsCursor(ContentResolver contentResolver) {
        return contentResolver.query(AlarmsContentProvider.CONTENT_URI, AlarmsTable.ALARM_QUERY_COLUMNS, AlarmsTable.WHERE_ENABLE, null, null);
    }


}
