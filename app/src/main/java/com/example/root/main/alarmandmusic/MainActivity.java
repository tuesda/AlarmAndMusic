package com.example.root.main.alarmandmusic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.example.root.blurringView.ActivityTest;
import com.example.root.blurringView.PopWindow;
import com.example.root.musicNav.MusicService;
import com.example.root.otherComponent.GlobalWidget;
import com.example.root.scroll.*;

import static com.example.root.scroll.ScrollBaseAdapter.*;
import com.example.root.musicNav.MusicService.*;
import com.example.root.musicNav.*;


import java.util.ArrayList;


public class MainActivity extends Activity {


    // scroll list items
    private ArrayList<Item> itemsData;
    // scroll layout
     private ScrollLayout scrollLayout;

    // music backend part
    private MusicUtils musicUtils;
    // music nav view part
    private MusicNavLayout musicNavLayout;

    // service staff start
    private MusicService musicService;
    private Intent serviceIntent;
    private ServiceConnection musicServiceConnection;

    // setting and edit Button
    private GlobalWidget globalWidget;

    // blurring staff
    private PopWindow popWindow;

    // view switch
//    private ViewSwitchLayout viewSwitchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        RelativeLayout mainActivity = (RelativeLayout)findViewById(R.id.activity_main);

//        Intent testIntent = new Intent(this, ActionBarTabsPager.class);
//        startActivity(testIntent);

//        itemsData = ScrollUtils.getItems(NUM_OF_ITEM);
//        scrollLayout = new ScrollLayout(this, itemsData);

        scrollLayout = new ScrollLayout(this, mainActivity);

        // music backend part of music nav
        musicUtils = new MusicUtils(this);

        if (!musicUtils.getSongsFromDevice()) {
            // work wrong
        } else {
            // work right
        }

        // music service init start
        musicServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicBinder musicBinder = (MusicBinder)service;
                musicService = musicBinder.getMusicService();
                musicService.setSongList(musicUtils.getSongList());
                musicNavLayout.setMusicService(musicService);
                musicNavLayout.addListeners();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        // music service init end


        musicNavLayout = new MusicNavLayout(this, mainActivity);

        globalWidget = new GlobalWidget(mainActivity, this);
        popWindow = new PopWindow(mainActivity, this);
        globalWidget.setSettingOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.addToMain();
            }
        });

        // view switch start
//        viewSwitchLayout = new ViewSwitchLayout(this, mainActivity);


        // view switch end
    }
    // music backend start**************
    @Override
    protected void onStart() {
        super.onStart();
        if (serviceIntent == null) {
            serviceIntent = new Intent(this, MusicService.class);
            bindService(serviceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE);
            startService(serviceIntent);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(musicServiceConnection);

    }
    // music backend stop*****************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
