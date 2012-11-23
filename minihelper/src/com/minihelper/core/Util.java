/**
 * Util.java
 * 
 * @user zn
 * @mail wusheng198910@126.com
 * 2012-8-2
 */
package com.minihelper.core;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.minihelper.ClientApp;
import com.minihelper.logic.ClientApi;

public class Util {
	
	public static String Host = "http://192.168.1.160:8080/m/mcm/check?token=X3Nlc3Npb25faWQ9IlpEVTNOek01WTJSbVlUYzBOR0l6WldJME5qZGpZbUZoWVRObU1UTXlNVFU9fDEzNDQyNDAwNDd8NDlhMDhmM2NlZjAzNzkzMzY1Yzg1ZGMxZmQ2MmUzMjFlZDNlZmRiMSI7IGV4cGlyZXM9V2VkLCAwNSBTZXAgMjAxMiAwODowMDo0NyBHTVQ7IFBhdGg9Lw==&uid=4ff56539b322d01f1b000001&";
	public static String Hosts = "http://192.168.1.160:8080";
	public static String Hostst = "http://192.168.1.172:8888";

	/**
	 * 初始化路径,无则创建
	 * 
	 * @param filepath
	 */
	public static void initPath(String filepath) {
		File mypic_dirs = new File(filepath);
		if (!mypic_dirs.exists()) {
			mypic_dirs.mkdirs();
		}
	}

	/**
	 * 转化时间格式
	 * 
	 * @param timelnMillis
	 * @return
	 */
	public static String getTimeString(long timelnMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timelnMillis);
		Date date = calendar.getTime();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String newTypeDate = f.format(date);
		return newTypeDate;
	}

	/**
	 * 获取所需当前时间
	 * 
	 * @return
	 */
	public static long getNowTime() {
		return Calendar.getInstance().getTime().getTime();
	}


	/**
	 * 获取更新地址，如果没有更新地址为null
	 * 
	 * @return
	 */
	public static String getUpdatePath() {

		try {
			JSONObject appUpdate = ClientApi.getAppUpdate();
			int verSion = appUpdate.getInt("varcode");
			if (Util.getAppVersionInfo(ClientApp.mContext).versionCode < verSion) {
				return appUpdate.getString("app_path");
			} else {
				return null;
			}

		} catch (JSONException e) {
			return null;
		} catch (HttpRequstError e) {
			return null;
		}
	}

	/**
	 * 返回当前程序版本信息
	 */
	public static PackageInfo getAppVersionInfo(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			return pi;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * This paragraph excerpt github/android
	 * 
	 * Show the given message in a {@link Toast}
	 * <p>
	 * This method may be called from any thread
	 * 
	 * @param activity
	 * @param message
	 */
	public static void show(final Activity activity, final String message) {
		if (activity == null)
			return;

		if (TextUtils.isEmpty(message))
			return;

		final Application application = activity.getApplication();
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(application, message, LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * Show the message with the given resource id in a {@link Toast}
	 * <p>
	 * This method may be called from any thread
	 * 
	 * @param activity
	 * @param resId
	 */
	public static void show(final Activity activity, final int resId) {
		if (activity == null)
			return;
		show(activity, activity.getString(resId));
	}

	/**
	 * Toggles the view's visibility between {@link View#VISIBLE} and
	 * {@link View#GONE} depending on the given gone flag
	 * 
	 * @param view
	 * @param gone
	 */
	public static void setGone(final View view, final boolean gone) {
		if (view == null)
			return;

		final int current = view.getVisibility();
		if (gone && current != GONE)
			view.setVisibility(GONE);
		else if (!gone && current != VISIBLE)
			view.setVisibility(VISIBLE);
	}

}
