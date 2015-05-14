package com.example.root.otherComponent;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;
import com.example.root.scroll.ScrollLayout;

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
 /*wind_fan animation rotation
     * created by wxk*/
    private Animation windmillfan_anim;
     /*end*/


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
        /*animation rotation
         * create by wxk*/
        windmillfan_anim=AnimationUtils.loadAnimation(landscape.getContext(), R.anim.windrotate);
        windmillFan.startAnimation(windmillfan_anim);
        /*end*/

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



}
