package com.barfoo.flip.demo.data;

import android.app.Activity;
import android.util.DisplayMetrics;
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
	 * 获取为设置view的宽度
	 * @param view
	 * @return
	 */
	/**
	public static int getNoSetViewWidth(View view){
		if(view.getLayoutParams().width==0){
			return setViewWidthHeight(view).getMeasuredWidth();
		}else{
			return view.getLayoutParams().width;
		}
	}
	
	public static int getNoSetViewHeight(View view){
		if(view.getLayoutParams().height==0){
			int viewW = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
			int viewH = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
			view.measure(viewW,viewH);
			return view.getMeasuredHeight();
		}else{
			return view.getLayoutParams().height;
		}
	}
	
	public static View setViewWidthHeight(View view){
		int viewW = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		int viewH = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		view.measure(viewW,viewH);
		return view;
	}
	
	**/
}
