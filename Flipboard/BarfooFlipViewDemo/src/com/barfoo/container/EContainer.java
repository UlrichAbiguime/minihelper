package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class EContainer extends AContainer implements IContainer{

	public EContainer(Context context) {
		super(context);
	}

	public EContainer(Context context,AttributeSet attrs ,JSONObject obj) {
		super(context,attrs, obj);
	}
	
	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH, 0.6,1.0/3);
		
		int itemviewW=ViewUtil.getViewWidth(this);
		int itemviewH=ViewUtil.getViewHeight(this);
		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.04, 0, 0.02, 0);//设置item的padding
		
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.93, 0.23);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH,0.93, 0.13);
		//ViewUtil.setViewPadding(ll_title, ViewUtil.getViewWidth(ll_title), ViewUtil.getViewHeight(ll_title), 0.05, 0,0.01, 0);
		//ViewUtil.setViewPadding(ll_source, ViewUtil.getViewWidth(ll_source), ViewUtil.getViewHeight(ll_source), 0.01,0, 0.01, 0);
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.4, 0.64);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.53, 0.64);
				ViewUtil.setViewPadding(ll_image,ViewUtil.getViewWidth(ll_image),ViewUtil.getViewHeight(ll_image), 0.05, 0, 0, 0);
				iv_image.setImageResource(R.drawable.loginbg);
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
		return R.layout.containeritemd;
	}

}
