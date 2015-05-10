package com.example.root.otherComponent;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.root.main.alarmandmusic.R;

/**
 * Created by zhanglei on 15-5-2.
 * Email: zhangleicoding@163.com
 */
public class GlobalWidget {
    private RelativeLayout mainActivity;
    private Context context;

    private Button settingBtn;
    private Button editBtn;
    private Button timeOkBtn;

    public GlobalWidget(RelativeLayout mainActivity, Context context) {
        this.mainActivity = mainActivity;
        this.context = context;
        init();
    }

    private void init() {
        RelativeLayout.LayoutParams settinglayoutParams = new RelativeLayout.LayoutParams(250, 60);
        settinglayoutParams.leftMargin = 50;
        settinglayoutParams.topMargin = 50;
        settingBtn = new Button(context);
        settingBtn.setText(R.string.btn_setting);
        settingBtn.setPadding(0, 0, 0, 0);
        settingBtn.setBackgroundColor(Color.argb(0x40, 0xff, 0xff, 0xff));
        settingBtn.setBackgroundResource(R.drawable.btn_bg);
        if (mainActivity != null) {
            mainActivity.addView(settingBtn, settinglayoutParams);
        } else {
            // tell to log that something wrong!
        }

        // add editBtn to mainActivity
        RelativeLayout.LayoutParams editLayoutParams = new RelativeLayout.LayoutParams(250, 60);
        editLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        editLayoutParams.rightMargin = 50;
        editLayoutParams.topMargin = 50;
        editBtn = new Button(context);
        editBtn.setText(R.string.btn_edit);
        editBtn.setPadding(0, 0, 0, 0);
        editBtn.setBackgroundColor(Color.argb(0x40, 0xff, 0xff, 0xff));
        editBtn.setBackgroundResource(R.drawable.btn_bg);
        if (mainActivity != null) {
            mainActivity.addView(editBtn, editLayoutParams);
        } else {
            // tell to log that something wrong!
        }


        // add TimeOkBtn to mainActivity
        RelativeLayout.LayoutParams timeOkLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        timeOkLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        timeOkLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        timeOkLayoutParams.bottomMargin = 200;
        timeOkBtn = new Button(context);
        timeOkBtn.setText(R.string.btn_time_ok);
        timeOkBtn.setPadding(0, 0, 0, 0);
        timeOkBtn.setBackgroundColor(Color.argb(0x40, 0xff, 0xff, 0xff));
        timeOkBtn.setBackgroundResource(R.drawable.btn_bg);
        if (mainActivity != null) {
            mainActivity.addView(timeOkBtn, timeOkLayoutParams);
        } else {
            // tell to log that something wrong!
        }
        timeOkBtn.setVisibility(View.INVISIBLE);

    }


    public boolean setSettingOnClickListener(View.OnClickListener listener) {
        boolean result = true;
        settingBtn.setOnClickListener(listener);

        return result;
    }




}
