/**
 *
 *@Class MainActivity.java
 *@author zxy
 *@date 2012-2012-9-13-下午5:07:25
 *@Description 说明
 */
package com.barfoo.demo;

import com.barfoo.R;
import com.barfoo.app.UpdateAppUtil;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		new UpdateAppUtil(this, 3000);
	}
}
