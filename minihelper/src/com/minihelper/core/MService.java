/**
 * BarfooService.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-8-20
 */
package com.minihelper.core;

import java.util.ArrayList;
import java.util.List;

import com.minihelper.ClientApp;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

/**
 * @author comger
 *
 */
public class MService extends Service {
	ServiceTask task;
	int spantime = 2;
	List<IMessager> msgList = new ArrayList<IMessager>();
	
	@Override
	public void onCreate() {
		super.onCreate();
		spantime = ClientApp.mPref.getInt("spantime", 2);
		task = new ServiceTask();
		task.execute(spantime);
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		Log.i("ppfserver", "onDestroy()");
		cleanSendTimerTask();
		super.onDestroy();
	}
	
	
	// 取消定时器
	private void cleanSendTimerTask() {
		if (task != null) {
			task.cancel(true);
		}
	}
	
	private NotificationManager getNotificationManager(){
		return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}
	
	//启动注册服务
	private void runService(){
		for(IMessager msger:msgList){
			try{
				int count = msger.countNewMessage();
				if(count>0){
					msger.showMessage(getNotificationManager(),count);
				}
			}catch (Exception e) {
				Log.i("IMessager", "Error:"+e.getMessage());
			}
		}
	}

	
	// 请求任务
	class ServiceTask extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			while (!isCancelled()) {
				try {
					Thread.sleep(spantime * 60 * 1000);
				} catch (InterruptedException e) {
					cleanSendTimerTask();
				}
				runService();
			}
			return null;
		}

	}
}
