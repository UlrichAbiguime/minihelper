package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class DContainer extends AContainer implements IContainer {

	Context mContext;

	public DContainer(Context context) {
		super(context);
	}

	public DContainer(Context context, AttributeSet attrs,JSONObject json) {
		super(context, attrs, json);
		mContext = context;
	}
	
	@Override
	public void buildView(JSONObject json) {
		//ViewUtil.setViewWidHeight(this,0.5, 0.346);// 竖屏0.35
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH,  0.5, 1.0/3);// 竖屏0.35
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);

		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 1, 0.25);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 1, 0.05);
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.5, 0.7);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.5, 0.7);
				ViewUtil.setViewPadding(ll_image,ViewUtil.getViewWidth(ll_image),ViewUtil.getViewHeight(ll_image), 0.05, 0, 0, 0);
				iv_image.setImageResource(R.drawable.loginbg);
			} else {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 1, 0.7);
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

	public void setTextLin() {
		tv_content.setMaxLines(10);
		tv_content.setEllipsize(TruncateAt.END);
	}
}
