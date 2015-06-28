package com.example.root.customeView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhanglei on 15/6/22.
 */
public class DayCheckBox extends View {

    private Paint mPaint;
    private Paint mTextPaint;


    private int mWidth;
    private int mHeight;
    // view center x
    private int centerX;
    // View center y
    private int centerY;

    // circle radius
    private int mRadius;

    // indicating if playing animation
    private boolean isPlaying = false;

    private boolean isChecked = false;

    private boolean flagSetPaint = false;
    private String mContentText;
    private Rect mTextBounds;



    private long startTime;
    private long endTime;
    private int duration;
    private int mAniDuration;


    public DayCheckBox(Context context) {
        this(context, null, 0);
    }

    public DayCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);

        mTextPaint = new Paint();
        mTextPaint.setColor(0xffeeeeee);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);

        mTextBounds = new Rect();






        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    initAnimator(mAniDuration == 0 ? 500 : mAniDuration);
                    invalidate();
                }
            }
        });
    }

    private void initAnimator(int ms) {
        duration = ms;
        startTime = System.currentTimeMillis();
        endTime = startTime + ms;
        isPlaying = true;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
            centerX = mWidth/2;
            centerY = mHeight/2;
            mRadius = mWidth * 4 / 9;
            mTextPaint.setTextSize(mRadius * 3 / 4);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, getCenterY(), getRadius(), mPaint);
        if (isPlaying) invalidate();
        if (!isPlaying && !isChecked && mContentText!=null && !mContentText.equals("")) {
            mTextPaint.getTextBounds(mContentText, 0, mContentText.length(), mTextBounds);
            float textW = mTextBounds.width();
            float textH = mTextBounds.height();
            if (mContentText.equals("一")) textH = textW;
            canvas.drawText(mContentText, getWidth()/2 - textW/2, getHeight()/2 + textH/2, mTextPaint);
        }
        if (!isPlaying && isChecked && mContentText!=null && !mContentText.equals("")) {
            mTextPaint.getTextBounds(mContentText, 0, mContentText.length(), mTextBounds);
            float textW = mTextBounds.width();
            float textH = mTextBounds.height();
            if (mContentText.equals("一")) textH = textW;
            canvas.drawText(mContentText, getWidth()/2 - textW/2, getHeight()/4 + textH/2, mTextPaint);
        }
    }

    private float getRadius() {
        long curTime = System.currentTimeMillis();
        float curRadius;
        int halfDuration = duration / 2;
        if (!isPlaying) {
            return isChecked ? mRadius/2 : mRadius;
        }
        if (curTime >= endTime) {
            curRadius = mPaint.getStyle() == Paint.Style.STROKE ? mRadius : mRadius/2;
            isPlaying = false;
            flagSetPaint = !flagSetPaint;
            setChecked(!isChecked());
        } else if (curTime>=startTime && curTime < startTime+halfDuration) {
            int radius = mPaint.getStyle() == Paint.Style.STROKE ? mRadius : mRadius/2;
            curRadius = (1 - (float) (curTime - startTime) / halfDuration)  * radius;

        } else if (curTime>=startTime+halfDuration && curTime < endTime) {
            int radius = mPaint.getStyle() == Paint.Style.STROKE ? mRadius : mRadius/2;
            curRadius = ((float) (curTime - startTime - halfDuration) / halfDuration) * radius;
            if (!flagSetPaint) {
                freshPaint();
            }
        } else {
            curRadius = mRadius;
        }
        return curRadius;
    }

    private int getCenterY() {
        int curY = centerY;
        if (!isPlaying) {
            return isChecked ? centerY * 3 / 2 : centerY;
        } else {
            float ratio = (System.currentTimeMillis()-startTime)/(float)duration;
            ratio = Math.min(ratio, 1);
            ratio = isChecked ? 1-ratio:ratio;
            curY += centerY/2 * ratio;

        }
        return curY;
    }

    //interface to set text in View
    public void setContentText(String s) {
        mContentText = s;
    }



    // public interface to set checked
    public void setChecked(boolean check) {
        isChecked = check;
        if (isChecked) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        invalidate();
    }
    public boolean isChecked() {
        return isChecked;
    }

    // interface to set animation duration
    public void setDuration(int time) {
        mAniDuration = time;
    }

    private void freshPaint() {
        if (mPaint.getStyle() == Paint.Style.STROKE) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        flagSetPaint = !flagSetPaint;
    }
}
