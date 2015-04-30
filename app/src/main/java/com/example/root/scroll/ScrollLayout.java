package com.example.root.scroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;

import java.util.ArrayList;

/**
 * Created by zhanglei on 15-4-30.
 * Email: zhangleicoding@163.com
 */
public class ScrollLayout {
    private Context context;
    private LayoutInflater mInflater;

    private TextView currentTimeView;
    private String currentTimeStr = "00 : 00";

    private ArrayList<Item> itemsData;

    public ScrollLayout(Context context, ArrayList<Item> itemsData) {
        this.context = context;
        this.itemsData = itemsData;
        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(context);
        ListView scrollList = (ListView)((MainActivity)context).findViewById(R.id.scroll_list_view);
        currentTimeView = (TextView)((MainActivity)context).findViewById(R.id.scroll_current_time);

        scrollList.setDivider(null);
        scrollList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                currentTimeStr = ScrollUtils.generateCurrentTime(firstVisibleItem);
                ScrollUtils.refreshCurrentTime(currentTimeView, currentTimeStr);
            }
        });
        ScrollBaseAdapter scrollBaseAdapter = new ScrollBaseAdapter(context, itemsData);
        scrollList.setAdapter(scrollBaseAdapter);

    }
}
