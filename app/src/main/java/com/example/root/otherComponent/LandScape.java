package com.example.root.otherComponent;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.root.main.alarmandmusic.MainActivity;
import com.example.root.main.alarmandmusic.R;
import com.example.root.scroll.ScrollLayout;

/**
 * Created by zhanglei on 15/5/12.
 */
public class LandScape {
    private Context context;
    private RelativeLayout mainActivity;
    private ScrollLayout scrollLayout;
    private LayoutInflater mInflater;

    private RelativeLayout landscape;

    private ImageView windmillWall;
    private ImageView windmillProof;


    public LandScape(Context context, RelativeLayout mainActivity, ScrollLayout scrollLayout) {
        this.context = context;
        this.mainActivity = mainActivity;
        this.scrollLayout = scrollLayout;
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {

        landscape = (RelativeLayout)mInflater.inflate(R.layout.landscape, null);
        windmillWall = (ImageView)landscape.findViewById(R.id.windmill_wall);
        windmillProof = (ImageView)landscape.findViewById(R.id.windmill_proof);

        scrollLayout.setRefreshLandScape(new ScrollLayout.RefreshLandScape() {
            @Override
            public void refreshWindmillWall(int color) {
                windmillWall.setColorFilter(color);
            }

            @Override
            public void refreshWindmillProof(int color) {
                windmillProof.setColorFilter(color);
            }
        });

        RelativeLayout.LayoutParams landParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainActivity.addView(landscape, landParams);



        Display mDisplay = ((MainActivity)context).getWindowManager().getDefaultDisplay();

        Log.i("zhanglei-landscape", mDisplay.getWidth() + "---" + mDisplay.getHeight());

    }



}
