package com.example.root.alarmModel;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by zhanglei on 15/6/1.
 */
public class AlarmsContentProvider extends ContentProvider {

    // database
    private AlarmSQLiteHelper database;

    // Used for Uri matchers
    private static final int ALARMS = 10;
    private static final int ALARM_ID = 20;

    private static final String AUTHORITY = "com.example.root.alarmModel.contentprovider";

    private static final String BASE_PATH = "alarms";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);


    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/alarms";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/alarm";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, ALARMS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ALARM_ID);
    }

    @Override
    public boolean onCreate() {
        database = new AlarmSQLiteHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projections, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        checkColumns(projections);

        queryBuilder.setTables(AlarmsTable.TABLE_ALARMS);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case ALARMS:
                break;
            case ALARM_ID:
                queryBuilder.appendWhere(AlarmsTable.COLUMN_ID + "="
                + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projections, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    private void checkColumns(String[] projections) {
        String[] available = {AlarmsTable.COLUMN_TIME_HOUR, AlarmsTable.COLUMN_TIME_MIN, AlarmsTable.COLUMN_TITLE, AlarmsTable.COLUMN_ID};
        if (projections!=null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projections));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        switch (uriType) {
            case ALARMS:
                id = sqlDB.insert(AlarmsTable.TABLE_ALARMS, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case ALARMS:
                rowsDeleted = sqlDB.delete(AlarmsTable.TABLE_ALARMS, selection, selectionArgs);
                break;
            case ALARM_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(AlarmsTable.TABLE_ALARMS, AlarmsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(AlarmsTable.TABLE_ALARMS, AlarmsTable.COLUMN_ID + "=" + id
                    + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case ALARMS:
                rowsUpdated = sqlDB.update(AlarmsTable.TABLE_ALARMS,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            case ALARM_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(AlarmsTable.TABLE_ALARMS,
                            contentValues,
                            AlarmsTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(AlarmsTable.TABLE_ALARMS,
                            contentValues,
                            AlarmsTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

}
