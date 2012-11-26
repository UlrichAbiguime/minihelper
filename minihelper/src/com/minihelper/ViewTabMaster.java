/**
 *
 *@Class ViewTabMaster.java
 *@author zxy
 *@date 2012-2012-8-20-下午4:31:00
 *@Description 说明
 */
package com.minihelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;

import com.minihelper.ui.ViewTabAct;

public class ViewTabMaster extends ViewTabAct {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewbottomtab);
		_create(savedInstanceState, this);
		
		AddActivity("第一个act", MainActivity.class);
		AddActivity("第二个act", ViewPageMaster.class);
		AddActivity("第三个act", ViewTopTabAct.class);
		AddActivity("第四个act", SidePageActivity.class);
		
		
		startServiceIntent();
	}

	private void startServiceIntent() {
		AlarmManager am = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
		Intent intent = new Intent(this, MiniServer.class);
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
		long interval = DateUtils.MINUTE_IN_MILLIS * 1;
		long firstWake = System.currentTimeMillis() + interval;
		am.setRepeating(AlarmManager.RTC,firstWake, interval, pendingIntent);
	}
}
