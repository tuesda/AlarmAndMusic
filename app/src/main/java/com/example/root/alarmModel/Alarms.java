package com.example.root.alarmModel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.AlarmClock;

import com.example.root.main.alarmandmusic.Log;
import com.example.root.main.alarmandmusic.MainActivity;
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
    public static final String CANCEL_SNOOZE = "cancel_snooze";

    public static final String AlARM_ID = "alarm_id";

    final static String PREF_SNOOZE_ID = "snooze_id";
    final static String PREF_SNOOZE_TIME = "snooze_time";

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
        if (!enableSnoozeAlert(context)) {
            AlarmItem alarmItem = calculateNextAlert(context);
            if (alarmItem!=null) {


                enableAlert(context, alarmItem, calculateAlarm(alarmItem.getTimeInDay().getHour(), alarmItem.getTimeInDay().getMin(), alarmItem.getWeeks()).getTimeInMillis());
            } else {
                disableAlert(context);
            }
        }
    }



    private static void disableAlert(Context context) {
        AlarmManager am = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 0, new Intent(ALARM_ALERT_ACTION),
                PendingIntent.FLAG_CANCEL_CURRENT);
        am.cancel(sender);
    }


    private static void enableAlert(Context context, final AlarmItem alarmItem, final long atTimeInMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(atTimeInMillis);
        Log.v("alarms_fuck " + c.toString() + "alarm id " + alarmItem.getId());
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ALARM_ALERT_ACTION);
        intent.putExtra(AlARM_ID, alarmItem.getId());


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


        int nowHour = c.get(Calendar.HOUR_OF_DAY);
        int nowMin = c.get(Calendar.MINUTE);

        if (hour < nowHour || (hour==nowHour && min <= nowMin)) {
            c.add(Calendar.DAY_OF_WEEK, 1);
        }


        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);



        int addDays = getNextDaysOfWeek(daysInWeek, c);

