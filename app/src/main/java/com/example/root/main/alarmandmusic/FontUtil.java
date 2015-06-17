package com.example.root.main.alarmandmusic;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by zhanglei on 15/6/17.
 */
public class FontUtil {
    public static Typeface getDefaultFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "font/custom.ttf");
    }
}