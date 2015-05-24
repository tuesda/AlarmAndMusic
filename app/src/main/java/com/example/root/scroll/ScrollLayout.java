package com.example.root.scroll;

import android.content.Context;
import android.view.LayoutInflater;
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
    private static final int TOTAL_ITEMS_IN_DAY = ITEMS_IN_DAY + 1;

    private Context context;
    private LayoutInflater mInflater;


    private ListView lv;


    private TextView display_debug;

    private RelativeLayout parentLayout;
    private RelativeLayout scrollContainer;




    private int hourZone;
    private boolean isHZchanged; // check is the hour zone changed

    private TimeInDay time;


    private OnSwipListener onSwipListener;
    private OnFanChange onFanChange;
    private OnTimeChange onTimeChange;

    private boolean isFirst = true;




    public ScrollLayout(Context context, RelativeLayout parentLayout) {
        this.context = context;
        this.parentLayout = parentLayout;
        mInflater = LayoutInflater.from(context);
        init();
    }


    private void init() {
        scrollContainer = (RelativeLayout)mInflater.inflate(R.layout.scroll_list, null);

        lv = (ListView)scrollContainer.findViewById(R.id.scroll_list_back);
        lv.setDivider(null);


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
//                display_debug.setText(firstVisibleItem + "/" + (totalItemCount - ITEMS_IN_VISIBLE));
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

                        time = getCurTime(firstItem);

                        if (onTimeChange!=null) {
                            onTimeChange.elapse(time);
                        }

                        updateFan(firstItem);



                    }
                }

            }
        });



    }

    private void updateFan(int item) {
        if (onFanChange!=null) {
            onFanChange.change(item);
        }

    }




    public void setOnSwipListener(OnSwipListener listener) {
        onSwipListener = listener;
    }

    public void setOnFanChange(OnFanChange change) { onFanChange = change; }

    public void setOnTimeChange(OnTimeChange change) {
        onTimeChange = change;
    }



    private TimeInDay getCurTime(int firstItem) {
        int hour = firstItem / 60;
        int min = firstItem % 60;
        TimeInDay result = new TimeInDay(hour, min);
        return result;
    }




    public interface OnSwipListener {
        void setVisible();
        void setInvisible();
    }

    public interface OnFanChange {
        void change(int item);
    }

    public interface OnTimeChange {
        void elapse(TimeInDay time);
    }
}
