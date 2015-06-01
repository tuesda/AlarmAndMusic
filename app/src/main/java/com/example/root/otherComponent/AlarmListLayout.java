package com.example.root.otherComponent;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.root.alarmModel.AlarmItem;
import com.example.root.alarmModel.AlarmsContentProvider;
import com.example.root.alarmModel.AlarmsTable;
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

    public AlarmListLayout(RelativeLayout mainActivity, Context context) {
        this.mainActivity = mainActivity;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        alarmListLayout = (RelativeLayout) mInflater.inflate(R.layout.alarm_list_layout, null);
        alarmListV = (ListView) alarmListLayout.findViewById(R.id.alarm_list);
        alarms = getAlarms(1);
        AlarmListAdapter alarmsAdapter = new AlarmListAdapter(alarms, context);
        alarmListV.setAdapter(alarmsAdapter);
        alarmListV.setDivider(null);
        RelativeLayout.LayoutParams alarmsParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainActivity.addView(alarmListLayout, alarmsParams);

    }


    private List<AlarmItem> getAlarms(int num) {
        List<AlarmItem> alarms = new ArrayList<AlarmItem>();
        Random generator = new Random(8);
        for (int i=0; i<num; i++) {
            TimeInDay time = new TimeInDay(generator.nextInt(24), generator.nextInt(60));
            int[] bgColors = ViewColorGenerator.getTopBtmColor(time);
            AlarmItem alarmItem = new AlarmItem(time, bgColors, "default");
            alarms.add(alarmItem);
        }

        return alarms;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {AlarmsTable.COLUMN_ID, AlarmsTable.COLUMN_TIME_HOUR, AlarmsTable.COLUMN_TIME_MIN};
        CursorLoader cursorLoader = new CursorLoader(context, AlarmsContentProvider.CONTENT_URI, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // handle the returned cursor

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // handle the returned cursor as onLoadFinished
    }
}
