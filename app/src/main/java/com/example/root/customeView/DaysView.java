package com.example.root.customeView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhanglei on 15/6/24.
 */
public class DaysView extends View {

    private Paint mCirclePaint;
    private int mRadius;
    private int mFirstCentreW;
    private int mCentreH;
    private int mWeeks = -1;

    private static final int MONDAY = 1;
    private static final int TUESDAY = 1;
    private static final int WEDNESDAY = 1;
    private static final int THURSDAY = 1;
    private static final int FRIDAY = 1;
    private static final int SATURDAY = 1;
    private static final int SUNDAY = 1;


    public DaysView(Context context) {
        this(context, null, 0);
    }

    public DaysView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DaysView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(0x7fffffff);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);

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
        for (int i=0; i<7; i++) {
            if (mWeeks!=-1) {
                Paint.Style style = invertWeeks(mWeeks, i) ? Paint.Style.FILL : Paint.Style.STROKE;
                mCirclePaint.setStyle(style);
            }
            canvas.drawCircle(centerW, mCentreH, mRadius, mCirclePaint);
            centerW += getWidth()/7;
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
