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
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.barfoo.flip.FlipViewController;
import com.barfoo.flip.demo.adapter.FlipDynamicAdapter;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class FlipDynamicActivity extends Activity {
	private FlipViewController flipView;

	private FlipDynamicAdapter adapter;

	private Resources res;

	private JSONArray array;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewUtil.screenInfo(this);// 获取屏幕的宽高

		res = getResources();

		getData();

		flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);
		flipView.setBackgroundColor(Color.WHITE);
		adapter = new FlipDynamicAdapter(this, array);
		flipView.setAdapter(adapter);

		flipView.setOnViewFlipListener(new FlipViewController.ViewFlipListener() {
			@Override
			public void onViewFlipped(View view, int position) {
				if (position == adapter.getCount() - 1) {
					adapter.setRepeatCount(adapter.getRepeatCount() + 3);
					adapter.notifyDataSetChanged();
				}
			}
		});

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

	public void getData() {

		array = new JSONArray();
		try {
			JSONObject obj0 = new JSONObject();
			obj0.put("title", res.getString(R.string.Adatatitle));
			obj0.put("sourceimage", "sourceimage");
			obj0.put("source", res.getString(R.string.Adatasource));
			obj0.put("content", res.getString(R.string.Adatacontent));
			obj0.put("titleimage", "");

			JSONObject obj1 = new JSONObject();
			obj1.put("title", res.getString(R.string.Bdatatitle));
			obj1.put("sourceimage", "sourceimage");
			obj1.put("source", res.getString(R.string.Bdatasource));
			obj1.put("content", res.getString(R.string.Bdatacontent));
			obj1.put("titleimage", "");

			JSONObject obj2 = new JSONObject();
			obj2.put("title", res.getString(R.string.Cdatatitle));
			obj2.put("sourceimage", "sourceimage");
			obj2.put("source", res.getString(R.string.Cdatasource));
			obj2.put("content", res.getString(R.string.Cdatacontent));
			obj2.put("titleimage", "sourceimage");

			JSONObject obj3 = new JSONObject();
			obj3.put("title", res.getString(R.string.Ddatatitle));
			obj3.put("sourceimage", "sourceimage");
			obj3.put("source", res.getString(R.string.Ddatasource));
			obj3.put("content", res.getString(R.string.Ddatacontent));
			obj3.put("titleimage", "");

			JSONObject obj4 = new JSONObject();
			obj4.put("title", res.getString(R.string.Edatatitle));
			obj4.put("sourceimage", "sourceimage");
			obj4.put("source", res.getString(R.string.Edatasource));
			obj4.put("content", res.getString(R.string.Edatacontent));
			obj4.put("titleimage", "");

			JSONObject obj5 = new JSONObject();
			obj5.put("title", res.getString(R.string.Fdatatitle));
			obj5.put("sourceimage", "sourceimage");
			obj5.put("source", res.getString(R.string.Fdatasource));
			obj5.put("content", res.getString(R.string.Fdatacontent));
			obj5.put("titleimage", "");

			array.put(obj0);
			array.put(obj1);
			array.put(obj2);
			array.put(obj3);
			array.put(obj4);
			array.put(obj5);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
