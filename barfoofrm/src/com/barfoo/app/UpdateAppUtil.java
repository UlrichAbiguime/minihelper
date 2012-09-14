/**
 *
 *@Class UpdateUtil.java
 *@author zxy
 *@date 2012-2012-7-18-上午9:39:28
 *@Description 说明
 */
package com.barfoo.app;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;

import com.barfoo.core.AsyncRunner;
import com.barfoo.core.BaseRequestListener;
import com.barfoo.logic.BarfooApi;

public class UpdateAppUtil {
	String app_path = null;
	UpdateManager mUpdateManager;
	Timer timer = new Timer();
	Context mContext;

	public UpdateAppUtil(Context context) {
		this.mContext = context;
	}

	public UpdateAppUtil(Context context, int times) {
		this.mContext = context;
		timer.schedule(task, times);
	}

	public void StartTimer(int times) {
		timer.schedule(task, times);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				update_app();
				break;
			case 2:
				if (app_path != null) {
					mUpdateManager = new UpdateManager(mContext, app_path);
					mUpdateManager.checkUpdateInfo();
				}
			}
			super.handleMessage(msg);
		}
	};

	private void update_app() {
		AsyncRunner.HttpGet(new BaseRequestListener() {
			@Override
			public void onRequesting() throws BarfooError, JSONException {
				app_path = UpdateAppUtil.getUpdatePath();
				Message msg = new Message();
				msg.what = 2;
				handler.sendMessage(msg);
			}
		});
	}

	TimerTask task = new TimerTask() {
		public void run() {
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	};
	
	/**
	 * 获取更新地址，如果没有更新地址为null
	 * 
	 * @return
	 */
	public static String getUpdatePath() {

		try {
			JSONObject appUpdate = BarfooApi.getAppUpdate();
			int verSion = appUpdate.getInt("varcode");
			if (UpdateAppUtil.getAppVersionInfo(BarfooApp.mContext).versionCode < verSion) {
				return appUpdate.getString("app_path");
			} else {
				return null;
			}

		} catch (JSONException e) {
			return null;
		} catch (BarfooError e) {
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

}
