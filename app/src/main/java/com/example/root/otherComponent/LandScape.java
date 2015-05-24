package com.example.root.otherComponent;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;
import com.example.root.scroll.ScrollLayout;
import com.example.root.scroll.TimeInDay;

/**
 * Created by zhanglei on 15/5/12.
 */
public class LandScape {

    private Context context;
    private RelativeLayout mainActivity;
    private ScrollLayout scrollLayout;
    private LayoutInflater mInflater;

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





    public LandScape(Context context, RelativeLayout mainActivity, ScrollLayout scrollLayout) {
        this.context = context;
        this.mainActivity = mainActivity;
        this.scrollLayout = scrollLayout;
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {




        landscape = (RelativeLayout)mInflater.inflate(R.layout.landscape, null);

        curTimeV = (TextView)landscape.findViewById(R.id.scroll_list_time);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "font/custom.ttf");
        curTimeV.setTypeface(tf);
        whenOfD = (TextView)landscape.findViewById(R.id.when_of_day);
        whenOfD.setTypeface(tf);
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
        checkMon = (CheckBox)landscape.findViewById(R.id.check_mon);
        checkTus = (CheckBox)landscape.findViewById(R.id.check_tues);
        checkWed = (CheckBox)landscape.findViewById(R.id.check_wed);
        checkThur = (CheckBox)landscape.findViewById(R.id.check_thur);
        checkFri = (CheckBox)landscape.findViewById(R.id.check_fri);
        checkSat = (CheckBox)landscape.findViewById(R.id.check_sat);
        checkSun = (CheckBox)landscape.findViewById(R.id.check_sun);


        scrollLayout.setOnTimeChange(new ScrollLayout.OnTimeChange() {
            @Override
            public void elapse(TimeInDay time) {
                refreshBackground(time);
                refreshCurTime(time);
                updateWhenOfD(time);
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
                btnsTimePicker.setVisibility(View.INVISIBLE);
                isBtnsShow = false;
                btnEdit.setOnClickListener(null);
                LandscapeAnimator landscapeAnimator = new LandscapeAnimator();
                landscapeAnimator.downBottom(500);
                landscapeAnimator.upTop(500);
            }
        });




//        scrollLayout.setRefreshLandScape(new ScrollLayout.RefreshLandScape() {
//            @Override
//            public void refreshWindmill(int wallColor, int proofColor, int winColor, int fanColor) {
//                windmillWall.setColorFilter(wallColor);
//                windmillProof.setColorFilter(proofColor);
//                windmillWindow.setColorFilter(winColor);
//                windmillFan.setColorFilter(fanColor);
//            }
//
//            @Override
//            public void refreshWave(int waveColor) {
//                if (waveColor==0) { //0x00000000
//                    wave_3.setVisibility(View.INVISIBLE);
//                    wave_4.setVisibility(View.INVISIBLE);
//                } else {
//                    wave_3.setVisibility(View.VISIBLE);
//                    wave_4.setVisibility(View.VISIBLE);
//                    wave_3.setColorFilter(waveColor);
//                    wave_4.setColorFilter(waveColor);
//                }
//
//            }
//
//            @Override
//            public void refreshMountain(int far, int near) {
//                mountainFar.setColorFilter(far);
//                mountainNear.setColorFilter(near);
//            }
//
//            @Override
//            public void refreshHouse(int front, int side, int window) {
//                houseFront.setColorFilter(front);
//                houseSide.setColorFilter(side);
//                houseWindow.setColorFilter(window);
//            }
//
//            @Override
//            public void setStarsAl(float alaph) {
//                stars.setAlpha(alaph);
//            }
//        });

        RelativeLayout.LayoutParams landParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainActivity.addView(landscape, landParams);





    }

