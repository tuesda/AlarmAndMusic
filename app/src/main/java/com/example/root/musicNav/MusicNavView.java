package com.example.root.musicNav;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhanglei on 15-4-16.
 * Email: zhangleicoding@163.com
 */
public class MusicNavView extends View {

    private Context context;
    private Paint mFillPaint;
    private int addValue;
    private int tmpAddValue;
    private static final int ADD_VALUE_POSITIVE = 5;
    private static final int ADD_VALUE_NAGATIVE = -5;


    private boolean isExpand = false;




    public MusicNavView(Context context) {
        this(context, null, 0);
    }

    public MusicNavView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicNavView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }



    private void init(final Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;

        // mFillPaint setting
        mFillPaint = new Paint();
        mFillPaint.setColor(0xFF4BB390);
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setStrokeWidth(10);
        mFillPaint.setAntiAlias(true);







    }




    RectF oval;
    int width; // width of init view
    int height; // height of init view

    int centerX; // center x of init view
    int centerY; // center y of init view

    int radius; // the radius of circle you will draw

    private boolean isAnimate = false;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("zhanglei", "width: "+getWidth()+" height: "+getHeight());
        width = getWidth();
        height = getHeight();
        centerX = width/2;
        centerY = height/2;


        radius = centerX;

        oval = new RectF(centerX-radius, centerX-radius, centerX+radius, centerX+radius);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i("zhanglei: ", "isAnimate: "+isAnimate+"isExpand: "+isExpand+"radius: "+radius);
//
//        if (!isAnimate && !isExpand) {
//            setVisibility(INVISIBLE);
//        } else {
//            setVisibility(VISIBLE);
//        }

        if (isAnimate) {
            if (tmpAddValue == 0) {
                tmpAddValue = addValue;
            } else {
                tmpAddValue = Math.min(tmpAddValue + addValue, centerX);
            }
            radius += tmpAddValue;
            if ((!isExpand && radius < centerX) || (isExpand && radius > 0)) {

                oval.set(centerX-radius, centerX-radius, centerX+radius, centerX+radius);
                canvas.drawArc(oval, 180, 90, true, mFillPaint);
                invalidate();
            } else {
                radius = Math.min(radius, centerX);
                oval.set(centerX-radius, centerX-radius, centerX+radius, centerX+radius);
                canvas.drawArc(oval, 180, 90, true, mFillPaint);
                isAnimate = false;
                isExpand = !isExpand;
            }
/**********  Below block can be used to perform slow animation for debug*********/
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
 /********************************************************************/

        } else {
            if (isExpand) {
                oval.set(centerX-radius, centerX-radius, centerX+radius, centerX+radius);
                canvas.drawArc(oval, 180, 90, true, mFillPaint);
            } else {
                // do nothing, because isExpand == false, mean that nothing be visible
            }
        }





    }

    // entry from outside, can expand or reduce view
    public void clickButton() {
        tmpAddValue = 0;
        if (isExpand) {
            addValue = ADD_VALUE_NAGATIVE;
            radius = centerX;
        } else {
            addValue = ADD_VALUE_POSITIVE;
            radius = 0;
        }

        Log.i("zhanglei", "in clickButton");
        isAnimate = true;
        invalidate();
    }


    public boolean getExpand() {
        return isExpand;
    }






}
