/**
 *
 *@Class ViewTabMaster.java
 *@author zxy
 *@date 2012-2012-8-20-下午4:31:00
 *@Description 说明
 */
package com.minihelper;

import android.os.Bundle;

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
	}
}
