package com.example.root.scroll;

import android.graphics.Color;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.root.scroll.ScrollBaseAdapter.NUM_OF_ITEM;

/**
 * Created by zhanglei on 15-4-29.
 * Email: zhangleicoding@163.com
 */
public class ScrollUtils {
    public static ArrayList<Item> getItems(int num) {
        ArrayList<Item> result = new ArrayList<Item>();
        for (int i=0; i<num; i++) {
            float alphaOrBlue = i <= NUM_OF_ITEM/2 ? ((float)i/((float)NUM_OF_ITEM/2)) * 255 :
                    ((float)(NUM_OF_ITEM-i)/(float)(NUM_OF_ITEM-NUM_OF_ITEM/2)) * 255;
            int alOrBl = (int)alphaOrBlue;
            Item item = new Item(i+1, Color.argb(88, 0, 0, alOrBl));
            result.add(item);
        }
        return result;
    }

    public static void refreshCurrentTime(TextView textView, String str) {
        textView.setText(str);
    }
    public static String generateCurrentTime(int input) {
        String result;
        int hours = input / 60;
        hours = hours == 24 ? 0 : hours;
        int mins = input % 60;
        String hourString = hours < 10 ? "0" + String.valueOf(hours) : String.valueOf(hours);
        String minString = mins < 10 ? "0" + String.valueOf(mins) : String.valueOf(mins);
        result = hourString + " : " + minString;
        return result;
    }
}
