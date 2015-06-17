package com.example.root.otherComponent;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.alarmModel.AlarmItem;
import com.example.root.alarmModel.Alarms;
import com.example.root.alarmModel.AlarmsContentProvider;
import com.example.root.alarmModel.AlarmsTable;
import com.example.root.main.alarmandmusic.FontUtil;
import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;
import com.example.root.scroll.ScrollLayout;
import com.example.root.scroll.TimeInDay;

import java.util.Calendar;

/**
 * Created by zhanglei on 15/5/12.
 */
public class LandScape {

    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 4;
    private static final int THURSDAY = 8;
    private static final int FRIDAY = 16;
    private static final int SATURDAY = 32;
    private static final int SUNDAY = 64;
    private static final int DAYS_MASK = 127;




    private Context context;
    private RelativeLayout mainActivity;
    private RelativeLayout backOfSky;
    private LayoutInflater mInflater;

    private RelativeLayout landscape;
    // scroll layout
    private ScrollLayout scrollLayout;




    private TextView curTimeV;
    private TextView whenOfD;


    private View scrollBackSky;
    private View scrollBackSea;

    private ImageView timeColon;




    private ImageView windmillWall;
    private ImageView windmillProof;
    private ImageView windmillWindow;
    private ImageView windmillFan;
    private ImageView mountainFar;
    private ImageView mountainNear;
    private ImageView wave_3;
    private ImageView wave_4;
    private ImageView houseFront;
    private ImageView houseSide;
    private ImageView houseWindow;
    private ImageView stars;
    private ImageView btnEdit;
 /*wind_fan animation rotation
     * created by wxk*/
    //private Animation windmillfan_anim;
     /*end*/


    private RelativeLayout btnsTimePicker;
    private Button timeOk;
    private CheckBox checkMon;
    private CheckBox checkTus;
    private CheckBox checkWed;
    private CheckBox checkThur;
    private CheckBox checkFri;
    private CheckBox checkSat;
    private CheckBox checkSun;

    private boolean isBtnsShow = true;

    private TimeInDay curTime = new TimeInDay(0,0);


    // flag indicate if the alarm detail view is first
    private final boolean mIsInitial;

    // current alarm object indicating time from alarm list
    private  AlarmItem initAlarm;


    // alarms handler
    private Handler alarmsHandler;




