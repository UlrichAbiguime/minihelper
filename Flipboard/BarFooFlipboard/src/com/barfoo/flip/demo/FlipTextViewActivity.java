/**
 * Copyright 2013 Barfoo
 *
 * All right reserved
 *
 * Created on 2013-2-27 上午9:31:17
 *
 * @author zxy
 */

package com.barfoo.flip.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.barfoo.flip.FlipViewController;
import com.barfoo.flip.FlipViewController.ViewFlipListener;
import com.barfoo.flipview.demo.R;

public class FlipTextViewActivity extends Activity implements ViewFlipListener {
	protected FlipViewController flipView;
	private MyBaseAdapter adapter;
	private int mCount = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		flipView = new FlipViewController(this);
		flipView.setOnViewFlipListener(this);

		setContentView(flipView);

		adapter = new MyBaseAdapter();
		adapter.setCount(mCount);
		flipView.setAdapter(adapter);
	}

	public class MyBaseAdapter extends BaseAdapter {

		int count;

		public void setCount(int count) {
			this.count = count;
		}

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			NumberTextView view;
			if (convertView == null) {
				final Context context = parent.getContext();
				view = new NumberTextView(context, position);
				view.setTextSize(context.getResources().getDimension(R.dimen.textSize));
			} else {
				view = (NumberTextView) convertView;
				view.setNumber(position);
			}

			return view;
		}
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
	public void onViewFlipped(View view, int position) {
		if (position == 0) {
			Toast.makeText(this, "这是第一页", Toast.LENGTH_SHORT).show();
		} else if (position == mCount - 1) {
			Toast.makeText(this, "这是最后一页", Toast.LENGTH_SHORT).show();
			mCount = (mCount += mCount);
			adapter.setCount(mCount);
			adapter.notifyDataSetInvalidated();
		}
	}
}
