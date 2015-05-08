package com.example.root.blurringView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by zhanglei on 15-5-3.
 * Email: zhangleicoding@163.com
 */
public class BackgroundOfPop extends RelativeLayout {
    public BackgroundOfPop(Context context) {
        this(context, null, 0);
    }

    public BackgroundOfPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgroundOfPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }
}
