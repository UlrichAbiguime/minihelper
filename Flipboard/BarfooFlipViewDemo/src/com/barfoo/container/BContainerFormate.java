/**
 * BContainerFormate
 * 功能:设定好container中的布局样式以及根据布局样式自动填充数据
 */
package com.barfoo.container;

import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;

import com.barfoo.flip.demo.data.ViewUtil;

public class BContainerFormate extends AContainerFormate implements IContainer{

	Context mContext;
	JSONObject mobj;
	public BContainerFormate(Context context) {
		super(context);
	}
	
	public BContainerFormate(Context context,AttributeSet attrs ,JSONObject obj) {
		super(context,attrs, obj);
		mContext=context;
		//LayoutInflater.from(mContext).inflate(R.layout.containeritemb, this, true);
		mobj=obj;
		buildView(obj);
	}

	@Override
	public void buildView(JSONObject json) {
		super.buildView(json);
		ViewUtil.setViewWidHeight(this, 0.5, 0.6);
		int itemviewW=ViewUtil.getViewWidth(this);
		int itemviewH=ViewUtil.getViewHeight(this);
		
		ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 1, 0.45);
		//ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 1, 0.2);
		//ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 1, 0.05);
		//ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 1, 0.3);
		
		//ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 1, 0.5);
		

	}
}