//        Log.i("alarms_fuck", "addDays: " + addDays);
        // when user didn't choose the days of week



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
            Log.v("Hour or Min from database is invalid hour=" + hour + " min=" + min);
        }

        return alarmItem;


    }


    //private method to get a more limited set of alarms from the database
    private static Cursor getFilterAlarmsCursor(ContentResolver contentResolver) {
        return contentResolver.query(AlarmsContentProvider.CONTENT_URI, AlarmsTable.ALARM_QUERY_COLUMNS, AlarmsTable.WHERE_ENABLE, null, null);
    }

    public static int getCount(Context context) {
        int result = 0;
        Cursor cursor = context.getContentResolver().query(AlarmsContentProvider.CONTENT_URI, null, null, null, null);
        if (cursor!=null) {
            result = cursor.getCount();
            cursor.close();
        }


        return result;
    }

    public static void saveAlarm(final Context context, final boolean insert, final int id, int hour, int min, int weeks, int alarmTime, int enable,
            int alertType, String title, int snooze, String alert, String ringName,
            int volume, int vibrate, String backGround) {


        // get alarmtime
        if (alarmTime == 0) alarmTime = 5;



        // get title
        if (title == null) title = "Get up!";


        // get alert
        if (alert == null) alert = "default";

        // get ringName
        if (ringName == null) ringName = "ring";



        // get backGround
        if (backGround == null) backGround = "default";


        final ContentValues values = new ContentValues();
        final ContentResolver resolver = context.getContentResolver();

        long time = 0;

        if(Log.LOGV) {
            Log.v("*** saveAlarm * id: " + id + " hour: " + hour + " min: " + min +
                    " enabled: " +  enable + " time: " + time);
        }

        values.put(AlarmsTable.COLUMN_TIME_HOUR, hour);
        values.put(AlarmsTable.COLUMN_TIME_MIN, min);
        values.put(AlarmsTable.COLUMN_DAYS_OF_WEEK, weeks);
        values.put(AlarmsTable.COLUMN_ALARM_TIME, alarmTime);
        values.put(AlarmsTable.COLUMN_ENABLE, enable);
        values.put(AlarmsTable.COLUMN_ALERT_TYPE, alertType);
        values.put(AlarmsTable.COLUMN_TITLE, title);
        values.put(AlarmsTable.COLUMN_SNOOZE, snooze);
        values.put(AlarmsTable.COLUMN_ALERT, alert);
        values.put(AlarmsTable.COLUMN_RING_NAME, ringName);
        values.put(AlarmsTable.COLUMN_VOLUME, volume);
        values.put(AlarmsTable.COLUMN_VIBRATE, vibrate);
        values.put(AlarmsTable.COLUMN_BACK_GROUND, backGround);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (insert) {
                    resolver.insert(AlarmsContentProvider.CONTENT_URI, values);
                } else {
                    resolver.update(ContentUris.withAppendedId(AlarmsContentProvider.CONTENT_URI, id), values, null, null);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                setNextAlert(context);
            }
        }.execute();






    }


    public static AlarmItem getAlarmWithId(Context context, int id) {
        AlarmItem alarm = null;
        if (id == -1) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            alarm = new AlarmItem(0, new TimeInDay(hour, min), 0, 0, 0, 0, "", 0, "", "", 0, 0,  "");
        } else {
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(ContentUris.withAppendedId(AlarmsContentProvider.CONTENT_URI, id), null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                alarm = getAlarmFromCursor(cursor);
            }
        }
        return alarm;
    }



    public static void saveSnoozeAlert(final Context context, final int id,
                                       final long time) {
        SharedPreferences prefs = context.getSharedPreferences(
                MainActivity.PREFERENCES, 0);
        if (id == -1) {
            clearSnoozePreference(context, prefs);
        } else {
            SharedPreferences.Editor ed = prefs.edit();
            ed.putInt(PREF_SNOOZE_ID, id);
            ed.putLong(PREF_SNOOZE_TIME, time);
            ed.commit();
        }
        // Set the next alert after updating the snooze.
        setNextAlert(context);
    }

    // Helper to remove the snooze preference. Do not use clear because that
    // will erase the clock preferences. Also clear the snooze notification in
    // the window shade.
    private static void clearSnoozePreference(final Context context,
                                              final SharedPreferences prefs) {
        final int alarmId = prefs.getInt(PREF_SNOOZE_ID, -1);


        final SharedPreferences.Editor ed = prefs.edit();
        ed.remove(PREF_SNOOZE_ID);
        ed.remove(PREF_SNOOZE_TIME);
        ed.commit();
    }

    /**
     * If there is a snooze set, enable it in AlarmManager
     * @return true if snooze is set
     */
    private static boolean enableSnoozeAlert(final Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                MainActivity.PREFERENCES, 0);

        int id = prefs.getInt(PREF_SNOOZE_ID, -1);
        if (id == -1) {
            return false;
        }
        long time = prefs.getLong(PREF_SNOOZE_TIME, -1);

        // Get the alarm from the db.
        final AlarmItem alarm = getAlarmWithId(context, id);
        // The time in the database is either 0 (repeating) or a specific time
        // for a non-repeating alarm. Update this value so the AlarmReceiver
        // has the right time to compare.
        alarm.setAlertTime((int) time);

        enableAlert(context, alarm, time);
        return true;
    }

    /**
     * Disable the snooze alert if the given id matches the snooze id.
     */
    static void disableSnoozeAlert(final Context context, final int id) {
        SharedPreferences prefs = context.getSharedPreferences(
                MainActivity.PREFERENCES, 0);
        int snoozeId = prefs.getInt(PREF_SNOOZE_ID, -1);
        if (snoozeId == -1) {
            // No snooze set, do nothing.
            return;
        } else if (snoozeId == id) {
            // This is the same id so clear the shared prefs.
            clearSnoozePreference(context, prefs);
        }
    }



    /**
     * Removes an existing Alarm.  If this alarm is snoozing, disables
     * snooze.  Sets next alert.
     */
    public static void deleteAlarm(
            Context context, int alarmId) {

        ContentResolver contentResolver = context.getContentResolver();
        /* If alarm is snoozing, lose it */
        disableSnoozeAlert(context, alarmId);

        Uri uri = ContentUris.withAppendedId(AlarmsContentProvider.CONTENT_URI, alarmId);
        contentResolver.delete(uri, "", null);

        setNextAlert(context);
    }

    public static void enableAlarm(
            final Context context, final int id, boolean enabled) {
        enableAlarmInternal(context, id, enabled);
        setNextAlert(context);
    }

    private static void enableAlarmInternal(final Context context,
                                            final int id, boolean enabled) {
        enableAlarmInternal(context, getAlarmWithId(context, id),
                enabled);
    }

    public static boolean isWeeksRepeat(int weeks) {
        int count = 0;
        if ((weeks & MONDAY) == 1<<0) count++;
        if ((weeks & TUESDAY) == 1<<1) count++;
        if ((weeks & WEDNESDAY) == 1<<2) count++;
        if ((weeks & THURSDAY) == 1<<3) count++;
        if ((weeks & FRIDAY) == 1<<4) count++;
        if ((weeks & SATURDAY) == 1<<5) count++;
        if ((weeks & SUNDAY) == 1<<6) count++;
        if (count > 1) {
            return  true;
        } else {
            return false;
        }

    }

}
