package com.example.root.blurringView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.root.main.alarmandmusic.R;

/**
 * Created by zhanglei on 15-5-3.
 * Email: zhangleicoding@163.com
 */
public class PopWindow {
    private RelativeLayout mainActivity;
    private Context context;
    private LayoutInflater mInflater;

    private RelativeLayout background;
    private RelativeLayout.LayoutParams bgLayoutParams;
    private RelativeLayout centerInPop;
    private RelativeLayout.LayoutParams centerLayoutParams;

    public PopWindow(RelativeLayout mainActivity, Context context) {
        this.mainActivity = mainActivity;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {

        background = new RelativeLayout(context);
        background.setBackgroundResource(R.color.background_in_pop);
        background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mainActivity.removeView(background);
                return true;
            }
        });

        bgLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        centerInPop = (RelativeLayout)mInflater.inflate(R.layout.center_in_pop, null);
        centerLayoutParams = new RelativeLayout.LayoutParams(780, 1200);
        centerLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        background.addView(centerInPop, centerLayoutParams);

    }










    public boolean addToMain() {
        boolean result = true; // indicate success or not

        mainActivity.addView(background, bgLayoutParams);




        return result;
    }

    public boolean removeFromMain() {
        boolean result = true;
        mainActivity.removeView(background);
        return result;
    }
}
