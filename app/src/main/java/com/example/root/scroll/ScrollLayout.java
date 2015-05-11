package com.example.root.scroll;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.main.alarmandmusic.R;

/**
 * Created by zhanglei on 15/5/10.
 */
public class ScrollLayout {
    private static final int ITEMS_IN_VISIBLE = 33;
    private static final int ITEMS_IN_DAY = 1440+ITEMS_IN_VISIBLE;

    private Context context;
    private LayoutInflater mInflater;

    private TextView curTimeV;

    private ListView lv;

    private View scrollBackSky;
    private View scrollBackSea;

    private TextView display_debug;

    private RelativeLayout parentLayout;
    private RelativeLayout scrollContainer;

    private boolean isReady = true;

    private Argb beginTop;
    private Argb beginBottom;
    private Argb finalTop;
    private Argb finalBottom;

    private Argb seaBeginTop;
    private Argb seaBeginBottom;
    private Argb seaFinalTop;
    private Argb seaFinalBottom;

    private Argb windmillWallBegin;
    private Argb windmillWallFinal;

    private Argb windmillProofBegin;
    private Argb windmillProofFinal;

    private int hourZone;
    private boolean isHZchanged; // check is the hour zone changed

    private TimeInDay time;


    private RefreshLandScape refreshLandScape;




