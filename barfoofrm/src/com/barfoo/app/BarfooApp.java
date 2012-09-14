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
	public static String Host = "http://192.168.1.160:8080/m/mcm/check?token=X3Nlc3Npb25faWQ9Ik1tSXhOekpqWldJNU56RXpOR0ZqWmpnNE1UZzJaak01WlRrNE9UUTFObVE9fDEzNDc1ODc0Mzd8OWU3ODc3YTMxYjFiZTFmMGZjZWM4MzlmYzM0NWYyZDA3MDhhMzMzMyI7IGV4cGlyZXM9U3VuLCAxNCBPY3QgMjAxMiAwMTo1MDozNyBHTVQ7IFBhdGg9Lw==&uid=4ff565c6b322d01f1c000008&";
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
