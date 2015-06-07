package com.example.root.otherComponent;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;

/**
 * Created by zhanglei on 15/6/7.
 */
public class AlarmDetailExpand {

    private Context context;

    private RelativeLayout landscape;

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



    private RelativeLayout btnsTimePicker;
    private Button timeOk;
    private CheckBox checkMon;
    private CheckBox checkTus;
    private CheckBox checkWed;
    private CheckBox checkThur;
    private CheckBox checkFri;
    private CheckBox checkSat;
    private CheckBox checkSun;

    public AlarmDetailExpand(Context context, RelativeLayout landscape) {
        this.context = context;
        this.landscape = landscape;
        init();
    }

    private void init() {
        curTimeV = (TextView)landscape.findViewById(R.id.scroll_list_time);
        whenOfD = (TextView)landscape.findViewById(R.id.when_of_day);

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



        btnsTimePicker = (RelativeLayout)landscape.findViewById(R.id.btns_pick_time);
        timeOk = (Button)landscape.findViewById(R.id.btn_time_ok);
        checkMon = (CheckBox)landscape.findViewById(R.id.check_mon);
        checkTus = (CheckBox)landscape.findViewById(R.id.check_tues);
        checkWed = (CheckBox)landscape.findViewById(R.id.check_wed);
        checkThur = (CheckBox)landscape.findViewById(R.id.check_thur);
        checkFri = (CheckBox)landscape.findViewById(R.id.check_fri);
        checkSat = (CheckBox)landscape.findViewById(R.id.check_sat);
        checkSun = (CheckBox)landscape.findViewById(R.id.check_sun);
    }

    public static void initReady() {

    }

    void upWindmill(int duration) {
        ObjectAnimator wall = ObjectAnimator.ofFloat(windmillWall, "translationY", DensityUtil.dptopx(context, 170), 0f);
        windmillFan.clearAnimation();
        ObjectAnimator fan = ObjectAnimator.ofFloat(windmillFan, "translationY", DensityUtil.dptopx(context, 170), 0f);
        ObjectAnimator proof = ObjectAnimator.ofFloat(windmillProof, "translationY", DensityUtil.dptopx(context, 170), 0f);
        ObjectAnimator window = ObjectAnimator.ofFloat(windmillWindow, "translationY", DensityUtil.dptopx(context, 170), 0f);
        AnimatorSet windmill = new AnimatorSet();
        windmill.play(wall).with(fan).with(proof).with(window);

        windmill.setDuration(duration);
        windmill.start();

    }
    void upWave(int duration) {
        ObjectAnimator wave3 = ObjectAnimator.ofFloat(wave_3, "translationY", DensityUtil.dptopx(context, 170), 0f);
        ObjectAnimator wave4 = ObjectAnimator.ofFloat(wave_4, "translationY", DensityUtil.dptopx(context, 170), 0f);
        AnimatorSet wave = new AnimatorSet();
        wave.play(wave3).with(wave4);
        wave.setDuration(duration);
        wave.start();
    }
    void upMountains(int duration) {
        ObjectAnimator far = ObjectAnimator.ofFloat(mountainFar, "translationY", DensityUtil.dptopx(context, 170), 0f);
        ObjectAnimator near = ObjectAnimator.ofFloat(mountainNear, "translationY", DensityUtil.dptopx(context, 170), 0f);
        AnimatorSet mountains = new AnimatorSet();
        mountains.play(far).with(near);
        mountains.setDuration(duration);
        mountains.start();
    }

    void upHouse(int duration) {
        ObjectAnimator front = ObjectAnimator.ofFloat(houseFront, "translationY", DensityUtil.dptopx(context, 170), 0f);
        ObjectAnimator side = ObjectAnimator.ofFloat(houseSide, "translationY", DensityUtil.dptopx(context, 170), 0f);
        ObjectAnimator window = ObjectAnimator.ofFloat(houseWindow, "translationY", DensityUtil.dptopx(context, 170), 0f);
        AnimatorSet house = new AnimatorSet();
        house.play(front).with(side).with(window);
        house.setDuration(duration);
        house.start();
    }

    void upSea(int duration) {
        ObjectAnimator sea = ObjectAnimator.ofFloat(scrollBackSea, "translationY", DensityUtil.dptopx(context, 170), 0f);
        sea.setDuration(duration);
        sea.start();
    }

    /**
     * Interface used by outside this class
     * Responsible for down Sea view and views related to sea
     * @param duration
     */
    void upBottom(int duration) {
        upWindmill(duration);
        upHouse(duration);
        upMountains(duration);
        upWave(duration);
        upSea(duration);
    }

