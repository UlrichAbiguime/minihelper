/**
 * Copyright 2013 Barfoo
 * 
 * All right reserved
 * 
 * Created on 2013-2-27 上午10:14:31
 * 
 * @author zxy
 */
package com.barfoo.flip.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.barfoo.flip.FlipViewController;
import com.barfoo.flip.demo.adapter.FlipDynamicAdapter;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class FlipDynamicActivity extends Activity {
	public FlipViewController flipView;

	private FlipDynamicAdapter adapter;

	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewUtil.screenInfo(this);// 获取屏幕的宽高
		
		

		flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);
		flipView.setBackgroundColor(Color.WHITE);
		adapter = new FlipDynamicAdapter(FlipDynamicActivity.this,flipView);
		adapter.setRepeatCount(10);
		flipView.setAdapter(adapter);
		
		flipView.setOnViewFlipListener(new FlipViewController.ViewFlipListener() {
			@Override
			public void onViewFlipped(View view, int position) {
				
			}
		});

		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			//adapter.setChangeScreenValue(0);
			adapter.notifyDataSetChanged();
			//adapter.screenRefresh();
			Log.e("change", "hengping");
			//Log.e("change", "shuping");
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			//adapter.setChangeScreenValue(1);
			adapter.notifyDataSetChanged();
			Log.e("change", "shuping");
		}
		setContentView(flipView);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		flipView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		flipView.onPause();
	}

	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		ViewUtil.screenInfo(this);// 获取屏幕的宽高
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			//adapter.setChangeScreenValue(0);
			adapter.notifyDataSetChanged();
			//adapter.screenRefresh();
			Log.e("change", "hengping");
			//Log.e("change", "shuping");
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			//adapter.setChangeScreenValue(1);
			adapter.notifyDataSetChanged();
			Log.e("change", "shuping");
		}
		
		super.onConfigurationChanged(newConfig);
		/**
		
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			adapter.setChangeScreenValue(0);
			adapter.notifyDataSetChanged();
			//adapter.screenRefresh();
			Log.e("change", "hengping");
			
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			adapter.setChangeScreenValue(1);
			adapter.notifyDataSetChanged();
			Log.e("change", "shuping");
		}
		**/

		/**
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Log.e("onConfigurationChanged", "hengping");
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			Log.e("onConfigurationChanged", "shuping");
		}
		**/
	}
}
