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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.View;

/**
 * 1. getallFormatClass
 * 2. getFormatClassBySize(size): FormatClass
 * 3. createview by FormatClass
 */
public class FormatMaster {
	public static Context mContent;
	public static JSONArray mJsonArray;

	private static ArrayList<Class<?>> getAllClasss() {
		ArrayList<Class<?>> classList = new ArrayList<Class<?>>();
		classList.add(AFormateStyle.class);
		classList.add(BFormateStyle.class);
		classList.add(CFormateStyle.class);
		return classList;
	}

	public static Class<?> getFormatSizeByClass(int size) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		ArrayList<Class<?>> list = getAllClasss();
		ArrayList<Class<?>> newList = new ArrayList<Class<?>>();
		for (Class<?> cls : list) {
			int sizevalue = cls.getDeclaredField("containSize").getInt(0);
			if (size == sizevalue) {
				newList.add(cls);
			}
		}

		int rint = new Random().nextInt(newList.size());
		return newList.get(rint);
	}

	public static Object getClassNameFormatMaster(JSONArray jsonarray, Object[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, InvocationTargetException {
		Class<?> formatClass = getFormatSizeByClass(jsonarray.length());

		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Constructor cons = formatClass.getConstructor(argsClass);
		return cons.newInstance(args);
	}

	public static View createFormatMaster(Context context, JSONArray jsonarray) throws JSONException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {

		ArrayList<Object> objects =new ArrayList<Object>();
		objects.add(context);
		objects.add(jsonarray);
		//getClassNameFormatMaster(jsonarray, objects);
		
		
		
		/*mContent = context;
		mJsonArray = jsonarray;
		int counts = 0;
		View view = null;

		switch (mJsonArray.length()) {
		case 3:
			ArrayList<View> threestyles = getThreeDataStyle();
			counts = (int) (Math.random() * threestyles.size());
			view = threestyles.get(counts);
			break;

		case 5:
			ArrayList<View> fivestyles = getFiveDataStyle();
			counts = (int) (Math.random() * fivestyles.size());
			view = fivestyles.get(counts);
			break;

		case 6:
			ArrayList<View> sixstyles = getSixDataStyle();
			counts = (int) (Math.random() * sixstyles.size());
			view = sixstyles.get(counts);
			break;

		default:
			break;
		}*/
		return null;
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
	private static int size;

	public static int getChangeScreenValue() {
		return changeScreenValue;
	}

	public void setChangeScreenValue(int changeScreenValue) {
		this.changeScreenValue = changeScreenValue;
	}

}
