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
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.minihelper.core.UpdateAppUtil;

public class MainActivity extends Activity implements OnClickListener {
	private double mLongitude;
	private double mLatitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 定位经纬度
		Location l = ClientApp.mBaseLocation.getLocation();
		if (l != null) {
			mLongitude = l.getLongitude();
			mLatitude = l.getLatitude();
		} else {
			Toast.makeText(this, "获取经纬度失败", Toast.LENGTH_LONG).show();
		}
		init();

		String updateflag = ClientApp.mPref.getString("updateApp", "0");
		if (!updateflag.equals("1")) {
			new UpdateAppUtil(this);
		}
	}

	private void init() {
		Button button = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		TextView textView = (TextView) findViewById(R.id.textView1);

		button.setOnClickListener(this);
		button2.setOnClickListener(this);

		textView.setText("经度：" + mLongitude + "纬度：" + mLatitude);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button1:
				Intent intent = new Intent(this, ProessTouchAct.class);
				intent.putExtra("ImageUrl", "http://img2.duitang.com/uploads/item/201203/26/20120326204202_YsuTE.jpeg");
				startActivity(intent);
				break;
			case R.id.button2:
	
				break;

		default:
			break;
		}
	}
}