    public LandScape(Context context, RelativeLayout mainActivity, boolean isFirst, AlarmItem alarm, Handler alarmsHandler) {
        this.context = context;
        this.mainActivity = mainActivity;
        this.mIsInitial = isFirst;
        this.alarmsHandler = alarmsHandler;
        if (!mIsInitial) {
            initAlarm = alarm;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            AlarmItem tmpAlarm = new AlarmItem(0, new TimeInDay(hour, min), 0, 0, 0, 0, "", 0, "", "", 0, 0,  "");
            initAlarm = tmpAlarm;
        }
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {

        scrollLayout = new ScrollLayout(context, mainActivity);
        //if (!mIsInitial) {
            scrollLayout.setItem(initAlarm.getTimeInDay().getListPosition() + 1);

        //}


        landscape = (RelativeLayout)mInflater.inflate(R.layout.landscape, null);
        backOfSky = (RelativeLayout)landscape.findViewById(R.id.back_of_sky);

        curTimeV = (TextView)landscape.findViewById(R.id.scroll_list_time);
        curTimeV.setTypeface(FontUtil.getDefaultFont(context));
        whenOfD = (TextView)landscape.findViewById(R.id.when_of_day);
        whenOfD.setTypeface(FontUtil.getDefaultFont(context));
        whenOfD.setText("Midnight");

        timeColon = (ImageView)landscape.findViewById(R.id.colon);

        scrollBackSky = landscape.findViewById(R.id.scroll_back_sky);
        scrollBackSea = landscape.findViewById(R.id.scroll_back_sea);



        windmillWall = (ImageView)landscape.findViewById(R.id.windmill_wall);
        windmillProof = (ImageView)landscape.findViewById(R.id.windmill_proof);
        windmillWindow = (ImageView)landscape.findViewById(R.id.windmill_window);
        windmillFan = (ImageView)landscape.findViewById(R.id.wind_fan);

        mountainFar = (ImageView)landscape.findViewById(R.id.mountain_far);
        mountainNear = (ImageView)landscape.findViewById(R.id.mountain_near);

        wave_3 = (ImageView)landscape.findViewById(R.id.wave_3);
        wave_4 = (ImageView)landscape.findViewById(R.id.wave_4);

        houseFront = (ImageView)landscape.findViewById(R.id.house_front);
        houseSide = (ImageView)landscape.findViewById(R.id.house_side);
        houseWindow = (ImageView)landscape.findViewById(R.id.house_window);

        stars = (ImageView)landscape.findViewById(R.id.stars);

        btnEdit = (ImageView)landscape.findViewById(R.id.btn_edit);

        /*animation rotation
         * create by wxk*/
        //windmillfan_anim= AnimationUtils.loadAnimation(landscape.getContext(), R.anim.windrotate);
        //windmillFan.startAnimation(windmillfan_anim);
        /*end*/


        btnsTimePicker = (RelativeLayout)landscape.findViewById(R.id.btns_pick_time);
        timeOk = (Button)landscape.findViewById(R.id.btn_time_ok);
        timeOk.setTypeface(FontUtil.getDefaultFont(context));
        checkMon = (CheckBox)landscape.findViewById(R.id.check_mon);
        checkTus = (CheckBox)landscape.findViewById(R.id.check_tues);
        checkWed = (CheckBox)landscape.findViewById(R.id.check_wed);
        checkThur = (CheckBox)landscape.findViewById(R.id.check_thur);
        checkFri = (CheckBox)landscape.findViewById(R.id.check_fri);
        checkSat = (CheckBox)landscape.findViewById(R.id.check_sat);
        checkSun = (CheckBox)landscape.findViewById(R.id.check_sun);
        if (!mIsInitial) {
            int weeks = initAlarm.getWeeks();
            if ((MONDAY & weeks) == MONDAY) { checkMon.setChecked(true);}
            if ((TUESDAY & weeks) == TUESDAY) { checkTus.setChecked(true);}
            if ((WEDNESDAY & weeks) == WEDNESDAY) { checkWed.setChecked(true);}
            if ((THURSDAY & weeks) == THURSDAY) { checkThur.setChecked(true);}
            if ((FRIDAY & weeks) == FRIDAY) { checkFri.setChecked(true);}
            if ((SATURDAY & weeks) == SATURDAY) { checkSat.setChecked(true);}
            if ((SUNDAY & weeks) == SUNDAY) { checkSun.setChecked(true);}

        }


        scrollLayout.setOnTimeChange(new ScrollLayout.OnTimeChange() {
            @Override
            public void elapse(TimeInDay time) {
                curTime = time;
                refreshBackground(time);
                refreshCurTime(time);
                whenOfD.setText(ViewColorGenerator.getWhenInDay(time));
                refreshWindmill(time);
                refreshWave(time);
                refreshMountain(time);
                refreshHouse(time);
            }
        });




        scrollLayout.setOnFanChange(new ScrollLayout.OnFanChange() {
            @Override
            public void change(int item) {
                final int curAngle = (item % 60) * 6;
                windmillFan.setRotation(curAngle);

            }
        });

        scrollLayout.setOnSwipListener(new ScrollLayout.OnSwipListener() {
            @Override
            public void setVisible() {
                if (isBtnsShow) {
                    btnsTimePicker.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void setInvisible() {
                btnsTimePicker.setVisibility(View.INVISIBLE);
            }
        });

        timeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAlarm();


                btnsTimePicker.setVisibility(View.INVISIBLE);
                isBtnsShow = false;
                SharedPreferences sharedPreference = context.getSharedPreferences(MainActivity.PREFERENCES, 0);
                int defaultColor = ViewColorGenerator.getMiddleColor(curTime);
                int backgroundColor = sharedPreference.getInt(MainActivity.BACKGROUND_COLOR, defaultColor);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                c.set(Calendar.HOUR_OF_DAY, curTime.getHour());
                c.set(Calendar.MINUTE, curTime.getMin());
                if (c.getTimeInMillis() < sharedPreference.getLong(MainActivity.CURRENT_CLOCK, Long.MAX_VALUE) || initAlarm.getId() == sharedPreference.getInt(MainActivity.FIRST_ID, -1)) {
                    backgroundColor = defaultColor;
                }
                backOfSky.setBackgroundColor(backgroundColor);
//                btnEdit.setOnClickListener(null);

                LandscapeAnimator landscapeAnimator = new LandscapeAnimator();
                landscapeAnimator.downBottom(500);

                int[] phonesizes = getPhoneSize();
                int btmMargin = phonesizes[1] - DensityUtil.dptopx(context, 100 + 40);
                int topMargin = DensityUtil.dptopx(context, 40);


                landscapeAnimator.upTop(500, btmMargin, topMargin);
            }
        });








        RelativeLayout.LayoutParams landParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainActivity.addView(landscape, landParams);


        landscape.post(new Runnable() {
            @Override
            public void run() {
                if (!mIsInitial) {
                    AlarmDetailExpand expand = new AlarmDetailExpand(context, landscape);
                    expand.upBottom(500);
                    int[] phonesizes = getPhoneSize();
                    int btmMargin = phonesizes[1] - DensityUtil.dptopx(context, 100 + 40);
                    int topMargin = DensityUtil.dptopx(context, 40);


                    expand.downTop(500, btmMargin, topMargin);
                }
            }
        });


    }

