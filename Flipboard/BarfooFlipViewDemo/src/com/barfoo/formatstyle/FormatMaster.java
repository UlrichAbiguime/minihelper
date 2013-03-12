/**
 * Copyright 2013 Barfoo
 * 
 * All right reserved
 * 
 * Created on 2013-3-12 上午10:07:49
 * 
 * @author zxy
 */
package com.barfoo.formatstyle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class FormatMaster {

	public static FormatMaster mFormatMasters = null;
	ArrayList<Class<?>> classList;
	public static FormatMaster getFormatMasters() {
		if (mFormatMasters == null) {
			mFormatMasters = new FormatMaster();
			mFormatMasters.initClasss();
		}
		return mFormatMasters;
	}

	private void initClasss() {
		classList.add(AFormateStyle.class);
		classList.add(BFormateStyle.class);
		classList.add(CFormateStyle.class);
	}

	public Class<?> getFormatSizeByClass(int size) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		ArrayList<Class<?>> newList = new ArrayList<Class<?>>();
		for (Class<?> cls : classList) {
			int sizevalue = cls.getDeclaredField("containSize").getInt(0);
			if (size == sizevalue) {
				newList.add(cls);
			}
		}

		int rint = new Random().nextInt(newList.size());
		return newList.get(rint);
	}

	public View createFormatView(Context context, JSONArray jsonarray) {
		Class<?> formatClass = null;
		try {
			formatClass = getFormatSizeByClass(jsonarray.length());
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Constructor localConstructor = null;
		try {
			localConstructor = formatClass.getConstructor(Context.class, AttributeSet.class, JSONArray.class, Integer.class);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View newInstance = null;
		try {
			newInstance = (View) localConstructor.newInstance(context, null, jsonarray, getChangeScreenValue());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newInstance;

	}

	/**
	 * 获取当前的activity是横屏还是竖屏
	 * @param HorizonVerticalScreen :0为 横屏，1为竖屏
	 * 
	 */
	public static int changeScreenValue;
	private static int size;

	public static int getChangeScreenValue() {
		return changeScreenValue;
	}

	public void setChangeScreenValue(int changeScreenValue) {
		this.changeScreenValue = changeScreenValue;
	}

}
