/**
 * Copyright 2013 Barfoo
 * 
 * All right reserved
 * 
 * Created on 2013-3-6 下午5:40:47
 * 
 * @author zxy
 */
package com.barfoo.formatstyle;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.View;

public class FormatMaster {
	public static Context mContent;
	public static JSONArray mJsonArray;
	public static View createFormatMaster(Context context,JSONArray jsonarray) throws JSONException {
		mContent=context;
		mJsonArray=jsonarray;
		ArrayList<View> threestyles = getThreeDataStyle();
		ArrayList<View> fivestyles = getFiveDataStyle();
		ArrayList<View> sixstyles = getSixDataStyle();

		int counts = 0;
		View view = null;
		switch (mJsonArray.length()) {
		case 3:
			counts = (int) (Math.random() * threestyles.size());
			view = threestyles.get(counts);
			break;

		case 5:
			counts = (int) (Math.random() * fivestyles.size());
			view = fivestyles.get(counts);
			break;

		case 6:
			counts = (int) (Math.random() * sixstyles.size());
			view = sixstyles.get(counts);
			break;

		default:
			break;
		}
		return view;
	}

	public static ArrayList<View> getThreeDataStyle() {
		ArrayList<View> list = new ArrayList<View>();
		list.add(new AFormateStyle(mContent, null, mJsonArray, getChangeScreenValue()));
		return list;
	}

	public static ArrayList<View> getFiveDataStyle() {
		ArrayList<View> list = new ArrayList<View>();
		list.add(new CFormateStyle(mContent, null, mJsonArray, getChangeScreenValue()));
		return list;
	}

	public static ArrayList<View> getSixDataStyle() {
		ArrayList<View> list = new ArrayList<View>();
		list.add(new BFormateStyle(mContent, null, mJsonArray, getChangeScreenValue()));
		return list;
	}
	
	/**
	 * 获取当前的activity是横屏还是竖屏
	 * @param HorizonVerticalScreen :0为 横屏，1为竖屏
	 * 
	 */
	public static int changeScreenValue;

	public static int getChangeScreenValue() {
		return changeScreenValue;
	}

	public void setChangeScreenValue(int changeScreenValue) {
		this.changeScreenValue = changeScreenValue;
	}

}
