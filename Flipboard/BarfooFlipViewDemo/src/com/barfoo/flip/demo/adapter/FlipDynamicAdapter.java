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

import java.lang.reflect.InvocationTargetException;

import org.json.JSONArray;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.barfoo.flip.FlipViewController;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;
import com.barfoo.formatstyle.AFormateStyle;
import com.barfoo.formatstyle.BFormateStyle;
import com.barfoo.formatstyle.CFormateStyle;
import com.barfoo.formatstyle.FormatMaster;
import com.barfoo.formatstyle.IFormat;
import com.barfoo.formatstyle.LayoutFormat;

public class FlipDynamicAdapter extends BaseAdapter {

	private Context mContext;

	private JSONArray mJsonArray;

	private int repeatCount = 1;

	FlipViewController mFlipViewController;

	private IFormat createFormatView;

	public FlipDynamicAdapter(Context context, JSONArray jsonArray, FlipViewController flipView) {
		this.mFlipViewController = flipView;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutFormat layoutFormat = new LayoutFormat(mContext);
		final ImageView[] imageViews = new ImageView[getCount()];
		ImageView image = new ImageView(mContext);
		for (int i = 0; i < getCount(); i++) {
			image = new ImageView(mContext);
			image.setLayoutParams(new LayoutParams(32, 32));
			imageViews[i] = image;
			if (i == position) {
				image.setBackgroundResource(R.drawable.radio_checked_down);
			} else {
				image.setBackgroundResource(R.drawable.radio_unchecked);
			}

			image.setOnClickListener(new FlipOnClickListener(i));
			layoutFormat.getFooderLinear().addView(image);
		}
		ViewUtil.setViewWidHeight(layoutFormat.getHeaderLinear(), 1, 0.05);
		ViewUtil.setViewWidHeight(layoutFormat.getFragmentLinear(), 1, 0.9);
		ViewUtil.setViewWidHeight(layoutFormat.getFooderLinear(), 1, 0.05);
		ViewUtil.trueScreenH=ViewUtil.getViewHeight(layoutFormat.getFragmentLinear());
		ViewUtil.trueScreenW=ViewUtil.getViewWidth(layoutFormat.getFragmentLinear());


		FormatMaster formatMaster = new FormatMaster();
		
		if (position % 2 == 0) {
			layoutFormat.getFragmentLinear().addView(new BFormateStyle(mContext, null, mJsonArray));
		} else {
			layoutFormat.getFragmentLinear().addView(new AFormateStyle(mContext, null, mJsonArray));
		}


		return (View) createFormatView;
	}

	public class FlipOnClickListener implements View.OnClickListener { // 标签点击监听事件
		private int indexpage;

		public FlipOnClickListener(int i) {
			indexpage = i;
		}

		@Override
		public void onClick(View v) {
			mFlipViewController.setSelection(indexpage);
		}
	};

}
