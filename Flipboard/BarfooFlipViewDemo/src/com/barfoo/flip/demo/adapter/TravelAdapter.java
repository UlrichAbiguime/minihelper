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

import com.barfoo.formatstyle.AFormateStyle;

public class TravelAdapter extends BaseAdapter {

	 
	private Context mContext;

	private JSONArray mJsonArray;
	
	private int repeatCount = 1;

	public TravelAdapter(Context context, JSONArray jsonArray) {
		this.mContext=context;
		this.mJsonArray=jsonArray;

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
		return new AFormateStyle(mContext,null,mJsonArray);
	}
}
