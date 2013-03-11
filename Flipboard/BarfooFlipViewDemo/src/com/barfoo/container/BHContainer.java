/**
 * BHContainer((为DContainer的横屏版式)6个item适用的版式)
 *   
 * ————|————
 * ————|————
 * 
 * 设计：采用containeritemd.xml的版式,计算是横屏锁适应的版式
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

public class BHContainer extends AContainer implements IContainer{

	public BHContainer(Context context) {
		super(context);
	}
	
	public BHContainer(Context context, AttributeSet attrs,JSONObject json) {
		super(context, attrs, json);
		mContext = context;
	}
	
	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH,0.33, 0.5);//0.6/0.5
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);

		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.04, 0, 0.04, 0);//设置item的padding 0.04, 0, 0.04, 0
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.91, 0.23);//0.92, 0.23
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH,0.91, 0.13);//0.91, 0.13
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.36, 0.64);//0.36, 0.64/0.4, 0.64
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.55, 0.64);//0.55, 0.64
				ViewUtil.setViewPadding(ll_image,ViewUtil.getViewWidth(ll_image),ViewUtil.getViewHeight(ll_image), 0.05, 0, 0, 0);//0.05, 0, 0, 0
				iv_image.setImageResource(R.drawable.loginbg);
			} else {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH,0.91, 0.64);//0.91, 0.64
				ll_image.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getXmlResource() {
		return R.layout.containeritemd;
	}

}
