package com.example.root.main.alarmandmusic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.root.alarmModel.AlarmItem;
import com.example.root.alarmModel.AlarmSQLiteHelper;
import com.example.root.blurringView.ActivityTest;
import com.example.root.blurringView.PopWindow;
import com.example.root.drawerNav.LeftDrawerLayout;
import com.example.root.musicNav.MusicService;
import com.example.root.otherComponent.AlarmListLayout;
import com.example.root.otherComponent.LandScape;
import com.example.root.scroll.*;

import com.example.root.musicNav.MusicService.*;
import com.example.root.musicNav.*;




public class MainActivity extends Activity {


    public static final int MESSAGE_INIT_ALARM_LIST = 0;
    public static final int MESSAGE_START_LANDSCAPE = 1;
    public static final int MESSAGE_START_ALARM_LIST = 2;
    public static final int MESSAGE_INIT_LANDSCAPE = 4;




    // music backend part
    private MusicUtils musicUtils;
    // music nav view part
    private MusicNavLayout musicNavLayout;

    // service staff start
    private MusicService musicService;
    private Intent serviceIntent;
    private ServiceConnection musicServiceConnection;


    // blurring staff
    private PopWindow popWindow;


    // landscape
    private LandScape landScape;

    private LeftDrawerLayout leftDrawerLayout;
    private boolean isDrawerOpen = false;

    private AlarmListLayout alarmListLayout;

    // handler for switch alarm detail and alarms list
    private Handler alarmsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //debug zone start

        //debug zone end


        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); // lock DrawerLayout so it won't be able to open with gestures

        final RelativeLayout mainActivity = (RelativeLayout)findViewById(R.id.activity_main);




        //landScape = new LandScape(this, mainActivity, true, null);

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


        // drawer start
        final ListView leftDrawer = (ListView)findViewById(R.id.left_drawer);
        leftDrawerLayout = new LeftDrawerLayout(this, leftDrawer);
        // drawer stop


        musicNavLayout = new MusicNavLayout(this, mainActivity);

        popWindow = new PopWindow(mainActivity, this);
//        landScape.setEditOnClickL(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //landScape.setBtnEditVisible(View.INVISIBLE);
//                // popWindow.addToMain();
//                drawerLayout.openDrawer(leftDrawer);
//
//            }
//        });
//        popWindow.setBgOnTouchL(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                popWindow.removeBg();
//                landScape.setBtnEditVisible(View.VISIBLE);
//                return true;
//            }
//        });


//        alarmListLayout = new AlarmListLayout(mainActivity, this);
//        alarmListLayout.setOnItemClickListenter(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                AlarmItem alarmItem = alarmListLayout.getAlarm(position);
//                landScape = new LandScape(MainActivity.this, mainActivity, false, alarmItem);
//                alarmListLayout = null;
//                landScape.setOnTimeIsOkListener(new LandScape.OnTimeIsOkListener() {
//                    @Override
//                    public void goToAlarmsList() {
//                        alarmListLayout = new AlarmListLayout(mainActivity, MainActivity.this);
//                        landScape = null;
//                    }
//                });
//            }
//        });

        alarmsHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_INIT_ALARM_LIST:
                        alarmListLayout = new AlarmListLayout(mainActivity, MainActivity.this, alarmsHandler);
                        break;
                    case MESSAGE_INIT_LANDSCAPE:
                        landScape = new LandScape(MainActivity.this, mainActivity, true, null, alarmsHandler);
                        break;
                    case MESSAGE_START_LANDSCAPE:
                        AlarmItem alarm = (AlarmItem)msg.obj;
                        landScape = new LandScape(MainActivity.this, mainActivity, false, alarm, alarmsHandler);
                        if (alarmListLayout!=null) {
                            alarmListLayout.dispose();
                            alarmListLayout = null;
                        }
                        break;
                    case MESSAGE_START_ALARM_LIST:
                        alarmListLayout = new AlarmListLayout(mainActivity, MainActivity.this, alarmsHandler);
                        if (landScape!=null) {
                            landScape.dispose();
                            landScape = null;
                        }
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "error message", Toast.LENGTH_LONG).show();

                }
            }
        };


        Message msg = Message.obtain(alarmsHandler, MESSAGE_INIT_ALARM_LIST);
        msg.sendToTarget();


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

    public void disposeViews() {
      //  landScape.dispose();
    }


}
