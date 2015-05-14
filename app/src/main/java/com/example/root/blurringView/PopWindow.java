package com.example.root.blurringView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
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
        background = new RelativeLayout(context);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void blur(Bitmap bkg, View view) {
        Log.i("zhanglei", "I am in blur()");
        long start = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 20;
        if (true /*downScale.isChecked()*/) {
            scaleFactor = 8;
            radius = 8;
        }

        Bitmap overlay = Bitmap.createBitmap((int)(view.getMeasuredWidth()/scaleFactor), (int)(view.getMeasuredHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        //paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = BlurringUtil.doBlur(overlay, (int)radius, true);

        view.setBackground(new BitmapDrawable(context.getResources(), overlay));
        Log.i("zhanglei-ms", "time spend: " + (System.currentTimeMillis() - start) + "ms");
    }



    public void setBgOnTouchL(View.OnTouchListener listener) {
        background.setOnTouchListener(listener);
    }

    public void removeBg() {
        mainActivity.removeView(background);
    }










    public boolean addToMain() {
        boolean result = true; // indicate success or not
        final Bitmap main = getScreenShot(mainActivity);

        // background.setBackground(new BitmapDrawable(context.getResources(),main));
        bgLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        centerInPop = (RelativeLayout)mInflater.inflate(R.layout.center_in_pop, null);
        centerLayoutParams = new RelativeLayout.LayoutParams(780, 1200);
        centerLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        background.addView(centerInPop, centerLayoutParams);

        mainActivity.addView(background, bgLayoutParams);
        ObjectAnimator bganimatorX = ObjectAnimator.ofFloat(background, "scaleX", 0f, 1f);
        ObjectAnimator bganimatorY = ObjectAnimator.ofFloat(background, "scaleY", 0f, 1f);

        AnimatorSet bganimator = new AnimatorSet();
        bganimator.play(bganimatorX).with(bganimatorY);
        bganimator.setDuration(20);
        bganimator.start();


        //Log.i("zhanglei-view", "w:" + background.getMeasuredHeight() + "h:" + background.getMeasuredHeight());
        // blur(main, background);
        background.post(new Runnable() {
            @Override
            public void run() {
                blur(main, background);

            }
        });




        return result;
    }

    


    private Bitmap getScreenShot(View view) {
        Bitmap bitmap;
        view.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }
}
