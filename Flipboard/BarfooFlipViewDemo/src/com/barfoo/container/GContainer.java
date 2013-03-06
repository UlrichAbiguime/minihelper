package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class GContainer extends AContainer implements IContainer {

	Context mContext;
	
	public GContainer(Context context) {
		super(context);
	}

	public GContainer(Context context, AttributeSet attrs,JSONObject json) {
		super(context, attrs, json);
		mContext = context;
	}

	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW,ViewUtil.trueScreenH, 0.6, 1.0/3);
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);
		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.04, 0, 0.02, 0);//设置item的padding
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.94, 0.25);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 0.94, 0.13);
		
		//ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.45, 0.65);
		//ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.55, 0.65);
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.4, 0.62);
				ViewUtil.setViewPadding(ll_image, ViewUtil.getViewWidth(ll_image),  ViewUtil.getViewHeight(ll_image),0, 0, 0.05, 0);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.53, 0.62);

			} else {
				ll_image.setVisibility(View.GONE);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 1, 0.62);
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
