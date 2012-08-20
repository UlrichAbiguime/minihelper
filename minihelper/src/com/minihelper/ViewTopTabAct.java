/**
 *
 *@Class ViewTopTabAct.java
 *@author zxy
 *@date 2012-2012-8-20-下午5:06:44
 *@Description 说明
 */
package com.minihelper;

import android.os.Bundle;

public class ViewTopTabAct extends ViewTabMaster {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewtoptab);
		_create(savedInstanceState, this);
		
		AddActivity("第一个act", TreeViewMaster.class);
		AddActivity("第二个act", ViewPageMaster.class);
		AddActivity("第三个act", TreeListAct.class);
	}
}
