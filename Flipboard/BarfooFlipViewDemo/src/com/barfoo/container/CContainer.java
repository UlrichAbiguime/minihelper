/**
 * AContainerFormate
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

public class CContainer extends AContainer implements IContainer {

	Context mContext;
	
	public CContainer(Context context) {
		super(context);
	}

	public CContainer(Context context, AttributeSet attrs,JSONObject json) {
		super(context, attrs, json);
		mContext = context;
	}

	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW,ViewUtil.trueScreenH, 0.5, 0.6);
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 1, 0.2);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 1, 0.05);
		ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.45, 0.65);
		ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.55, 0.65);
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.45, 0.65);
				ViewUtil.setViewPadding(ll_image, ViewUtil.getViewWidth(ll_image),  ViewUtil.getViewHeight(ll_image), 0.05, 0, 0, 0);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.55, 0.65);

			} else {
				ll_image.setVisibility(View.GONE);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 1, 0.65);
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
