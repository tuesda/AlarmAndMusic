package com.example.root.otherComponent;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by zhanglei on 15/6/9.
 */
public class AlarmItemView extends RelativeLayout
                        implements GestureDetector.OnGestureListener {

    private GestureDetectorCompat mDetector;
    private Context context;

    public AlarmItemView(Context context) {
        this(context, null, 0);
    }

    public AlarmItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlarmItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mDetector = new GestureDetectorCompat(context, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v1, float v2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY) {

        Log.d("gesture debug", "x: " + velocityX + " y: " + velocityY);
        if ((-velocityX) > (2 * Math.abs(velocityY))) {
            Toast.makeText(context, "delete occurred", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
