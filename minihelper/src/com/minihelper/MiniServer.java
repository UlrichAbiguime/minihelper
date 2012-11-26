package com.minihelper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MiniServer extends Service {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.i("MiniServer", "serice");
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