    /**
     * Interface used by outside this class
     * Responsible for up and folder sky part view
     * @param duration
     * @return return the top margin
     */
    int downTop(int duration, int lastBtm, int lastTop) {
        int btmMargin = lastBtm;

        expandSky(duration, lastBtm, lastTop);


        return btmMargin;
    }
    int expandSky(int duration, int btmMargin, int topMargin) {
        stars.setVisibility(View.INVISIBLE);
        AnimatorSet skyAnim = getSkyAnim(btmMargin);
        AnimatorSet timeAnim = getTimeAnim(topMargin);
        AnimatorSet colonAnim = getColonAnim(topMargin);
        AnimatorSet whenAnim = getWhenAnim(topMargin);

        AnimatorSet sky = new AnimatorSet();
        sky.play(skyAnim).with(timeAnim).with(whenAnim).with(colonAnim);



        sky.setDuration(duration);
        sky.start();

        return (int) (scrollBackSky.getHeight() * 2.0);
    }

    private AnimatorSet getSkyAnim(int btmMargin) {
        RelativeLayout.LayoutParams skyParams = (RelativeLayout.LayoutParams)scrollBackSky.getLayoutParams();
        ValueAnimator animSkyHeight = ValueAnimator.ofInt(DensityUtil.dptopx(context, 100), scrollBackSky.getMeasuredHeight());
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

        ValueAnimator animSkyMarginB = ValueAnimator.ofInt(btmMargin, skyParams.bottomMargin);
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
        ValueAnimator timeAniW = ValueAnimator.ofInt((int) context.getResources().getDimension(R.dimen.alarm_time_w), curTimeV.getMeasuredWidth());
        timeAniW.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int) valueAnimator.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) curTimeV.getLayoutParams();
                params.width = val;
                curTimeV.setLayoutParams(params);

            }
        });

        ValueAnimator timeAniH = ValueAnimator.ofInt( (int) context.getResources().getDimension(R.dimen.alarm_time_h), curTimeV.getMeasuredHeight());
        timeAniH.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int) valueAnimator.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) curTimeV.getLayoutParams();
                params.height = val;
                curTimeV.setLayoutParams(params);

            }
        });

        ValueAnimator timeAniLeft = ValueAnimator.ofInt((int) context.getResources().getDimension(R.dimen.alarm_time_left), timeParams.leftMargin);
        timeAniLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int) valueAnimator.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) curTimeV.getLayoutParams();
                params.leftMargin = val;
                curTimeV.setLayoutParams(params);
            }
        });

        ValueAnimator timeAniTop = ValueAnimator.ofInt((int) context.getResources().getDimension(R.dimen.alarm_time_top) + topMargin, timeParams.topMargin);
        timeAniTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int) valueAnimator.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) curTimeV.getLayoutParams();
                params.topMargin = val;
                curTimeV.setLayoutParams(params);
            }
        });

        ValueAnimator timeAniText = ValueAnimator.ofInt((int) context.getResources().getDimension(R.dimen.alarm_time_text), (int) curTimeV.getTextSize());
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

        ValueAnimator colonLeft = ValueAnimator.ofInt((int) context.getResources().getDimension(R.dimen.alarm_colon_left), colonParams.leftMargin);
        colonLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int) valueAnimator.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) timeColon.getLayoutParams();
                params.leftMargin = val;
                timeColon.setLayoutParams(params);
            }
        });

        ValueAnimator colonTop = ValueAnimator.ofInt((int) context.getResources().getDimension(R.dimen.alarm_colon_top) + topMargin, colonParams.topMargin);
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

        ValueAnimator whenTop = ValueAnimator.ofInt((int) context.getResources().getDimension(R.dimen.alarm_when_top) + topMargin, whenParams.topMargin);
        whenTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int) valueAnimator.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)whenOfD.getLayoutParams();
                params.topMargin = val;
                whenOfD.setLayoutParams(params);
            }
        });

        ValueAnimator whenLeft = ValueAnimator.ofInt((int) context.getResources().getDimension(R.dimen.alarm_when_left), whenParams.leftMargin);
        whenLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int) valueAnimator.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) whenOfD.getLayoutParams();
                params.leftMargin = val;
                whenOfD.setLayoutParams(params);
            }
        });

        ValueAnimator whenText = ValueAnimator.ofInt((int) context.getResources().getDimension(R.dimen.alarm_when_text), (int) whenOfD.getTextSize());
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



}
