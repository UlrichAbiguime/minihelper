/**
 * AHContainer(为AFormateStyle的横屏版式)
 * 设计：采用containeritema.xml的版式,计算是横屏锁适应的版式
 * 功能:设定好container中的布局样式以及根据布局样式自动填充数据
 */
package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class AHContainer extends AContainer{

	public AHContainer(Context context) {
		super(context);
	}

	public AHContainer(Context context,AttributeSet attrs ,JSONObject obj) {
		super(context,attrs, obj);
		mContext=context;
	}

	
	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH, 0.4,1);//0.4/1
		int itemviewW=ViewUtil.getViewWidth(this);
		int itemviewH=ViewUtil.getViewHeight(this);
		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.04, 0, 0.03, 0);
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.92, 0.1);//1/0.15
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 0.92, 0.07);//1/0.05
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH,0.92, 0.35);//1/0.35
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.92,0.48);//1/0.45

			} else {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.92, 0.83);//1/0.8
				ll_image.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getXmlResource() {
		return R.layout.containeritema;
	}
}
