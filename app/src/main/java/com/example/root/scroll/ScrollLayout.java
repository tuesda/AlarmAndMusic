package com.example.root.scroll;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;
import com.example.root.otherComponent.DensityUtil;

/**
 * Created by zhanglei on 15/5/10.
 */
public class ScrollLayout {
    private static final int ITEMS_IN_VISIBLE = 33;
    private static final int ITEMS_IN_DAY = 1440+ITEMS_IN_VISIBLE;
    private static final int TOTAL_ITEMS_IN_DAY = ITEMS_IN_DAY + 1;

    private Context context;
    private LayoutInflater mInflater;

    private TextView curTimeV;
    private TextView whenOfD;

    private ListView lv;

    private View scrollBackSky;
    private View scrollBackSea;

    private ImageView timeColon;

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

    private Argb windmillFanBegin;
    private Argb windmillFanFinal;

    private Argb mountainFarBegin;
    private Argb mountainFarFinal;

    private Argb mountainNearBegin;
    private Argb mountainNearFinal;

    private Argb houseFrontBegin;
    private Argb houseFrontFinal;

    private Argb houseSideBegin;
    private Argb houseSideFinal;

    private Argb houseWindowBegin;
    private Argb houseWindowFinal;

    private Argb windmillWinBegin;
    private Argb windmillWinFinal;

    private Argb waveBegin;
    private Argb waveFinal;

    private int hourZone;
    private boolean isHZchanged; // check is the hour zone changed

    private TimeInDay time;


    private RefreshLandScape refreshLandScape;
    private OnSwipListener onSwipListener;

    private boolean isFirst = true;




    public ScrollLayout(Context context, RelativeLayout parentLayout) {
        this.context = context;
        this.parentLayout = parentLayout;
        mInflater = LayoutInflater.from(context);
        init();
    }


    private void init() {
        scrollContainer = (RelativeLayout)mInflater.inflate(R.layout.scroll_list, null);
        curTimeV = (TextView)scrollContainer.findViewById(R.id.scroll_list_time);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "font/custom.ttf");
        curTimeV.setTypeface(tf);
        whenOfD = (TextView)scrollContainer.findViewById(R.id.when_of_day);
        whenOfD.setTypeface(tf);
        whenOfD.setText("Midnight");

        lv = (ListView)scrollContainer.findViewById(R.id.scroll_list_back);
        lv.setDivider(null);

        timeColon = (ImageView)scrollContainer.findViewById(R.id.colon);

        scrollBackSky = scrollContainer.findViewById(R.id.scroll_back_sky);
        scrollBackSea = scrollContainer.findViewById(R.id.scroll_back_sea);

        display_debug = (TextView)scrollContainer.findViewById(R.id.display_debug);
        RelativeLayout.LayoutParams scrollParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        parentLayout.addView(scrollContainer, scrollParams);


