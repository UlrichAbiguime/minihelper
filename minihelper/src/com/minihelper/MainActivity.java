/**
 *
 *@Class MainActivity.java
 *@author zxy
 *@date 2012-2012-8-3-下午4:26:51
 *@Description 说明
 */
package com.minihelper;

import com.minihelper.core.UpdateAppUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		init();

		String updateflag = ClientApp.mPref.getString("updateApp", "0");
		if (!updateflag.equals("1")) {
			new UpdateAppUtil(this);
		}
	}

	private void init() {
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent TouchAct = new Intent(this, TouchImage.class);
			TouchAct.putExtra("ImageUrl", "http://hiphotos.baidu.com/tkj19860924/pic/item/9e4526f3679afe960a46e09e.jpg");
			startActivity(TouchAct);
			break;

		default:
			break;
		}
	}
}