    public ScrollLayout(Context context, RelativeLayout parentLayout) {
        this.context = context;
        this.parentLayout = parentLayout;
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        scrollContainer = (RelativeLayout)mInflater.inflate(R.layout.scroll_list, null);
        curTimeV = (TextView)scrollContainer.findViewById(R.id.scroll_list_time);

        lv = (ListView)scrollContainer.findViewById(R.id.scroll_list_back);
        lv.setDivider(null);

        scrollBackSky = scrollContainer.findViewById(R.id.scroll_back_sky);
        scrollBackSea = scrollContainer.findViewById(R.id.scroll_back_sea);

        display_debug = (TextView)scrollContainer.findViewById(R.id.display_debug);
        RelativeLayout.LayoutParams scrollParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        parentLayout.addView(scrollContainer, scrollParams);


        ScrollBaseAdapter scrollAdapter = new ScrollBaseAdapter(ITEMS_IN_DAY, context);
        lv.setAdapter(scrollAdapter);

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                display_debug.setText(firstVisibleItem + "/" + (totalItemCount - ITEMS_IN_VISIBLE));
                int curHourZone = firstVisibleItem / 60;
                if ((curHourZone != hourZone || hourZone == 0) && curHourZone < 24) {
                    hourZone = curHourZone;
                    isHZchanged = true;
                } else {
                    isHZchanged = false;
                }
                updateBF(firstVisibleItem); // update begin_top begin_bottom final_top final_bottom

                time = getCurTime(firstVisibleItem);
                curTimeV.setText(time.toString());

                refreshLand(firstVisibleItem);

                if (isReady) {
                    refreshBg(firstVisibleItem);
                }

            }
        });



    }


    public void setRefreshLandScape(RefreshLandScape refreshLandScape) {
        this.refreshLandScape = refreshLandScape;
    }


    private void refreshLand(int firstItem) {

        if (isHZchanged) {
            windmillWallBegin = null;
            windmillWallBegin = new Argb(ScrollColorValue.getWindmillWallC(time.getHour()));
            windmillWallFinal = null;
            windmillWallFinal = new Argb(ScrollColorValue.getWindmillWallC(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));

            windmillProofBegin = null;
            windmillProofBegin = new Argb(ScrollColorValue.getWindmillProofC(time.getHour()));
            windmillProofFinal = null;
            windmillProofFinal = new Argb(ScrollColorValue.getWindmillProofC(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));
        }
        int windmillWallColor = generateColor(firstItem, windmillWallBegin, windmillWallFinal, windmillWallBegin, windmillWallFinal)[0];

        int windmillProofColor = generateColor(firstItem, windmillProofBegin, windmillProofFinal, windmillProofBegin, windmillProofFinal)[0];
        if (refreshLandScape!=null) {
            refreshLandScape.refreshWindmillWall(windmillWallColor);
            refreshLandScape.refreshWindmillProof(windmillProofColor);
        }
    }

    private TimeInDay getCurTime(int firstItem) {
        int hour = firstItem / 60;
        int min = firstItem % 60;
        TimeInDay result = new TimeInDay(hour, min);
        return result;
    }


    private void updateBF(int curItem) {

        ScrollColorValue.ColorRange beginColor;
        ScrollColorValue.ColorRange finalColor;

        ScrollColorValue.ColorRange seaBeginColor;
        ScrollColorValue.ColorRange seaFinalColor;

//        int curHourZone = curItem/60;
//        if ((curHourZone != hourZone || hourZone==0) && curHourZone < 24) {
          if (isHZchanged) {
//            hourZone = curHourZone;
            beginColor = ScrollColorValue.getColorRange(hourZone);
            finalColor = ScrollColorValue.getColorRange((hourZone + 1) > 23 ? 0 : hourZone + 1);

            seaBeginColor = ScrollColorValue.getSeaColorRange(hourZone);
            seaFinalColor = ScrollColorValue.getSeaColorRange((hourZone + 1) > 23 ? 0 : hourZone + 1);
            beginTop = null;
            beginTop = new Argb(beginColor.getStart());
            beginBottom = null;
            beginBottom = new Argb(beginColor.getEnd());



            finalTop = null;
            finalTop = new Argb(finalColor.getStart());
            finalBottom = null;
            finalBottom = new Argb(finalColor.getEnd());

            seaBeginTop = null;
            seaBeginTop = new Argb(seaBeginColor.getStart());
            seaBeginBottom = null;
            seaBeginBottom = new Argb(seaBeginColor.getEnd());

            seaFinalTop = null;
            seaFinalTop = new Argb(seaFinalColor.getStart());
            seaFinalBottom = null;
            seaFinalBottom = new Argb(seaFinalColor.getEnd());
        }












    }


    private void refreshBg(int firstItem) {





        int[] skyColor = generateColor(firstItem, beginTop, finalTop, beginBottom, finalBottom);
        int[] seaColor = generateColor(firstItem, seaBeginTop, seaFinalTop, seaBeginBottom, seaFinalBottom);

//        int startColor = Color.argb(cur_start_a, cur_start_r, cur_start_g, cur_start_b);
//        int endColor = Color.argb(cur_end_a, cur_end_r, cur_end_g, cur_end_b);
//
//        int seaStartColor = Color.argb(sea_cur_start_a, sea_cur_start_r, sea_cur_start_g, sea_cur_start_b);
//        int seaEndColor = Color.argb(sea_cur_end_a, sea_cur_end_r, sea_cur_end_g, sea_cur_end_b);
        setBackColor(skyColor, seaColor);
    }

    private int[] generateColor(int firstItem, Argb mBeginTop, Argb mFinalTop, Argb mBeginBottom, Argb mFinalBottom) {
        int cur_num = firstItem % 60;

        int cur_start_a = mBeginTop.getA() + (((mFinalTop.getA()-mBeginTop.getA()) * cur_num) / 60);
        int cur_start_r = mBeginTop.getR() + (((mFinalTop.getR()-mBeginTop.getR()) * cur_num) / 60);
        int cur_start_g = mBeginTop.getG() + (((mFinalTop.getG()-mBeginTop.getG()) * cur_num) / 60);
        int cur_start_b = mBeginTop.getB() + (((mFinalTop.getB()-mBeginTop.getB()) * cur_num) / 60);

        int start = Color.argb(cur_start_a, cur_start_r, cur_start_g, cur_start_b);



        int cur_end_a = mBeginBottom.getA() + (((mFinalBottom.getA()-mBeginBottom.getA()) * cur_num) / 60);
        int cur_end_r = mBeginBottom.getR() + (((mFinalBottom.getR()-mBeginBottom.getR()) * cur_num) / 60);
        int cur_end_g = mBeginBottom.getG() + (((mFinalBottom.getG()-mBeginBottom.getG()) * cur_num) / 60);
        int cur_end_b = mBeginBottom.getB() + (((mFinalBottom.getB()-mBeginBottom.getB()) * cur_num) / 60);

        int end = Color.argb(cur_end_a, cur_end_r, cur_end_g, cur_end_b);

        return new int[]{start, end};


//        int sea_cur_start_a = seaBeginTop.getA() + (((seaFinalTop.getA()-seaBeginTop.getA()) * cur_num) / 60);
//        int sea_cur_start_r = seaBeginTop.getR() + (((seaFinalTop.getR()-seaBeginTop.getR()) * cur_num) / 60);
//        int sea_cur_start_g = seaBeginTop.getG() + (((seaFinalTop.getG()-seaBeginTop.getG()) * cur_num) / 60);
//        int sea_cur_start_b = seaBeginTop.getB() + (((seaFinalTop.getB()-seaBeginTop.getB()) * cur_num) / 60);
//
//
//
//        int sea_cur_end_a = seaBeginBottom.getA() + (((seaFinalBottom.getA()-seaBeginBottom.getA()) * cur_num) / 60);
//        int sea_cur_end_r = seaBeginBottom.getR() + (((seaFinalBottom.getR()-seaBeginBottom.getR()) * cur_num) / 60);
//        int sea_cur_end_g = seaBeginBottom.getG() + (((seaFinalBottom.getG()-seaBeginBottom.getG()) * cur_num) / 60);
//        int sea_cur_end_b = seaBeginBottom.getB() + (((seaFinalBottom.getB()-seaBeginBottom.getB()) * cur_num) / 60);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setBackColor(int[] skyColor, int[] seaColor) {
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, skyColor);
        scrollBackSky.setBackground(gd);
        GradientDrawable seaGd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, seaColor);
        scrollBackSea.setBackground(seaGd);

    }


    public TimeInDay getTime() {
        return time;
    }


    public interface RefreshLandScape {
        void refreshWindmillWall(int color);
        void refreshWindmillProof(int color);
    }
}
