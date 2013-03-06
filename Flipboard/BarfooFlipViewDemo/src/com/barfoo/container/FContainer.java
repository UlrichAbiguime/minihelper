package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class FContainer extends AContainer implements IContainer{
	Context mContext;
	JSONObject mobj;
	public FContainer(Context context) {
		super(context);
	}
	
	
	public FContainer(Context context,AttributeSet attrs ,JSONObject obj) {
		super(context,attrs, obj);
		mContext=context;
	}

	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH, 0.6,1.0/3);
		
		int itemviewW=ViewUtil.getViewWidth(this);
		int itemviewH=ViewUtil.getViewHeight(this);
		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.04, 0, 0.02, 0.01);//设置item的padding
		
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.94, 0.25);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 0.94, 0.13);
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.94, 0.31);
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.94, 0.3);
				iv_image.setImageResource(R.drawable.loginbg);
			} else {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.94, 0.61);
				ll_image.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	@Override
	public int getXmlResource() {
		return R.layout.containeritemb;
	}
}