    /**
     * save current state into database
     */
    private void saveAlarm() {

        // get weeks
        int weeks = 0;
        if (checkMon.isChecked()) { weeks |= MONDAY; }
        if (checkTus.isChecked()) { weeks |= TUESDAY; }
        if (checkWed.isChecked()) { weeks |= WEDNESDAY; }
        if (checkThur.isChecked()) { weeks |= THURSDAY; }
        if (checkFri.isChecked()) { weeks |= FRIDAY; }
        if (checkSat.isChecked()) { weeks |= SATURDAY; }
        if (checkSun.isChecked()) { weeks |= SUNDAY; }


        // get alarmtime
        int alarmTime = 5;

        // get enable
        int enable = 1;

        // get alertType
        int alertType = 0;

        // get title
        String title = "Get up!";

        // get snooze
        int snooze = 0;

        // get alert
        String alert = "default";

        // get ringName
        String ringName = "ring";

        // get volume
        int volume = 5;

        // get vibrate
        int vibrate = 0;

        // get backGround
        String backGround = "default";



        Alarms.saveAlarm(context, mIsInitial, initAlarm.getId(), curTime.getHour(), curTime.getMin(), weeks, alarmTime,
                enable, alertType, title, snooze, alert, ringName, volume, vibrate, backGround);
    }






    /**
     * return a series of number represents for phone screen size
     * @return
     */
    private int[] getPhoneSize() {
        Rect rectangle= new Rect();
        Window window= ((MainActivity)context).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight= rectangle.top;
        int contentViewTop=
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight= contentViewTop - statusBarHeight;
        int height = rectangle.height();

//        Log.i("zhanglei-size", "StatusBar Height= " + statusBarHeight + ", height = " + height + ",TitleBar Height = " + titleBarHeight);
        int[] sizes = {statusBarHeight, height, titleBarHeight};
        return sizes;
    }


    /**
     * This method used for refresh backsea and backsky color decided by param time
     * @param time
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void refreshBackground(TimeInDay time) {

        int[] skyColor = ViewColorGenerator.getTopBtmColor(time);
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, skyColor);
        scrollBackSky.setBackground(gd);

        int[] seaColor = ViewColorGenerator.getTopBtmColor(time);

        GradientDrawable seaGd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, seaColor);
        scrollBackSea.setBackground(seaGd);



    }




    /**
     * refresh view curTimeV
     * @param time
     */
    private void refreshCurTime(TimeInDay time) {
        curTimeV.setText(time.toString());
    }



