package com.example.root.otherComponent;

import android.content.Context;

/**
 * the class change pixel to device independent pixel
 * or change dp to pixel
 * created by wxk on 2015-5-14
 * function diptopx() or pxtodp()
 * */

public class DensityUtil {
	
	public static int dptopx(Context context,float dpvalue){
		final float scale=context.getResources().getDisplayMetrics().density;
		return (int)(dpvalue*scale+0.5f);
		
	}
	
	public static int pxtodp(Context context,float pxvalue){
		final float scale=context.getResources().getDisplayMetrics().density;
		return (int)(pxvalue/scale+0.5f);
	}
	public static float pixelsToSp(Context context, float px) {
		float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
		return px/scaledDensity;
	}

	public static float spToPx(Context context, float sp) {
		float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
		return sp * scaledDensity;
	}
}
