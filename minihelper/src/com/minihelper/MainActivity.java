/**
 *
 *@Class MainActivity.java
 *@author zxy
 *@date 2012-2012-8-3-下午4:26:51
 *@Description 说明
 */
package com.minihelper;

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
	}

	private void init() {
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent TouchAct = new Intent(this, TouchAct.class);
			TouchAct.putExtra("ImageUrl", "http://192.168.1.160:8080/m/getimg?fid=501ba944931e33493800000f&token=X3Nlc3Npb25faWQ9Ik4yVXhZams0Tm1FeE5tSTVOR0U0WWpsa016TmtOR00zTUdGaVptUmpZakk9fDEzNDQyMjI5OTl8YTRjZjQ0MGI0ZGIzOTZhMjQ3MGUxMmFlNzI3ZDU5N2JlZjEwNDM2NyI7IGV4cGlyZXM9V2VkLCAwNSBTZXAgMjAxMiAwMzoxNjozOSBHTVQ7IFBhdGg9Lw==&uid=4ff56539b322d01f1b000001&");
			startActivity(TouchAct);
			break;

		default:
			break;
		}
	}
}
