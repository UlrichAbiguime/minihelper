package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class JContainer extends AContainer{

	public JContainer(Context context) {
		super(context);
	}

	public JContainer(Context context, AttributeSet attrs,JSONObject json) {
		super(context, attrs, json);
		mContext = context;
	}
	
	@Override
	public void buildView(JSONObject json) {

		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH, 0.4,0.5);
		int itemviewW=ViewUtil.getViewWidth(this);
		int itemviewH=ViewUtil.getViewHeight(this);
		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.04, 0, 0.02, 0);//设置item的padding
		
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.93, 0.14);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH,0.93, 0.08);
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.38, 0.78);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH,0.55, 0.78);
				ViewUtil.setViewPadding(ll_image,ViewUtil.getViewWidth(ll_image),ViewUtil.getViewHeight(ll_image), 0.05, 0, 0, 0);
				iv_image.setImageResource(R.drawable.loginbg);
			} else {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH,0.93, 0.78);
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
