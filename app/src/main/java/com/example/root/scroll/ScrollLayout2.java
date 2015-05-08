package com.example.root.scroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;

import java.util.ArrayList;

/**
 * Created by zhanglei on 15-4-30.
 * Email: zhangleicoding@163.com
 */
public class ScrollLayout2 {

    private TextView currentTimeView;
    private String currentTimeStr = "00 : 00";

    private ArrayList<Item> itemsData;

    private RelativeLayout scrollLayout;

    public ScrollLayout2(RelativeLayout scrollLayout, ArrayList<Item> itemsData) {
        this.itemsData = itemsData;
        this.scrollLayout = scrollLayout;
        init();
    }

    private void init() {
        ListView scrollList = (ListView)scrollLayout.findViewById(R.id.scroll_list_back);

        scrollList.setClickable(false);
        currentTimeView = (TextView)scrollLayout.findViewById(R.id.scroll_list_time);

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
        ScrollBaseAdapter scrollBaseAdapter = new ScrollBaseAdapter(scrollLayout.getContext(), itemsData);
        scrollList.setAdapter(scrollBaseAdapter);

    }

    public View getTimePicker() {
        return scrollLayout;
    }
}