    /**
     * Used for refresh the windmill color: wall proof window fans
     * @param time
     */
    private void refreshWindmill(TimeInDay time) {
        Argb wallStart = new Argb(ViewColorGenerator.getWindmillWallC(time.getHour()));
        Argb wallEnd = new Argb(ViewColorGenerator.getWindmillWallC(time.getHour()+1>23 ? 0 : time.getHour() + 1));
        int curWall = getCurColor(wallStart, wallEnd, time);
        windmillWall.setColorFilter(curWall);

        Argb proofStart = new Argb(ViewColorGenerator.getWindmillProofC(time.getHour()));
        Argb proofEnd = new Argb(ViewColorGenerator.getWindmillProofC(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));
        int curProof = getCurColor(proofStart, proofEnd, time);
        windmillProof.setColorFilter(curProof);

        Argb windowStart = new Argb(ViewColorGenerator.getWindmillWinColor(time.getHour()));
        Argb windowEnd = new Argb(ViewColorGenerator.getWindmillWinColor(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));
        int curWindow = getCurColor(windowStart, windowEnd, time);
        windmillWindow.setColorFilter(curWindow);

        Argb fanStart = new Argb(ViewColorGenerator.getWindmillFanColor(time.getHour()));
        Argb fanEnd = new Argb(ViewColorGenerator.getWindmillFanColor(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));
        int curFan = getCurColor(fanStart, fanEnd, time);
        windmillFan.setColorFilter(curFan);

    }


    /**
     * Used for refresh the wave views
     * @param time
     */
    private void refreshWave(TimeInDay time) {
        Argb waveStart = new Argb(ViewColorGenerator.getWave(time.getHour()));
        Argb waveEnd = new Argb(ViewColorGenerator.getWave(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));
        int curWave = getCurColor(waveStart, waveEnd, time);
        if (time.getHour() == 2 || time.getHour()==22) {
            curWave = 0; // 0x00000000
        }
        if (curWave==0) { //0x00000000
            wave_3.setVisibility(View.INVISIBLE);
            wave_4.setVisibility(View.INVISIBLE);
        } else {
            wave_3.setVisibility(View.VISIBLE);
            wave_4.setVisibility(View.VISIBLE);
            wave_3.setColorFilter(curWave);
            wave_4.setColorFilter(curWave);
        }
    }

    /**
     * Used for refresh mountain view color
     * @param time
     */
    private void refreshMountain(TimeInDay time) {
        Argb farStart = new Argb(ViewColorGenerator.getMountainFarColor(time.getHour()));
        Argb farEnd = new Argb(ViewColorGenerator.getMountainFarColor(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));
        int curFar = getCurColor(farStart, farEnd, time);
        mountainFar.setColorFilter(curFar);

        Argb nearStart = new Argb(ViewColorGenerator.getMountainNearColor(time.getHour()));
        Argb nearEnd = new Argb(ViewColorGenerator.getMountainNearColor(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));
        int curNear = getCurColor(nearStart, nearEnd, time);
        mountainNear.setColorFilter(curNear);
    }

    /**
     * Used for refresh House view color
     * @param time
     */
    private void refreshHouse(TimeInDay time) {
        Argb frontStart = new Argb(ViewColorGenerator.getHouseFron(time.getHour()));
        Argb frontEnd = new Argb(ViewColorGenerator.getHouseFron(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));
        int curFront = getCurColor(frontStart, frontEnd, time);
        houseFront.setColorFilter(curFront);

        Argb sideStart = new Argb(ViewColorGenerator.getHouseSideColor(time.getHour()));
        Argb sideEnd = new Argb(ViewColorGenerator.getHouseSideColor(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));
        int curSide = getCurColor(sideStart, sideEnd, time);
        houseSide.setColorFilter(curSide);

        Argb winStart = new Argb(ViewColorGenerator.getHouseWindowC(time.getHour()));
        Argb winEnd = new Argb(ViewColorGenerator.getHouseWindowC(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));
        int curWin = getCurColor(winStart, winEnd, time);
        houseWindow.setColorFilter(curWin);
    }


