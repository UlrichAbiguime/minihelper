/**
 * BHContainer
 * 设计：采用containeritemb.xml的版式,计算是横屏锁适应的版式
 * 功能:设定好container中的布局样式以及根据布局样式自动填充数据
 */
package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class CHContainer extends AContainer{

	public CHContainer(Context context) {
		super(context);
	}

	public CHContainer(Context context,AttributeSet attrs ,JSONObject obj) {
		super(context,attrs, obj);
		mContext=context;
	}

	
	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH,  0.6, 0.5);//0.6/0.5
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);

		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.02, 0, 0.04, 0);//设置item的padding
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.93, 0.23);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH,0.93, 0.13);
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH,0.4, 0.64);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.55, 0.64);
				ViewUtil.setViewPadding(ll_image,ViewUtil.getViewWidth(ll_image),ViewUtil.getViewHeight(ll_image),0, 0, 0.05, 0);
			} else {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH,0.93, 0.64);
				ll_image.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public int getXmlResource() {
		return R.layout.containeritemc;
	}
}
