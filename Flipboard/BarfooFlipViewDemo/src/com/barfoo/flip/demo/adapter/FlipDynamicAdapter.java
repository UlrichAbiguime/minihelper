/**
 * Copyright 2013 Barfoo
 * 
 * All right reserved
 * 
 * Created on 2013-2-27 上午10:14:31
 * 
 * @author zxy
 */
package com.barfoo.flip.demo.adapter;

import org.json.JSONArray;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;
import com.barfoo.formatstyle.AFormateStyle;
import com.barfoo.formatstyle.BFormateStyle;
import com.barfoo.formatstyle.LayoutFormat;

public class FlipDynamicAdapter extends BaseAdapter {

	private Context mContext;

	private JSONArray mJsonArray;

	private int repeatCount = 1;

	public FlipDynamicAdapter(Context context, JSONArray jsonArray) {
		this.mContext = context;
		this.mJsonArray = jsonArray;

	}

	@Override
	public int getCount() {
		return repeatCount;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
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
		LayoutFormat layoutFormat = new LayoutFormat(mContext);
		ImageView[] imageViews = new ImageView[getCount()];
		ImageView image = new ImageView(mContext);

		for (int i = 0; i < getCount(); i++) {
			image = new ImageView(mContext);
			image.setLayoutParams(new LayoutParams(20, 20));
			image.setPadding(20, 0, 20, 0);
			imageViews[i] = image;

			if (i == 0) {
				image.setBackgroundResource(R.drawable.feed_taggeduser_image);
			} else {
				image.setBackgroundResource(R.drawable.ic_launcher);
			}

			layoutFormat.getFooderLinear().addView(image);
		}

		ViewUtil.setViewWidHeight(layoutFormat.getHeaderLinear(), 1, 0.1);
		ViewUtil.setViewWidHeight(layoutFormat.getFragmentLinear(), 1, 0.8);
		ViewUtil.setViewWidHeight(layoutFormat.getFooderLinear(), 1, 0.1);
		ViewUtil.trueScreenH = ViewUtil.getViewHeight(layoutFormat.getFragmentLinear());
		ViewUtil.trueScreenW = ViewUtil.getViewWidth(layoutFormat.getFragmentLinear());

		if (position % 2 == 0) {
			//layoutFormat.getFragmentLinear().addView(new BFormateStyle(mContext, null, mJsonArray));
		} else {
			layoutFormat.getFragmentLinear().addView(new AFormateStyle(mContext, null, mJsonArray));
		}

		return layoutFormat;
	}
}