    /**
     * Help method used for get cur color for general view color which no gradient
     * @param start
     * @param end
     * @param time
     * @return
     */
    private int getCurColor(Argb start, Argb end, TimeInDay time) {
        int curA = start.getA() + ((end.getA() - start.getA()) * time.getMin()) / 60;
        int curR = start.getR() + ((end.getR() - start.getR()) * time.getMin()) / 60;
        int curG = start.getG() + ((end.getG() - start.getG()) * time.getMin()) / 60;
        int curB = start.getB() + ((end.getB() - start.getB()) * time.getMin()) / 60;
        return Color.argb(curA, curR, curG, curB);
    }




    public void setEditOnClickL(View.OnClickListener listener) {
        btnEdit.setOnClickListener(listener);
    }

    public void setBtnEditVisible(int visible) {
        btnEdit.setVisibility(visible);
    }

    public TimeInDay getCurTime() {
        return curTime;
    }



    class LandscapeAnimator {
        void downWindmill(int duration) {
            ObjectAnimator wall = ObjectAnimator.ofFloat(windmillWall, "translationY", 0f, DensityUtil.dptopx(context, 170));
            windmillFan.clearAnimation();
            ObjectAnimator fan = ObjectAnimator.ofFloat(windmillFan, "translationY", 0f, DensityUtil.dptopx(context, 170));
            ObjectAnimator proof = ObjectAnimator.ofFloat(windmillProof, "translationY", 0f, DensityUtil.dptopx(context, 170));
            ObjectAnimator window = ObjectAnimator.ofFloat(windmillWindow, "translationY", 0f, DensityUtil.dptopx(context, 170));
            AnimatorSet windmill = new AnimatorSet();
            windmill.play(wall).with(fan).with(proof).with(window);

            windmill.setDuration(duration);
            windmill.start();

        }
        void downWave(int duration) {
            ObjectAnimator wave3 = ObjectAnimator.ofFloat(wave_3, "translationY", 0f, DensityUtil.dptopx(context, 170));
            ObjectAnimator wave4 = ObjectAnimator.ofFloat(wave_4, "translationY", 0f, DensityUtil.dptopx(context, 170));
            AnimatorSet wave = new AnimatorSet();
            wave.play(wave3).with(wave4);
            wave.setDuration(duration);
            wave.start();
        }
        void downMountains(int duration) {
            ObjectAnimator far = ObjectAnimator.ofFloat(mountainFar, "translationY", 0f, DensityUtil.dptopx(context, 170));
            ObjectAnimator near = ObjectAnimator.ofFloat(mountainNear, "translationY", 0f, DensityUtil.dptopx(context, 170));
            AnimatorSet mountains = new AnimatorSet();
            mountains.play(far).with(near);
            mountains.setDuration(duration);
            mountains.start();
        }

        void downHouse(int duration) {
            ObjectAnimator front = ObjectAnimator.ofFloat(houseFront, "translationY", 0f, DensityUtil.dptopx(context, 170));
            ObjectAnimator side = ObjectAnimator.ofFloat(houseSide, "translationY", 0f, DensityUtil.dptopx(context, 170));
            ObjectAnimator window = ObjectAnimator.ofFloat(houseWindow, "translationY", 0f, DensityUtil.dptopx(context, 170));
            AnimatorSet house = new AnimatorSet();
            house.play(front).with(side).with(window);
            house.setDuration(duration);
            house.start();
        }

        /**
         * Interface used by outside this class
         * Responsible for down Sea view and views related to sea
         * @param duration
         */
        void downBottom(int duration) {
            downWindmill(duration);
            downHouse(duration);
            downMountains(duration);
            downWave(duration);
            downSea(duration);
        }

