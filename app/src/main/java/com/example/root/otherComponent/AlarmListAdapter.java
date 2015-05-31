package com.example.root.otherComponent;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.main.alarmandmusic.R;

import java.util.List;

/**
 * Created by zhanglei on 15/5/31.
 */
public class AlarmListAdapter extends BaseAdapter {

    private List<AlarmItem> alarms;
    private Context context;
    private LayoutInflater mInflater;
    Typeface tf;

    public AlarmListAdapter(List<AlarmItem> alarms, Context context) {
        this.alarms = alarms;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        tf = Typeface.createFromAsset(context.getAssets(), "font/custom.ttf");
    }

    @Override
    public int getCount() {
        return alarms.size();
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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlarmViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.alarm_item, viewGroup, false);
            holder = new AlarmViewHolder();
            holder.time = (TextView) view.findViewById(R.id.alarm_time);
            holder.when = (TextView) view.findViewById(R.id.alarm_when_of_day);
            holder.colon = (ImageView) view.findViewById(R.id.alarm_colon);
            holder.enable = (CheckBox) view.findViewById(R.id.alarm_enable);
            view.setTag(holder);
        } else {
            holder = (AlarmViewHolder) view.getTag();
        }
        holder.time.setTypeface(tf);
        holder.when.setTypeface(tf);
        holder.time.setText(alarms.get(i).getTimeInDay().toString());
        holder.when.setText(ViewColorGenerator.getWhenInDay(alarms.get(i).getTimeInDay()));
        GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, alarms.get(i).getBgColors());
        view.setBackground(bg);


        return view;
    }

    class AlarmViewHolder {
        TextView time;
        TextView when;
        ImageView colon;
        CheckBox enable;

    }
}
