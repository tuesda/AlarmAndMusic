package com.example.root.drawerNav;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

/**
 * Created by zhanglei on 15/5/18.
 */
public class LeftDrawerLayout {
    private Context context;
    private LayoutInflater mInflater;
    private ListView leftDrawer;

    public LeftDrawerLayout(Context context, ListView leftDrawer) {
        this.context = context;
        this.leftDrawer = leftDrawer;
        mInflater = LayoutInflater.from(context);
        init();
    }
    private void init() {
        DrawerAdapter drawerAdapter = new DrawerAdapter(5, context);
        leftDrawer.setAdapter(drawerAdapter);

    }
}
