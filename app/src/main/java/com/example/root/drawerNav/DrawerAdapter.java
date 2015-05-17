package com.example.root.drawerNav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.root.main.alarmandmusic.R;

/**
 * Created by zhanglei on 15/5/18.
 */
public class DrawerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private int num;

    private SeekBar timeToSleep;
    private TextView howTimeSleep;

    private TextView leftDrawerT_1;
    private TextView leftDrawerT_2;
    private TextView leftDrawerC_1;
    private TextView leftDrawerC_2;

    private CheckBox leftDrawerCheck_1;
    private CheckBox leftDrawerCheck_2;

    public DrawerAdapter(int num, Context context) {
        this.context = context;
        this.num = num;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return num;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        switch (i) {
            case 0:
                view = mInflater.inflate(R.layout.left_drawer_title, null);
                break;
            case 1:
                view = mInflater.inflate(R.layout.drawer_progress, null);
                timeToSleep = (SeekBar)view.findViewById(R.id.time_to_sleep_seek);
                howTimeSleep = (TextView)view.findViewById(R.id.time_to_sleep_value);
                howTimeSleep.setText(timeToSleep.getProgress() + " mins");
                timeToSleep.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        howTimeSleep.setText(i + " mins");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
            case 2:
                view = mInflater.inflate(R.layout.left_drawer_content, null);
                leftDrawerT_1 = (TextView)view.findViewById(R.id.left_drawer_content_title);
                leftDrawerC_1 = (TextView)view.findViewById(R.id.left_drawer_content);
                leftDrawerCheck_1 = (CheckBox)view.findViewById(R.id.left_drawer_content_check);
                break;
            case 3:
                view = mInflater.inflate(R.layout.left_drawer_content, null);
                leftDrawerT_2 = (TextView)view.findViewById(R.id.left_drawer_content_title);
                leftDrawerC_2 = (TextView)view.findViewById(R.id.left_drawer_content);
                leftDrawerCheck_2 = (CheckBox)view.findViewById(R.id.left_drawer_content_check);
                break;
            case 4:
                view = mInflater.inflate(R.layout.left_drawer_content, null);
                break;
        }
        return view;
    }
}
