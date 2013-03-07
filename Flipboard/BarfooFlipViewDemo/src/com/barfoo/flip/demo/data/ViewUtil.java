package com.barfoo.flip.demo.data;

import com.barfoo.flipview.demo.R;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class ViewUtil {
	public static int screenW;
	public static int screenH;
	public static int trueScreenW;
	public static int trueScreenH;
	
	public static void screenInfo(Activity activity){
		DisplayMetrics dm=new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenW = dm .widthPixels;
		screenH = dm.heightPixels;
		Log.e("screenW:", screenW+",screenH:"+screenH);
	}
	/**
	 * 设置组件的宽度(给父类的宽度为整个屏幕的宽度)
	 * @param view(要设置的组件)
	 * @param width(所占比例)
	 */

	public static void setViewWidth(View view,double width){
		if(view.getLayoutParams() ==null){
			view.setLayoutParams(new LayoutParams((int)(screenW * width),LayoutParams.WRAP_CONTENT));
		}else {
			view.getLayoutParams().width=(int)(screenW * width);
		}

	}
	
	/**
	 * 设置组件的宽度
	 * @param view(要设置的组件)
	 * @param groupviewWidth(父组件的宽度)
	 * @param width(所占比例)
	 */
	public static void setViewWidth(View view,double groupviewWidth,double width){
		if(view.getLayoutParams() ==null){
			view.setLayoutParams(new LayoutParams((int)(groupviewWidth * width),LayoutParams.WRAP_CONTENT));
		}else{
			view.getLayoutParams().width=(int)(groupviewWidth * width);
		}
	}
	
	
	
	/** 
	 * 设置组件的高度(给父类的宽度为整个屏幕的高度)
	 * @param view
	 * @param height
	 */
	public static void setViewHeight(View view,double height){
		if(view.getLayoutParams() ==null){
			view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, (int)(screenH * height)));
		}else{
			view.getLayoutParams().height=(int)(screenH * height);
		}
	}

	
	/**
	 * 设置组件的高度
	 * @param view(要设置的组件)
	 * @param groupviewHeight(父组件的高度)
	 * @param height(所占比例)
	 */
	public static void setViewHeight(View view,double groupviewHeight,double height){
		if(view.getLayoutParams() ==null){
			view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, (int)(groupviewHeight * height)));
		}else{
			view.getLayoutParams().height=(int)(groupviewHeight * height);
		}
	}
	
	/**
	 * 设置组件的宽高
	 * @param view
	 * @param width
	 * @param height
	 */
	public static void setViewWidHeight(View view ,double width,double height){
		if(view.getLayoutParams() ==null){
			view.setLayoutParams(new LayoutParams((int)(screenW * width),(int)(screenH * height)));
		}else{
			view.getLayoutParams().width=(int)(screenW * width);
			view.getLayoutParams().height=(int)(screenH * height);
		}
	}
	
	/**
	 * 设置组件的宽高
	 * @param (要设置的组件)
	 * @param groupWidth(父组件的宽度)
	 * @param groupHeight(父组件的高度)
	 * @param width
	 * @param height
	 */
	public static void setViewWidHeight(View view , double groupWidth,double groupHeight,double width,double height){
		if(view.getLayoutParams() ==null){
			view.setLayoutParams(new LayoutParams((int)((groupWidth * width)),(int)(groupHeight * height)));
		}else{
			view.getLayoutParams().width=(int)((groupWidth * width));
			view.getLayoutParams().height=(int)(groupHeight * height);
		}
	}
	
	/**
	 * 获取view宽度
	 * @param view
	 * @return
	 */
	public static int getViewWidth(View view){
		
		return view.getLayoutParams().width;
	}
	/**
	 * 获取view高度
	 * @param view
	 * @return
	 */
	public static int getViewHeight(View view){
		return view.getLayoutParams().height;
	}
	
	/**
	 * 设置view padding
	 * @param view
	 * @param width
	 * @param height
	 * @param proleft
	 * @param protop
	 * @param proright
	 * @param probottom
	 */
	
	public static void setViewPadding(View view ,int width,int height,double proleft,double protop,double proright,double probottom ){
		view.setPadding((int)(width * proleft),(int)(height *protop),(int)(width * proright),(int)(height *probottom));
	}
	
	
	/**
	 * 横向称满全屏的直线
	 * @param context
	 * @return
	 */
	public static View addViewlines(Context context){
		View view=LayoutInflater.from(context).inflate(R.layout.viewline, null);
		setViewWidth(view, screenW, 1);
		view.getLayoutParams().height=1;
		return view;
	}
	
	/**
	 * 横向container之间添加横线
	 * @param context
	 * @return
	 */
	public static View addViewlines(Context context,int width,int height){
		View view=LayoutInflater.from(context).inflate(R.layout.viewline, null);
		ViewUtil.setViewWidth(view, width, 1);
		view.getLayoutParams().height=1;
		Log.e("height", view.getLayoutParams().height+"");
		return view;
	}
	
	/**
	 * 纵向称满全屏
	 * @param context
	 * @return
	 */
	public static View addVerticalViewlines(Context context){
		View view=LayoutInflater.from(context).inflate(R.layout.verticalviewline, null);
		ViewUtil.setViewHeight(view, screenH, 1);
		view.getLayoutParams().width=1;
		return view;
	}
	
	/**
	 * 纵向container之间添加竖线
	 * @param context
	 * @param width
	 * @param height
	 * @return
	 */
	public static View addVerticalViewlines(Context context,int height){
		View view=LayoutInflater.from(context).inflate(R.layout.verticalviewline, null);
		ViewUtil.setViewHeight(view, height, 1);
		view.getLayoutParams().width=1;
		return view;
	}
}
