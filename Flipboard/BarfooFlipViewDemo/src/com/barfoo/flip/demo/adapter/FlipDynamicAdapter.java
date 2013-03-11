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
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.barfoo.flip.FlipViewController;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;
import com.barfoo.formatstyle.FormatMaster;
import com.barfoo.formatstyle.IFormat;
import com.barfoo.formatstyle.LayoutFormat;

public class FlipDynamicAdapter extends BaseAdapter {

	private Context mContent;

	private JSONArray mJsonArray;

	private int repeatCount = 1;

	FlipViewController mFlipViewController;

	private IFormat createFormatView;
	
	private Resources res;

	private JSONArray array;

	private JSONArray arrays;
	

	public FlipDynamicAdapter(Context context, FlipViewController flipView) {
		this.mFlipViewController = flipView;
		this.mContent = context;
		res = mContent.getResources();
		getData();
		mJsonArray=array;
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
		if (position==3) {
			mJsonArray=arrays;
		}
		
		LayoutFormat layoutFormat = new LayoutFormat(mContent);
		final ImageView[] imageViews = new ImageView[getCount()];
		ImageView image = new ImageView(mContent);
		for (int i = 0; i < getCount(); i++) {
			image = new ImageView(mContent);
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
		ViewUtil.trueScreenH = ViewUtil.getViewHeight(layoutFormat.getFragmentLinear());
		ViewUtil.trueScreenW = ViewUtil.getViewWidth(layoutFormat.getFragmentLinear());


		
		try {
			createFormatMaster = FormatMaster.createFormatMaster(mContent, mJsonArray);
		} catch (JSONException e) {
			Toast.makeText(mContent, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		layoutFormat.getFragmentLinear().addView(createFormatMaster);
		return layoutFormat;
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

	/**
	 * 获取当前的activity是横屏还是竖屏
	 * @param HorizonVerticalScreen :0为 横屏，1为竖屏
	 * 
	 */
	public int changeScreenValue;

	private View createFormatMaster;

	public int getChangeScreenValue() {
		return changeScreenValue;
	}

	public void setChangeScreenValue(int changeScreenValue) {
		this.changeScreenValue = changeScreenValue;
	}
	
	public void addDatalist(JSONArray jsonArray) {
		mJsonArray=jsonArray;
	}

	
	
	public void getData() {

		array = new JSONArray();
		arrays = new JSONArray();
		
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
			obj1.put("titleimage", "xx");


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
			obj3.put("titleimage", "xx");

			JSONObject obj4 = new JSONObject();
			obj4.put("title", res.getString(R.string.Edatatitle));
			obj4.put("sourceimage", "sourceimage");
			obj4.put("source", res.getString(R.string.Edatasource));
			obj4.put("content", res.getString(R.string.Edatacontent));
			obj4.put("titleimage", "xx");

			JSONObject obj5 = new JSONObject();
			obj5.put("title", res.getString(R.string.Fdatatitle));
			obj5.put("sourceimage", "sourceimage");
			obj5.put("source", res.getString(R.string.Fdatasource));
			obj5.put("content", res.getString(R.string.Fdatacontent));
			obj5.put("titleimage", "xx");
			
			JSONObject obj6 = new JSONObject();
			obj5.put("title", res.getString(R.string.Fdatatitle));
			obj5.put("sourceimage", "sourceimage");
			obj5.put("source", res.getString(R.string.Fdatasource));
			obj5.put("content", res.getString(R.string.Fdatacontent));
			obj5.put("titleimage", "xx");
			
			JSONObject obj7 = new JSONObject();
			obj5.put("title", res.getString(R.string.Fdatatitle));
			obj5.put("sourceimage", "sourceimage");
			obj5.put("source", res.getString(R.string.Fdatasource));
			obj5.put("content", res.getString(R.string.Fdatacontent));
			obj5.put("titleimage", "xx");
			
			JSONObject obj8 = new JSONObject();
			obj5.put("title", res.getString(R.string.Fdatatitle));
			obj5.put("sourceimage", "sourceimage");
			obj5.put("source", res.getString(R.string.Fdatasource));
			obj5.put("content", res.getString(R.string.Fdatacontent));
			obj5.put("titleimage", "xx");
			
			arrays.put(obj0);
			arrays.put(obj1);
			arrays.put(obj2);
			array.put(obj3);
			array.put(obj4);
			array.put(obj5);
			array.put(obj6);
			array.put(obj7);
			array.put(obj8);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
