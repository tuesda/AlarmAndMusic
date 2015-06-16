package com.example.root.otherComponent;

import android.app.AlarmManager;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.root.alarmModel.AlarmItem;
import com.example.root.alarmModel.Alarms;
import com.example.root.alarmModel.AlarmsContentProvider;
import com.example.root.alarmModel.AlarmsTable;
import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;
import com.example.root.scroll.TimeInDay;

import java.util.ArrayList;
import java.util.Calendar;
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
    private ImageView btnEdit;
    private ListView alarmListV;
    AlarmListAdapter alarmsAdapter;

    private Handler alarmsHandler;


    private HashMap<RelativeLayout, Button> deleteBtnCache;


    // indicating first time load data from database
    private boolean isFirstLoad = true;




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
        btnEdit = (ImageView)alarmListLayout.findViewById(R.id.btn_edit);
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

        refreshBackgroundColor();

    }






    private void fillWithDB() {
        final List<AlarmItem> alarmsData = new ArrayList<AlarmItem>();
        alarmsCursor.moveToFirst();
        while (alarmsCursor.isAfterLast() == false) {
            AlarmItem alarmItem = Alarms.getAlarmFromCursor(alarmsCursor);
            alarmsData.add(alarmItem);
            alarmsCursor.moveToNext();
        }
        if (alarms == null) {
            alarms = alarmsData;
        } else {
            alarms.clear();
            alarms.addAll(alarmsData);
        }
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

        refreshBackgroundColor();
        if (isFirstLoad) {
            initAlarmsListV();
            isFirstLoad = false;
        } else {
            alarmsAdapter.notifyDataSetChanged();
        }

        alarmListV.setVisibility(View.VISIBLE);
    }



    private void refreshBackgroundColor() {
        if (alarms!=null && alarms.size()>0) {
            // store background color of alarms[0]
            TimeInDay firstTime = alarms.get(0).getTimeInDay();
            SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.PREFERENCES, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(MainActivity.BACKGROUND_COLOR, ViewColorGenerator.getMiddleColor(firstTime));
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.set(Calendar.HOUR_OF_DAY, firstTime.getHour());
            c.set(Calendar.MINUTE, firstTime.getMin());
            editor.putLong(MainActivity.CURRENT_CLOCK, c.getTimeInMillis());
            editor.putInt(MainActivity.FIRST_ID, alarms.get(0).getId());
            editor.apply();
        }
        if (alarms!=null && alarms.size()==0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.PREFERENCES, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(MainActivity.BACKGROUND_COLOR);
            editor.remove(MainActivity.CURRENT_CLOCK);
            editor.remove(MainActivity.FIRST_ID);
            editor.apply();
        }
    }



    private void initAlarmsListV() {
        alarmsAdapter = new AlarmListAdapter(alarms, context);
        alarmListV.setAdapter(alarmsAdapter);
        alarmListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlarmItem alarm = alarms.get(position);
                Message msg = Message.obtain(alarmsHandler, MainActivity.MESSAGE_START_LANDSCAPE, alarm);
                msg.sendToTarget();
//                Log.d("animation", "click time  " + System.currentTimeMillis());

            }
        });



        alarmListV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final View tmpView = view;
                final Button deleteBtn = new Button(context);
                deleteBtn.setText("Delete");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                final int alarmId = ((AlarmListAdapter.AlarmViewHolder) view.getTag()).id;
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View btnView) {
//                        Toast.makeText(context, "delete button clicked id: " + alarmId, Toast.LENGTH_SHORT).show();
                        context.getContentResolver().delete(AlarmsContentProvider.CONTENT_URI, "_id=?", new String[]{String.valueOf(alarmId)});
                        ((RelativeLayout) tmpView).removeView(deleteBtn);
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
                        deleteBtnCache.remove(entry.getKey());
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (alarms.size() > 0) {
                    alarmListLayout.setBackgroundColor(ViewColorGenerator.getMiddleColor(alarms.get(firstVisibleItem).getTimeInDay()));
                }
            }
        });
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


    // set the btnEdit onclick listener
    public void setEditOnClickL(View.OnClickListener listener) {
        btnEdit.setOnClickListener(listener);
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
