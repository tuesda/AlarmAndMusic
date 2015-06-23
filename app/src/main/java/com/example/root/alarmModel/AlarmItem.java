package com.example.root.alarmModel;

import com.example.root.otherComponent.ViewColorGenerator;
import com.example.root.scroll.TimeInDay;

import java.util.Arrays;

/**
 * Created by zhanglei on 15/5/31.
 */
public class AlarmItem {

    private int id;

    private TimeInDay timeInDay;
    private int[] bgColors;
    private int weeks;
    private int alertTime;
    private int enable;
    private int alertType;
    private String title;
    private int snooze;
    private String alert;
    private String ringName;
    private int volume;
    private int vibrate;
    private String backGround;

    public AlarmItem(int id, TimeInDay timeInDay, int weeks, int alertTime, int enable,
                     int alertType, String title, int snooze, String alert, String ringName,
                     int volume, int vibrate, String backGround) {
        this.id = id;
        this.timeInDay = timeInDay;
        this.bgColors = ViewColorGenerator.getTopBtmColor(timeInDay);
        this.weeks = weeks;
        this.alertTime = alertTime;
        this.enable = enable;
        this.alertType = alertType;
        this.title = title;
        this.snooze = snooze;
        this.alert = alert;
        this.ringName = ringName;
        this.volume = volume;
        this.vibrate = vibrate;
        this.backGround = backGround;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public int getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(int alertTime) {
        this.alertTime = alertTime;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getAlertType() {
        return alertType;
    }

    public void setAlertType(int alertType) {
        this.alertType = alertType;
    }

    public int getSnooze() {
        return snooze;
    }

    public void setSnooze(int snooze) {
        this.snooze = snooze;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getRingName() {
        return ringName;
    }

    public void setRingName(String ringName) {
        this.ringName = ringName;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getVibrate() {
        return vibrate;
    }

    public void setVibrate(int vibrate) {
        this.vibrate = vibrate;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    @Override
    public String toString() {
        return "AlarmItem{" +
                "id=" + id +
                ", timeInDay=" + timeInDay +
                ", bgColors=" + Arrays.toString(bgColors) +
                ", weeks=" + weeks +
                ", alertTime=" + alertTime +
                ", enable=" + enable +
                ", alertType=" + alertType +
                ", title='" + title + '\'' +
                ", snooze=" + snooze +
                ", alert='" + alert + '\'' +
                ", ringName='" + ringName + '\'' +
                ", volume=" + volume +
                ", vibrate=" + vibrate +
                ", backGround='" + backGround + '\'' +
                '}';
    }
}
