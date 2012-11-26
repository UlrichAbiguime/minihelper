/**
 *
 *@Class UpdateUtil.java
 *@author zxy
 *@date 2012-2012-7-18-上午9:39:28
 *@Description 说明
 */
package com.minihelper.core;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

import com.minihelper.UpdateManager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class UpdateAppUtil {
	String app_path = null;
	UpdateManager mUpdateManager;
	Timer timer = new Timer();
	Context mContext;
	public static boolean isUpdate=false;

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
					mUpdateManager.checkUpdateInfo(true);
				}else {
					if (isUpdate) {
						Toast.makeText(mContext, "您当前是最新版本！", Toast.LENGTH_LONG).show();
					}
				}
			}
			super.handleMessage(msg);
		}
	};

	private void update_app() {
		AsyncRunner.HttpGet(new BaseRequestListener() {
			@Override
			public void onRequesting() throws HttpRequstError, JSONException {
				app_path = Util.getUpdatePath();
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

}