    /**
     * This method used for refresh backsea and backsky color decided by param time
     * @param time
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void refreshBackground(TimeInDay time) {
        ViewColorGenerator.ColorRange colorRange = ViewColorGenerator.getColorRange(time.getHour());
        ViewColorGenerator.ColorRange finalColorRange = ViewColorGenerator.getColorRange(time.getHour()+1 > 23 ? 0 : time.getHour()+1);

        Argb startTop = new Argb(colorRange.getStart());
        Argb startBottom = new Argb(colorRange.getEnd());

        Argb finalTop = new Argb(finalColorRange.getStart());
        Argb finalBottom = new Argb(finalColorRange.getEnd());


        int[] skyColor = getTopBtmColor(startTop, finalTop, startBottom, finalBottom, time);
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, skyColor);
        scrollBackSky.setBackground(gd);

        ViewColorGenerator.ColorRange colorRangeSea = ViewColorGenerator.getSeaColorRange(time.getHour());
        ViewColorGenerator.ColorRange finalColorRangeSea = ViewColorGenerator.getSeaColorRange(time.getHour()+1 > 23 ? 0 : time.getHour() + 1);

        Argb startTopS = new Argb(colorRangeSea.getStart());
        Argb startBottomS = new Argb(colorRangeSea.getEnd());

        Argb finalTopS = new Argb(finalColorRangeSea.getStart());
        Argb finalBottomS = new Argb(finalColorRangeSea.getEnd());

        int[] seaColor = getTopBtmColor(startTopS, finalTopS, startBottomS, finalBottomS, time);


        GradientDrawable seaGd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, seaColor);
        scrollBackSea.setBackground(seaGd);



    }

    /**
     * Help method used by refreshBackground() method
     * @param startTop
     * @param finalTop
     * @param startBottom
     * @param finalBottom
     * @param time
     * @return
     */
    private int[] getTopBtmColor(Argb startTop, Argb finalTop, Argb startBottom, Argb finalBottom, TimeInDay time) {
        int curTopA = ((finalTop.getA() - startTop.getA()) * time.getMin()) / 60 + startTop.getA();
        int curTopR = ((finalTop.getR() - startTop.getR()) * time.getMin()) / 60 + startTop.getR();
        int curTopG = ((finalTop.getG() - startTop.getG()) * time.getMin()) / 60 + startTop.getG();
        int curTopB = ((finalTop.getB() - startTop.getB()) * time.getMin()) / 60 + startTop.getB();

        int top = Color.argb(curTopA, curTopR, curTopG, curTopB);

        int curBtmA = ((finalBottom.getA() - startBottom.getA()) * time.getMin()) / 60 + startBottom.getA();
        int curBtmR = ((finalBottom.getR() - startBottom.getR()) * time.getMin()) / 60 + startBottom.getR();
        int curBtmG = ((finalBottom.getG() - startBottom.getG()) * time.getMin()) / 60 + startBottom.getG();
        int curBtmB = ((finalBottom.getB() - startBottom.getB()) * time.getMin()) / 60 + startBottom.getB();

        int btm = Color.argb(curBtmA, curBtmR, curBtmG, curBtmB);

        return new int[]{top, btm};
    }


    /**
     * refresh view curTimeV
     * @param time
     */
    private void refreshCurTime(TimeInDay time) {
        curTimeV.setText(time.toString());
    }

    /**
     * update view whenOfD
     * @param time
     */
    private void updateWhenOfD(TimeInDay time) {
        int hour = 0;
        if (time!=null) hour = time.getHour();
        switch (hour) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 0:
                whenOfD.setText("Evening");
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                whenOfD.setText("Midnight");
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                whenOfD.setText("Morning");
                break;
            case 12:
                whenOfD.setText("Midday");
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                whenOfD.setText("Afternoon");
                break;
            default:
                whenOfD.setText("Wrong text");

        }
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
         * Responsible for up and folder sky part view
         * @param duration
         */
        void upTop(int duration) {
            folderSky(duration);
            ObjectAnimator folder = ObjectAnimator.ofFloat(stars, "scaleY", 1f, 0.2f);
            ObjectAnimator up = ObjectAnimator.ofFloat(stars, "translationY", 0f, -DensityUtil.dptopx(context,60));
            AnimatorSet stars_ani = new AnimatorSet();
            stars_ani.play(folder).with(up);
            stars_ani.setDuration(duration);
            stars_ani.start();
        }
        void folderSky(int duration) {
            ObjectAnimator folder = ObjectAnimator.ofFloat(scrollBackSky, "scaleY", 1f, 0.2f);
            ObjectAnimator up = ObjectAnimator.ofFloat(scrollBackSky, "translationY", 0f, -DensityUtil.dptopx(context,180));
            ObjectAnimator time = ObjectAnimator.ofFloat(curTimeV, "translationX", 0f, -DensityUtil.dptopx(context, 95));
            ObjectAnimator timeScaleY = ObjectAnimator.ofFloat(curTimeV, "scaleY", 1f, 0.8f);
            ObjectAnimator timeScaleX = ObjectAnimator.ofFloat(curTimeV, "scaleX", 1f, 0.8f);
            ObjectAnimator whenInDay = ObjectAnimator.ofFloat(whenOfD, "translationX", 0f, -DensityUtil.dptopx(context, 140));
            ObjectAnimator colonAni = ObjectAnimator.ofFloat(timeColon, "translationX", 0f, -DensityUtil.dptopx(context, 95));
            AnimatorSet sky = new AnimatorSet();
            sky.play(folder).with(up).with(time).with(timeScaleY).with(timeScaleX).with(whenInDay).with(colonAni);
            sky.setDuration(duration);
            sky.start();
        }

        void downSea(int duration) {
            ObjectAnimator sea = ObjectAnimator.ofFloat(scrollBackSea, "translationY", 0f, DensityUtil.dptopx(context, 170));
            sea.setDuration(duration);
            sea.start();
        }
    }

}
