package com.example.root.otherComponent;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.Image;
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
import android.widget.RelativeLayout;

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

        scrollLayout.setOnFanChange(new ScrollLayout.OnFanChange() {
            @Override
            public void change(int item) {
                windmillFan.setRotation(item % 360);
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




        scrollLayout.setRefreshLandScape(new ScrollLayout.RefreshLandScape() {
            @Override
            public void refreshWindmill(int wallColor, int proofColor, int winColor, int fanColor) {
                windmillWall.setColorFilter(wallColor);
                windmillProof.setColorFilter(proofColor);
                windmillWindow.setColorFilter(winColor);
                windmillFan.setColorFilter(fanColor);
            }

            @Override
            public void refreshWave(int waveColor) {
                if (waveColor==0) { //0x00000000
                    wave_3.setVisibility(View.INVISIBLE);
                    wave_4.setVisibility(View.INVISIBLE);
                } else {
                    wave_3.setVisibility(View.VISIBLE);
                    wave_4.setVisibility(View.VISIBLE);
                    wave_3.setColorFilter(waveColor);
                    wave_4.setColorFilter(waveColor);
                }

            }

            @Override
            public void refreshMountain(int far, int near) {
                mountainFar.setColorFilter(far);
                mountainNear.setColorFilter(near);
            }

            @Override
            public void refreshHouse(int front, int side, int window) {
                houseFront.setColorFilter(front);
                houseSide.setColorFilter(side);
                houseWindow.setColorFilter(window);
            }

            @Override
            public void setStarsAl(float alaph) {
                stars.setAlpha(alaph);
            }
        });

        RelativeLayout.LayoutParams landParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainActivity.addView(landscape, landParams);





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

        void downBottom(int duration) {
            downWindmill(duration);
            downHouse(duration);
            downMountains(duration);
            downWave(duration);
            scrollLayout.downSear(duration);
        }

        void upTop(int duration) {
            scrollLayout.folderSky(duration);
            ObjectAnimator folder = ObjectAnimator.ofFloat(stars, "scaleY", 1f, 0.2f);
            ObjectAnimator up = ObjectAnimator.ofFloat(stars, "translationY", 0f, -DensityUtil.dptopx(context,60));
            AnimatorSet stars_ani = new AnimatorSet();
            stars_ani.play(folder).with(up);
            stars_ani.setDuration(duration);
            stars_ani.start();
        }
    }

}
