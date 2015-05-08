package com.example.root.blurringView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.root.main.alarmandmusic.R;

/**
 * Created by zhanglei on 15-5-2.
 * Email: zhangleicoding@163.com
 */
public class ActivityTest extends Activity {

    private ImageView img;
    private RelativeLayout activityTest;
    private View text;
    private ImageView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);
        init();


    }

    private void init() {
        img = (ImageView)findViewById(R.id.test_bg);
        activityTest = (RelativeLayout)findViewById(R.id.activity_id_test);
        text = findViewById(R.id.test_text);
        result = (ImageView)findViewById(R.id.test_result);
        applyBlur();
        // BitmapDrawable bd = new BitmapDrawable(getResources(), getScreenShot());
        //result.setImageBitmap(getScreenShot(text));
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityTest.this, "click img", Toast.LENGTH_LONG).show();
                // applyBlur();
            }
        });
    }

    private void applyBlur() {
        img.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                img.getViewTreeObserver().removeOnPreDrawListener(this);
                img.buildDrawingCache();

                Bitmap bmp = img.getDrawingCache();
                blur(bmp, result);
                return true;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void blur(Bitmap bkg, View view) {
        Log.i("zhanglei", "I am in blur()");
        long start = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 20;
        if (true /*downScale.isChecked()*/) {
            scaleFactor = 10;
            radius = 2;
        }

        Bitmap overlay = Bitmap.createBitmap((int)(view.getMeasuredWidth()/scaleFactor), (int)(view.getMeasuredHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        //paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = BlurringUtil.doBlur(overlay, (int)radius, true);

        view.setBackground(new BitmapDrawable(getResources(), overlay));
        Log.i("zhanglei-ms", "time spend: " + (System.currentTimeMillis() - start) + "ms" );
    }



    private Bitmap getScreenShot(View view) {
        Bitmap bitmap;
        view.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

}
