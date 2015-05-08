package com.example.root.scroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.root.main.alarmandmusic.R;

import java.util.ArrayList;

/**
 * Created by zhanglei on 15-4-26.
 * Email: zhangleicoding@163.com
 */
public class ScrollBaseAdapter extends BaseAdapter {
    public static final int NUM_OF_SCREEN = 32;
    public static final int NUM_OF_ITEM = 1441 + NUM_OF_SCREEN;

    private LayoutInflater mLayoutInflater;
    private ArrayList<Item> itemsData;

    public ScrollBaseAdapter(Context context, ArrayList<Item> itemsData) {
        mLayoutInflater = LayoutInflater.from(context);
        this.itemsData = itemsData;
    }

    @Override
    public int getCount() {
        return NUM_OF_ITEM;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.scroll_item, null);
            viewHolder = new ViewHolder();
            viewHolder.item_text = (TextView)convertView.findViewById(R.id.item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (itemsData != null) {
            // viewHolder.item_text.setText(String.valueOf(itemsData.get(position).getIndex()));
        } else {
            // viewHolder.item_text.setText("error");
        }
        convertView.setBackgroundColor(itemsData.get(position).getColor());


        return convertView;
    }
    public final class ViewHolder {
        public TextView item_text;
    }
}

