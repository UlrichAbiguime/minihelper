/**
 * BContainerFormate
 * 功能:设定好container中的布局样式以及根据布局样式自动填充数据
 */
package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class BContainer extends AContainer implements IContainer{

	Context mContext;
	JSONObject mobj;
	public BContainer(Context context) {
		super(context);
	}
	
	
	public BContainer(Context context,AttributeSet attrs ,JSONObject obj) {
		super(context,attrs, obj);
		mContext=context;
	}

	
	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH, 0.5, 0.6);//
		int itemviewW=ViewUtil.getViewWidth(this);
		int itemviewH=ViewUtil.getViewHeight(this);
		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.04, 0, 0.02, 0);
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.93, 0.13);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH,  0.93, 0.08);
		try {
			if(Util.isJsonNull(json, "titleimage")){
				
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH,  0.93, 0.38);
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH,  0.93, 0.35);
			}else{
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH,  0.93, 0.7);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int getXmlResource() {
		return R.layout.containeritemb;
	}
}