        /**
         * Interface used by outside this class
         * Responsible for up and folder sky part view
         * @param duration
         * @return return the top margin
         */
        int upTop(int duration, int lastBtm, int lastTop) {
            int btmMargin = lastBtm;

            folderSky(duration, lastBtm, lastTop);
            ObjectAnimator folder = ObjectAnimator.ofFloat(stars, "scaleY", 1f, 0.2f);
            ObjectAnimator up = ObjectAnimator.ofFloat(stars, "translationY", 0f, -DensityUtil.dptopx(context,60));
            AnimatorSet stars_ani = new AnimatorSet();
            stars_ani.play(folder).with(up);
            stars_ani.setDuration(duration);
            stars_ani.start();

            return btmMargin;
        }
        int folderSky(int duration, int btmMargin, int topMargin) {
            stars.setVisibility(View.INVISIBLE);
            AnimatorSet skyAnim = getSkyAnim(btmMargin);
            AnimatorSet timeAnim = getTimeAnim(topMargin);
            AnimatorSet colonAnim = getColonAnim(topMargin);
            AnimatorSet whenAnim = getWhenAnim(topMargin);

            AnimatorSet sky = new AnimatorSet();
            sky.play(skyAnim).with(timeAnim).with(whenAnim).with(colonAnim);

            sky.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    Message msg = Message.obtain(alarmsHandler, MainActivity.MESSAGE_START_ALARM_LIST);
                    msg.sendToTarget();

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            sky.setDuration(duration);
            sky.start();

            return (int) (scrollBackSky.getHeight() * 2.0);
        }

