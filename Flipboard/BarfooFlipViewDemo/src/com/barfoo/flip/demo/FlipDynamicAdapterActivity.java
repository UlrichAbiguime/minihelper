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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.barfoo.flip.FlipViewController;
import com.barfoo.flip.demo.adapter.TravelAdapter;

public class FlipDynamicAdapterActivity extends Activity {
	private FlipViewController flipView;

	private TravelAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);

		adapter = new TravelAdapter(this);
		flipView.setAdapter(adapter);

		flipView.setOnViewFlipListener(new FlipViewController.ViewFlipListener() {
			@Override
			public void onViewFlipped(View view, int position) {
				if (position == adapter.getCount() - 1) {
					adapter.setRepeatCount(adapter.getRepeatCount() + 5);
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
}
