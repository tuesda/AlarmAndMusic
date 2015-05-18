package com.example.root.otherComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * created by wangxukang on 2015-5-17 
 * implemented the Sea Wave result 
 * studyed on csdn 
 */
public class WaveView extends View {

	private int mViewWidth;
	private int mViewHeight;

	/*
	 * the level Line of sea wave
	 */
	private float mLevelLine;

	/*
	 * the range of sea wave
	 */
	private float mWaveHeight = 20;
	/*
	 * the wave length of sea wave
	 */
	private float mWaveWidth = 80;
	/*
	 * the hidden Left waveform
	 */
	private float mLeftSide;

	private float mMoveLen;
	/*
	 * the translate speed of the sea wave
	 */
	public static final float SPEED = 1f;
	/*
	 * the list of all sea wave points
	 */
	private List<Point> mPointsList;
	/*
	 * the paint of sea wave
	 */
	private Paint mPaint;
	/*
	 * the path class of sea wave
	 */
	private Path mWavePath;
	private boolean isMeasured = false;

	private Timer timer;
	private MyTimerTask mTask;

	public WaveView(Context context) {
		super(context);
		init();
	}

	public WaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WaveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mPointsList = new ArrayList<Point>();
		timer = new Timer();

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(Color.BLUE);

		mWavePath = new Path();
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		// start sea wave
		start();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!isMeasured) {
			isMeasured = true;
			// get the wave view height and width
			mViewHeight = getMeasuredHeight();
			mViewWidth = getMeasuredWidth();
			
            //get the height and width of wave
			
			Log.d("wxk", "mViewHeight" + mViewHeight + "mViewWidth"
					+ mViewWidth);
			/*
			 * the sea level raise from 
			 * the bottom of the view
			 * */
			mLevelLine = mViewHeight;
			/*
			 * the peak value of the wave 
			 * cal by view Width
			 * */
			mWaveHeight = mViewWidth / 20f;
			/* wave Width equal 3*ViewWidth
			 * so you see the obvious fling
			 * the sea wave
			 * */ 
			mWaveWidth = mViewWidth * 3;
			
			mLeftSide = -mWaveWidth;
	        /*
	         * view conclude n wave
	         * */
			int n = (int) Math.round(mViewWidth / mWaveWidth + 0.5);
			for (int i = 0; i < (4 * n + 5); i++) {
				
				float x = i * mWaveWidth / 4 - mWaveWidth;
				float y = 0;
				switch (i % 4) {
				case 0:
				case 2:
					
					y = mLevelLine;
					break;
				case 1:
					//down the wave peak
					y = mLevelLine + mWaveHeight;
					break;
				case 3:
					// up the wave peak
					y = mLevelLine - mWaveHeight;
					break;
				}
				mPointsList.add(new Point(x, y));
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		mWavePath.reset();
		int i = 0;
		mWavePath.moveTo(mPointsList.get(0).getX(), mPointsList.get(0).getY());
		for (; i < mPointsList.size() - 2; i = i + 2) {
			mWavePath.quadTo(mPointsList.get(i + 1).getX(),
					mPointsList.get(i + 1).getY(), mPointsList.get(i + 2)
							.getX(), mPointsList.get(i + 2).getY());
		}
		mWavePath.lineTo(mPointsList.get(i).getX(), mViewHeight);
		mWavePath.lineTo(mLeftSide, mViewHeight);
		mWavePath.close();

		// paint under the sea wave zone
		
		canvas.drawPath(mWavePath, mPaint);
	}

	Handler updateHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// the total length of sea wave
			mMoveLen += SPEED;
			
			/* level line of sea
			 * this value can 
			 * change the level
			 * the lower value sea level is high
			 * the bigger value sea level is low
			 */
			mLevelLine = 10;
			
			mLeftSide += SPEED;
			for (int i = 0; i < mPointsList.size(); i++) {
				mPointsList.get(i).setX(mPointsList.get(i).getX() + SPEED);
				switch (i % 4) {
				case 0:
				case 2:
					mPointsList.get(i).setY(mLevelLine);
					break;
				case 1:
					// control the highest point y Value
					mPointsList.get(i).setY(mLevelLine + mWaveHeight+20);
					break;
				case 3:
					//control the lowest point y value
					mPointsList.get(i).setY(mLevelLine - mWaveHeight);
					break;
				}
			}
			if (mMoveLen >= mWaveWidth) {
				// recovery the wave x value
				mMoveLen = 0;
				resetPoints();
			}
			invalidate();
		}

	};

	/*
	 * recovery the all point x original value
	 */
	private void resetPoints() {
		mLeftSide = -mWaveWidth;
		for (int i = 0; i < mPointsList.size(); i++) {
			mPointsList.get(i).setX(i * mWaveWidth / 4 - mWaveWidth);
		}
	}

	private void start() {
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}
		mTask = new MyTimerTask(updateHandler);
		timer.schedule(mTask, 0, 10);
	}

	class MyTimerTask extends TimerTask {
		Handler handler;

		public MyTimerTask(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void run() {
			handler.sendMessage(handler.obtainMessage());
		}

	}

	class Point {
		private float x;
		private float y;

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}

	}

}
