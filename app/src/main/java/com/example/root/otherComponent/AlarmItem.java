package com.example.root.otherComponent;

import com.example.root.scroll.TimeInDay;

/**
 * Created by zhanglei on 15/5/31.
 */
public class AlarmItem {

    private TimeInDay timeInDay;
    private int[] bgColors;
    private String title;

    public AlarmItem(TimeInDay timeInDay, int[] bgColors, String title) {
        this.timeInDay = timeInDay;
        this.bgColors = bgColors;
        this.title = title;
    }

    public TimeInDay getTimeInDay() {
        return timeInDay;
    }

    public void setTimeInDay(TimeInDay timeInDay) {
        this.timeInDay = timeInDay;
    }

    public int[] getBgColors() {
        return bgColors;
    }

    public void setBgColors(int[] bgColors) {
        this.bgColors = bgColors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
