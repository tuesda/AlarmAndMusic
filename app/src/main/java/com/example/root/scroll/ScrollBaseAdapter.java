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

    private int num;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public ScrollBaseAdapter(int num, Context context) {
        this.context = context;
        this.num = num;
        mLayoutInflater = LayoutInflater.from(context);
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
        if (view==null) {
            view = mLayoutInflater.inflate(R.layout.scroll_item, null);
        }
        return view;
    }
}