        private AnimatorSet getSkyAnim(int btmMargin) {
            RelativeLayout.LayoutParams skyParams = (RelativeLayout.LayoutParams)scrollBackSky.getLayoutParams();
            //ObjectAnimator folder = ObjectAnimator.ofFloat(scrollBackSky, "scaleY", 1f, 0.2f);
            ValueAnimator animSkyHeight = ValueAnimator.ofInt(scrollBackSky.getMeasuredHeight(), DensityUtil.dptopx(context, 100));
            //Toast.makeText(context, "init height:" + skyParams.height, Toast.LENGTH_LONG).show();
            animSkyHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollBackSky.getLayoutParams();
                    params.height = val;
                    scrollBackSky.setLayoutParams(params);
                }
            });


            // ObjectAnimator up = ObjectAnimator.ofFloat(scrollBackSky, "translationY", 0f, -DensityUtil.dptopx(context,180));

            ValueAnimator animSkyMarginB = ValueAnimator.ofInt(skyParams.bottomMargin, btmMargin);
            animSkyMarginB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollBackSky.getLayoutParams();
                    params.bottomMargin = val;
                    scrollBackSky.setLayoutParams(params);
                }
            });

            AnimatorSet sky = new AnimatorSet();
            sky.play(animSkyHeight).with(animSkyMarginB);
            return sky;
        }


        /**
         * helper method for generate the current time view animator
         * @param topMargin
         * @return
         */
        private AnimatorSet getTimeAnim(int topMargin) {
            RelativeLayout.LayoutParams timeParams = (RelativeLayout.LayoutParams) curTimeV.getLayoutParams();
            ValueAnimator timeAniW = ValueAnimator.ofInt(curTimeV.getMeasuredWidth(), (int) context.getResources().getDimension(R.dimen.alarm_time_w));
            timeAniW.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) curTimeV.getLayoutParams();
                    params.width = val;
                    curTimeV.setLayoutParams(params);

                }
            });

            ValueAnimator timeAniH = ValueAnimator.ofInt(curTimeV.getMeasuredHeight(), (int) context.getResources().getDimension(R.dimen.alarm_time_h));
            timeAniH.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) curTimeV.getLayoutParams();
                    params.height = val;
                    curTimeV.setLayoutParams(params);

                }
            });

            ValueAnimator timeAniLeft = ValueAnimator.ofInt(timeParams.leftMargin, (int) context.getResources().getDimension(R.dimen.alarm_time_left));
            timeAniLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) curTimeV.getLayoutParams();
                    params.leftMargin = val;
                    curTimeV.setLayoutParams(params);
                }
            });

            ValueAnimator timeAniTop = ValueAnimator.ofInt(timeParams.topMargin, (int) context.getResources().getDimension(R.dimen.alarm_time_top) + topMargin);
            timeAniTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) curTimeV.getLayoutParams();
                    params.topMargin = val;
                    curTimeV.setLayoutParams(params);
                }
            });

            ValueAnimator timeAniText = ValueAnimator.ofInt((int) curTimeV.getTextSize(), (int) context.getResources().getDimension(R.dimen.alarm_time_text));
            timeAniText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    curTimeV.setTextSize(TypedValue.COMPLEX_UNIT_PX, val);
                }
            });

            AnimatorSet timeAni = new AnimatorSet();

            timeAni.play(timeAniW).with(timeAniH).with(timeAniLeft).with(timeAniTop).with(timeAniText);
            return timeAni;
        }

        /**
         * helper method for generate colon animator
         * @param topMargin
         * @return
         */
        private AnimatorSet getColonAnim(int topMargin) {
            RelativeLayout.LayoutParams colonParams = (RelativeLayout.LayoutParams) timeColon.getLayoutParams();

            ValueAnimator colonLeft = ValueAnimator.ofInt(colonParams.leftMargin, (int) context.getResources().getDimension(R.dimen.alarm_colon_left));
            colonLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) timeColon.getLayoutParams();
                    params.leftMargin = val;
                    timeColon.setLayoutParams(params);
                }
            });

            ValueAnimator colonTop = ValueAnimator.ofInt(colonParams.topMargin, (int) context.getResources().getDimension(R.dimen.alarm_colon_top) + topMargin);
            colonTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) timeColon.getLayoutParams();
                    params.topMargin = val;
                    timeColon.setLayoutParams(params);
                }
            });

            AnimatorSet colonAnim = new AnimatorSet();
            colonAnim.play(colonTop).with(colonLeft);
            return colonAnim;
        }

        /**
         * helper method for generate the when textview animator
         * @param topMargin
         * @return
         */
        private AnimatorSet getWhenAnim(int topMargin) {
            RelativeLayout.LayoutParams whenParams = (RelativeLayout.LayoutParams) whenOfD.getLayoutParams();

            ValueAnimator whenTop = ValueAnimator.ofInt(whenParams.topMargin, (int) context.getResources().getDimension(R.dimen.alarm_when_top) + topMargin);
            whenTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)whenOfD.getLayoutParams();
                    params.topMargin = val;
                    whenOfD.setLayoutParams(params);
                }
            });

            ValueAnimator whenLeft = ValueAnimator.ofInt(whenParams.leftMargin, (int) context.getResources().getDimension(R.dimen.alarm_when_left));
            whenLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) whenOfD.getLayoutParams();
                    params.leftMargin = val;
                    whenOfD.setLayoutParams(params);
                }
            });

            ValueAnimator whenText = ValueAnimator.ofInt((int)whenOfD.getTextSize(), (int) context.getResources().getDimension(R.dimen.alarm_when_text));
            whenText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    whenOfD.setTextSize(TypedValue.COMPLEX_UNIT_PX, val);
                }
            });

            AnimatorSet whenAnim = new AnimatorSet();
            whenAnim.play(whenTop).with(whenLeft).with(whenText);
            return whenAnim;
        }


        void downSea(int duration) {
            ObjectAnimator sea = ObjectAnimator.ofFloat(scrollBackSea, "translationY", 0f, DensityUtil.dptopx(context, 170));
            sea.setDuration(duration);
            sea.start();
        }
    }


    public void dispose() {
        mainActivity.removeView(landscape);
        scrollLayout.dispose();
    }



}
