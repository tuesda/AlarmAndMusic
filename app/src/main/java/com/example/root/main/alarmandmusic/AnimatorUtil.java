package com.example.root.main.alarmandmusic;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * Created by zhanglei on 15/6/22.
 */
public class AnimatorUtil {
    public static AnimatorSet expandView(View view, int duration, boolean expand) {
        float start, end;
        if (expand) {
            start = 0f;
            end = 1f;
        } else {
            start = 1f;
            end = 0f;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", start, end);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", start, end);
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(duration);
        return animatorSet;

    }
}
