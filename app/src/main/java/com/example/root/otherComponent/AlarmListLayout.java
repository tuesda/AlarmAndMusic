package com.example.root.otherComponent;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.root.alarmModel.AlarmItem;
import com.example.root.alarmModel.AlarmsContentProvider;
import com.example.root.alarmModel.AlarmsTable;
import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;
import com.example.root.scroll.TimeInDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhanglei on 15/5/31.
 */
public class AlarmListLayout  implements LoaderManager.LoaderCallbacks<Cursor> {
    private Context context;
    private RelativeLayout mainActivity;
    private LayoutInflater mInflater;

    private List<AlarmItem> alarms;
    private RelativeLayout alarmListLayout;
    private ListView alarmListV;
    AlarmListAdapter alarmsAdapter;


    private Cursor alarmsCursor;

    public AlarmListLayout(RelativeLayout mainActivity, Context context) {
        this.mainActivity = mainActivity;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        alarmListLayout = (RelativeLayout) mInflater.inflate(R.layout.alarm_list_layout, null);
        alarmListV = (ListView) alarmListLayout.findViewById(R.id.alarm_list);
        ((MainActivity)context).getLoaderManager().initLoader(0, null, this);

        alarmListV.setDivider(null);
        RelativeLayout.LayoutParams alarmsParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainActivity.addView(alarmListLayout, alarmsParams);

    }




    private void fillWithDB() {
        List<AlarmItem> alarmsData = new ArrayList<AlarmItem>();
        alarmsCursor.moveToFirst();
        while (alarmsCursor.isAfterLast() == false) {
            int hour = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_TIME_HOUR));
            int min = alarmsCursor.getInt(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_TIME_MIN));
            String title = alarmsCursor.getString(alarmsCursor.getColumnIndex(AlarmsTable.COLUMN_TITLE));
            if (hour>=0 && hour<24 && min>=0 && min<60) {
                TimeInDay time = new TimeInDay(hour, min);
                int[] bgColors = ViewColorGenerator.getTopBtmColor(time);
                AlarmItem alarmItem = new AlarmItem(time, bgColors, title);
                alarmsData.add(alarmItem);
            } else {
                Log.i(AlarmListLayout.class.getName(), "Hour or Min from database is invalid hour=" + hour + " min=" + min);
            }
            alarmsCursor.moveToNext();
        }
        alarmsAdapter = new AlarmListAdapter(alarmsData, context);
        alarmListV.setAdapter(alarmsAdapter);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {AlarmsTable.COLUMN_ID, AlarmsTable.COLUMN_TIME_HOUR, AlarmsTable.COLUMN_TITLE, AlarmsTable.COLUMN_TIME_MIN};
        CursorLoader cursorLoader = new CursorLoader(context, AlarmsContentProvider.CONTENT_URI, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // handle the returned cursor
        alarmsCursor = cursor;
        fillWithDB();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // handle the returned cursor as onLoadFinished
        alarmsCursor = null;
    }
}
