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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;
import com.barfoo.formatstyle.AFormateStyle;
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

		for (int i = 0; i <= getCount(); i++) {
			ImageView image = new ImageView(mContext);
			if (i == position) {
				image.setBackgroundResource(R.drawable.feed_taggeduser_image);
			} else {
				image.setBackgroundResource(R.drawable.ic_launcher);
			}

			image.setLayoutParams(new LinearLayout.LayoutParams(32, 32));
			layoutFormat.getFooderLinear().addView(image);
		}
		ViewUtil.setViewWidHeight(layoutFormat.getHeaderLinear(), 1, 0.1);
		ViewUtil.setViewWidHeight(layoutFormat.getFragmentLinear(), 1, 0.8);
		ViewUtil.setViewWidHeight(layoutFormat.getFooderLinear(), 1, 0.1);
		ViewUtil.trueScreenH=ViewUtil.getViewHeight(layoutFormat.getFragmentLinear());
		ViewUtil.trueScreenW=ViewUtil.getViewWidth(layoutFormat.getFragmentLinear());
		layoutFormat.getFragmentLinear().addView(new AFormateStyle(mContext, null, mJsonArray));
		
		return layoutFormat;
	}
}
