package com.example.root.otherComponent;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.root.alarmModel.AlarmItem;
import com.example.root.alarmModel.AlarmsContentProvider;
import com.example.root.alarmModel.AlarmsTable;
import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;
import com.example.root.scroll.TimeInDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
    private Button btn_add;
    private ListView alarmListV;
    AlarmListAdapter alarmsAdapter;

    private Handler alarmsHandler;


    private HashMap<RelativeLayout, Button> deleteBtnCache;




    private Cursor alarmsCursor;

    public AlarmListLayout(RelativeLayout mainActivity, Context context, Handler alarmsHandler) {
        this.mainActivity = mainActivity;
        this.context = context;
        this.alarmsHandler = alarmsHandler;
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
        alarmListV.setVisibility(View.INVISIBLE);

        if (((MainActivity)context).getLoaderManager().getLoader(0).isStarted()) {
            alarmListV.setVisibility(View.VISIBLE);
        }
        deleteBtnCache = new HashMap<RelativeLayout, Button>();

        btn_add = (Button)alarmListLayout.findViewById(R.id.add_alarm);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = Message.obtain(alarmsHandler, MainActivity.MESSAGE_INIT_LANDSCAPE);
                msg.sendToTarget();
            }
        });


    }






    private void fillWithDB() {
        final List<AlarmItem> alarmsData = new ArrayList<AlarmItem>();
        alarmsCursor.moveToFirst();
        while (alarmsCursor.isAfterLast() == false) {
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
                AlarmItem alarmItem = new AlarmItem(
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
                alarmsData.add(alarmItem);
            } else {
                Log.i(AlarmListLayout.class.getName(), "Hour or Min from database is invalid hour=" + hour + " min=" + min);
            }
            alarmsCursor.moveToNext();
        }
        alarms = alarmsData;
        Collections.sort(alarms, new Comparator<AlarmItem>() {
            @Override
            public int compare(AlarmItem alarmItem, AlarmItem otherAlarmItem) {
                int hour = alarmItem.getTimeInDay().getHour();
                int min = alarmItem.getTimeInDay().getMin();

                int otherHour = otherAlarmItem.getTimeInDay().getHour();
                int otherMin = otherAlarmItem.getTimeInDay().getMin();

                return hour == otherHour ? (min - otherMin) : (hour - otherHour);
            }
        });
        alarmsAdapter = new AlarmListAdapter(alarmsData, context);
        alarmListV.setAdapter(alarmsAdapter);
        alarmListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlarmItem alarm = alarms.get(position);
                Message msg = Message.obtain(alarmsHandler, MainActivity.MESSAGE_START_LANDSCAPE, alarm);
                msg.sendToTarget();
            }
        });



        alarmListV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final Button deleteBtn = new Button(context);
                deleteBtn.setText("Delete");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                final int alarmId = ((AlarmListAdapter.AlarmViewHolder) view.getTag()).id;
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "delete button clicked id: " + alarmId, Toast.LENGTH_SHORT).show();
                        context.getContentResolver().delete(AlarmsContentProvider.CONTENT_URI, "_id=?", new String[] {String.valueOf(alarmId)});
                    }
                });
                deleteBtnCache.put((RelativeLayout) view, deleteBtn);
                ((RelativeLayout) view).addView(deleteBtn, params);

                return true;
            }
        });


        alarmListV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (!deleteBtnCache.isEmpty()) {
                    for (HashMap.Entry<RelativeLayout, Button> entry : deleteBtnCache.entrySet()) {
                        entry.getKey().removeView(entry.getValue());
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int[] colors = ViewColorGenerator.getTopBtmColor(alarms.get(firstVisibleItem).getTimeInDay());
//                Toast.makeText(context, "red " + Color.red(colors[0]), Toast.LENGTH_LONG).show();
                int start_a = Color.alpha(colors[0]);
                int start_r = Color.red(colors[0]);
                int start_g = Color.green(colors[0]);
                int start_b = Color.blue(colors[0]);

                int end_a = Color.alpha(colors[1]);
                int end_r = Color.red(colors[1]);
                int end_g = Color.green(colors[1]);
                int end_b = Color.blue(colors[1]);

                int current = Color.argb((start_a + end_a) / 2, (start_r + end_r) / 2, (start_g + end_g) / 2, (start_b + end_b) / 2);
                alarmListLayout.setBackgroundColor(current);
            }
        });
        alarmListV.setVisibility(View.VISIBLE);
    }


    /**
     * interface to set listener for item click in listView
     * @param listener
     */
    public void setOnItemClickListenter(AdapterView.OnItemClickListener listener) {
        alarmListV.setOnItemClickListener(listener);
    }

    public AlarmItem getAlarm(int position) {
        return alarms.get(position);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                AlarmsTable.COLUMN_ID,
                AlarmsTable.COLUMN_TIME_HOUR,
                AlarmsTable.COLUMN_TIME_MIN,
                AlarmsTable.COLUMN_DAYS_OF_WEEK,
                AlarmsTable.COLUMN_ALARM_TIME,
                AlarmsTable.COLUMN_ENABLE,
                AlarmsTable.COLUMN_ALERT_TYPE,
                AlarmsTable.COLUMN_TITLE,
                AlarmsTable.COLUMN_SNOOZE,
                AlarmsTable.COLUMN_ALERT,
                AlarmsTable.COLUMN_RING_NAME,
                AlarmsTable.COLUMN_VOLUME,
                AlarmsTable.COLUMN_VIBRATE,
                AlarmsTable.COLUMN_BACK_GROUND
        };
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

    public void dispose() {
        mainActivity.removeView(alarmListLayout);
    }


}
