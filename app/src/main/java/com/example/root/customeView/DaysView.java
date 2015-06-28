package com.example.root.customeView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.root.main.alarmandmusic.R;

/**
 * Created by zhanglei on 15/6/24.
 */
public class DaysView extends View {

    private Paint mCirclePaint;
    private int mRadius;
    private int mFirstCentreW;
    private int mCentreH;
    private int mWeeks = -1;

    private String[] mWeekTexts;
    private String mFullWeeksText;
    private String mOneTime;

    private Rect mBounds;


    public DaysView(Context context) {
        this(context, null, 0);
    }

    public DaysView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DaysView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(0x7fffffff);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setTextSize(25);

        mWeekTexts = context.getResources().getStringArray(R.array.weeks_text);
        mFullWeeksText = context.getResources().getString(R.string.full_weeks);
        mOneTime = context.getResources().getString(R.string.weeks_one_time);
        mBounds = new Rect();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mRadius = getWidth()/28;
            mFirstCentreW = getWidth()/14;
            mCentreH = getHeight() / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerW = mFirstCentreW;

        if (mWeeks == 127) {
            mCirclePaint.getTextBounds(mFullWeeksText, 0, mFullWeeksText.length(), mBounds);
            canvas.drawText(mFullWeeksText, centerW - mBounds.width()/2, mCentreH+mBounds.height()/2, mCirclePaint);
            return;
        }

        if (mWeeks == 0) {
            mCirclePaint.getTextBounds(mOneTime, 0, mOneTime.length(), mBounds);
            canvas.drawText(mOneTime, centerW - mBounds.width()/2, mCentreH + mBounds.height()/2, mCirclePaint);
            return;
        }

        for (int i=0; i<7; i++) {
            String text = mWeekTexts[i];
            if (mWeeks!=-1 && invertWeeks(mWeeks, i)) {
                mCirclePaint.getTextBounds(text, 0, text.length(), mBounds);
                int height = mCentreH + mBounds.height()/2;
                if (i==0) height = mCentreH * 3 / 2;
                canvas.drawText(text, centerW-mBounds.width()/2, height, mCirclePaint);
                centerW += getWidth()/7 * text.length();
            }

        }
    }

    private boolean invertWeeks(int weeks, int index) {
        int mask = 1<<index;
        return (weeks & mask) == mask;
    }


    // interface to set weeks
    public void setWeeks(int weeks) {
        mWeeks = weeks;
        invalidate();
    }
}
