/**
 * AContainerFormate(为AFormateStyle的竖屏版式)
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
		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.02, 0, 0.04, 0);
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.93, 0.13);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH,0.93, 0.08);
		//ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.45, 0.65);
		//ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.55, 0.65);
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.4, 0.79);//0.65
				ViewUtil.setViewPadding(ll_image, ViewUtil.getViewWidth(ll_image),  ViewUtil.getViewHeight(ll_image), 0, 0, 0.05, 0);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.53, 0.79);//0.65

			} else {
				ll_image.setVisibility(View.GONE);
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.93, 0.79);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getXmlResource() {
		return R.layout.containeritemc;
	}
	
	public void setTextLin() {
		tv_content.setMaxLines(10);
		tv_content.setEllipsize(TruncateAt.END);
	}
	
}
