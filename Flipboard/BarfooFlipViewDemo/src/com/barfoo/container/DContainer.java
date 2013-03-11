/**
 * DContainer(为BFormateStyle的竖屏版式)
 * 设计：采用containeritemd.xml的版式,计算是竖屏锁适应的版式
 * 功能:设定好container中的布局样式以及根据布局样式自动填充数据
 */
package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

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
		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.04, 0, 0.04, 0);
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.91, 0.25);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 0.91, 0.13);
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.4, 0.62);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.51,  0.62);
				ViewUtil.setViewPadding(ll_image,ViewUtil.getViewWidth(ll_image),ViewUtil.getViewHeight(ll_image), 0.05, 0, 0, 0);
				iv_image.setImageResource(R.drawable.loginbg);
			} else {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.91,  0.62);
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
