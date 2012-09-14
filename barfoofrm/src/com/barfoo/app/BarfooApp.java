/**
 *
 *@Class BarfooApp.java
 *@author zxy
 *@date 2012-2012-9-13-上午10:50:56
 *@Description 说明
 */
package com.barfoo.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

public class BarfooApp extends Application {

	public static String API_HOST = "http://192.168.1.160:8080";
	public static Resources resources;
	public static Context mContext;
	public static SharedPreferences mLoginUser;
	public static SharedPreferences mPref;
	public static int HttpTimeOut = 5 * 1000;

	@Override
	public void onCreate() {
		super.onCreate();
		resources = this.getResources();
		mContext = this.getApplicationContext();
		mLoginUser = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
		mPref = this.getSharedPreferences("sharedoc", Context.MODE_PRIVATE);
	}
}
