/**
 * BContainerFormate
 * 功能:设定好container中的布局样式以及根据布局样式自动填充数据
 */
package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
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
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH, 0.6, 1.0/3);
		int itemviewW=ViewUtil.getViewWidth(this);
		int itemviewH=ViewUtil.getViewHeight(this);
		
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 1, 0.25);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 1, 0.05);
		try {
			if(Util.isJsonNull(json, "titleimage")){
				
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 1, 0.35);
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 1, 0.35);
			}else{
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 1, 0.7);

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