        ScrollBaseAdapter scrollAdapter = new ScrollBaseAdapter(TOTAL_ITEMS_IN_DAY, context);
        lv.setAdapter(scrollAdapter);

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case SCROLL_STATE_FLING:
                        if (onSwipListener!=null) {
                            onSwipListener.setInvisible();
                        }
                        break;
                    case SCROLL_STATE_IDLE:
                        if (onSwipListener!=null) {
                            onSwipListener.setVisible();
                        }
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        if (onSwipListener!=null) {
                            onSwipListener.setInvisible();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                display_debug.setText(firstVisibleItem + "/" + (totalItemCount - ITEMS_IN_VISIBLE));
                int firstItem;
                int totalItems;
                if (firstVisibleItem==0) {
                    if (isFirst) {
                        lv.setSelection(1);
                        isFirst = false;
                    } else {
                        lv.setSelection(TOTAL_ITEMS_IN_DAY-ITEMS_IN_VISIBLE);
                    }
                } else {
                    firstItem = firstVisibleItem - 1;
                    totalItems = totalItemCount - 1;
                    if (firstItem == totalItems - ITEMS_IN_VISIBLE) {
                        lv.setSelection(1);
                    } else {
                        int curHourZone = firstItem / 60;
                        if ((curHourZone != hourZone || hourZone == 0) && curHourZone < 24) {
                            hourZone = curHourZone;
                            isHZchanged = true;
                        } else {
                            isHZchanged = false;
                        }
                        updateBF(firstItem); // update begin_top begin_bottom final_top final_bottom

                        time = getCurTime(firstItem);
                        curTimeV.setText(time.toString());
                        updateWhenOfD();

                        refreshLand(firstItem);

                        if (isReady) {
                            refreshBg(firstItem);
                        }
                    }
                }
                // lv.setSelection(TOTAL_ITEMS_IN_DAY-ITEMS_IN_VISIBLE-1);

            }
        });



    }

    private void updateWhenOfD() {
        int hour = 0;
        if (time!=null) hour = time.getHour();
        switch (hour) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 0:
                whenOfD.setText("Evening");
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                whenOfD.setText("Midnight");
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                whenOfD.setText("Morning");
                break;
            case 12:
                whenOfD.setText("Midday");
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                whenOfD.setText("Afternoon");
                break;
            default:
                whenOfD.setText("Wrong text");

        }
    }


    public void setRefreshLandScape(RefreshLandScape refreshLandScape) {
        this.refreshLandScape = refreshLandScape;
    }

    public void setOnSwipListener(OnSwipListener listener) {
        onSwipListener = listener;
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

            windmillFanBegin = null;
            windmillFanBegin = new Argb(ScrollColorValue.getWindmillFanColor(time.getHour()));
            windmillFanFinal = null;
            windmillFanFinal = new Argb(ScrollColorValue.getWindmillFanColor(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));

            mountainFarBegin = null;
            mountainFarFinal = null;
            mountainFarBegin = new Argb(ScrollColorValue.getMountainFarColor(time.getHour()));
            mountainFarFinal = new Argb(ScrollColorValue.getMountainFarColor(time.getHour()+1 > 23 ? 0 : time.getHour()+1));

            mountainNearBegin = null;
            mountainNearFinal = null;
            mountainNearBegin = new Argb(ScrollColorValue.getMountainNearColor(time.getHour()));
            mountainNearFinal = new Argb(ScrollColorValue.getMountainNearColor(time.getHour()+1 > 23 ? 0 : time.getHour() + 1));

            houseFrontBegin = null;
            houseFrontFinal = null;
            houseFrontBegin = new Argb(ScrollColorValue.getHouseFron(time.getHour()));
            houseFrontFinal = new Argb(ScrollColorValue.getHouseFron(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));

            houseSideBegin = null;
            houseSideFinal = null;
            houseSideBegin = new Argb(ScrollColorValue.getHouseSideColor(time.getHour()));
            houseSideFinal = new Argb(ScrollColorValue.getHouseSideColor(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));

            houseWindowBegin = null;
            houseWindowFinal = null;
            houseWindowBegin = new Argb(ScrollColorValue.getHouseWindowC(time.getHour()));
            houseWindowFinal = new Argb(ScrollColorValue.getHouseWindowC(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));

            windmillWinBegin = null;
            windmillWinFinal = null;
            windmillWinBegin = new Argb(ScrollColorValue.getWindmillWinColor(time.getHour()));
            windmillWinFinal = new Argb(ScrollColorValue.getWindmillWinColor(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));

            waveBegin = null;
            waveFinal = null;
            waveBegin = new Argb(ScrollColorValue.getWave(time.getHour()));
            waveFinal = new Argb(ScrollColorValue.getWave(time.getHour() + 1 > 23 ? 0 : time.getHour() + 1));

        }
        int windmillWallColor = generateColor(firstItem, windmillWallBegin, windmillWallFinal, windmillWallBegin, windmillWallFinal)[0];
        int windmillProofColor = generateColor(firstItem, windmillProofBegin, windmillProofFinal, windmillProofBegin, windmillProofFinal)[0];
        int windmillWinColor = generateColor(firstItem, windmillWinBegin, windmillWinFinal, windmillWinBegin, windmillWinFinal)[0];
        int windmillFanColor = generateColor(firstItem, windmillFanBegin, windmillFanFinal, windmillFanBegin, windmillFanFinal)[0];

        int mountainFarColor = generateColor(firstItem, mountainFarBegin, mountainFarFinal, mountainFarBegin, mountainFarFinal)[0];
        int mountainNearColor = generateColor(firstItem, mountainNearBegin, mountainNearFinal, mountainNearBegin, mountainNearFinal)[0];

        int houseFrontColor = generateColor(firstItem, houseFrontBegin, houseFrontFinal, houseFrontBegin, houseFrontFinal)[0];
        int houseSideColor = generateColor(firstItem, houseSideBegin, houseSideFinal, houseSideBegin, houseSideFinal)[0];
        int houseWindowColor = generateColor(firstItem, houseWindowBegin, houseWindowFinal, houseWindowBegin, houseWindowFinal)[0];

        int waveColor = generateColor(firstItem, waveBegin, waveFinal, waveBegin, waveFinal)[0];
        if (time.getHour() == 2 || time.getHour()==22) {
            waveColor = 0; // 0x00000000
        }

        float starsAl = ScrollColorValue.getStarsAl(time.getHour());

        if (refreshLandScape!=null) {
            refreshLandScape.refreshWindmill(windmillWallColor, windmillProofColor, windmillWinColor, windmillFanColor);
            refreshLandScape.refreshMountain(mountainFarColor, mountainNearColor);
            refreshLandScape.refreshHouse(houseFrontColor, houseSideColor, houseWindowColor);
            refreshLandScape.refreshWave(waveColor);
            refreshLandScape.setStarsAl(starsAl);
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

          if (isHZchanged) {
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


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setBackColor(int[] skyColor, int[] seaColor) {
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, skyColor);
        scrollBackSky.setBackground(gd);
        GradientDrawable seaGd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, seaColor);
        scrollBackSea.setBackground(seaGd);

    }

    public void downSear(int duration) {
        ObjectAnimator sea = ObjectAnimator.ofFloat(scrollBackSea, "translationY", 0f, DensityUtil.dptopx(context, 170));
        sea.setDuration(duration);
        sea.start();
    }
    public void folderSky(int duration) {
        ObjectAnimator folder = ObjectAnimator.ofFloat(scrollBackSky, "scaleY", 1f, 0.2f);
        ObjectAnimator up = ObjectAnimator.ofFloat(scrollBackSky, "translationY", 0f, -DensityUtil.dptopx(context,180));
        ObjectAnimator time = ObjectAnimator.ofFloat(curTimeV, "translationX", 0f, -DensityUtil.dptopx(context, 95));
        ObjectAnimator timeScaleY = ObjectAnimator.ofFloat(curTimeV, "scaleY", 1f, 0.8f);
        ObjectAnimator timeScaleX = ObjectAnimator.ofFloat(curTimeV, "scaleX", 1f, 0.8f);
        ObjectAnimator whenInDay = ObjectAnimator.ofFloat(whenOfD, "translationX", 0f, -DensityUtil.dptopx(context, 140));
        ObjectAnimator colonAni = ObjectAnimator.ofFloat(timeColon, "translationX", 0f, -DensityUtil.dptopx(context, 95));
        AnimatorSet sky = new AnimatorSet();
        sky.play(folder).with(up).with(time).with(timeScaleY).with(timeScaleX).with(whenInDay).with(colonAni);
        sky.setDuration(duration);
        sky.start();
    }


    public TimeInDay getTime() {
        return time;
    }


    public interface RefreshLandScape {
        void refreshWindmill(int wallColor, int proofColor, int winColor, int fanColor);
        void refreshWave(int waveColor);
        void refreshMountain(int far, int near);
        void refreshHouse(int front, int side, int window);
        void setStarsAl(float alaph);
    }

    public interface OnSwipListener {
        void setVisible();
        void setInvisible();
    }
}
