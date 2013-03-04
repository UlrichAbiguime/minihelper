package com.barfoo.flip.demo.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.pm.ActivityInfo;

public class Util {

	/**
	 * 判断当前的关键字是否为空
	 * @param obj
	 * @param str(关键字)
	 * @return
	 * @throws JSONException
	 */
	public static  boolean isJsonNull(JSONObject obj,String str) throws JSONException{
		if(obj.getString(str).length()>0 && obj.getString(str)!=null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 *  获取当前的屏幕是横屏还是竖屏
	 * @param activity
	 * @return
	 */
	public static boolean isScreenOrientation(Activity activity){
		
		if(activity.getRequestedOrientation() ==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
			return true;
		}else{
			return false;
		}
	} 
}
