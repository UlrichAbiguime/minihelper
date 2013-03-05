package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class DContainerFormate extends AContainer implements IContainer {

	Context mContext;

	public DContainerFormate(Context context) {
		super(context);
	}

	public DContainerFormate(Context context, AttributeSet attrs,JSONObject json) {
		super(context, attrs, json);
		mContext = context;
	}
	
	

	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, 0.5, 0.346);// 竖屏0.35
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);

		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 1, 0.2);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 1, 0.05);
		
		
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 0.5, 0.75);
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.5, 0.75);
				ll_image.setLayoutParams(new LayoutParams((int) (itemviewW * 0.45), LayoutParams.WRAP_CONTENT));
				ll_tvcontent.setLayoutParams(new LayoutParams((int) (itemviewW * 0.55), LayoutParams.WRAP_CONTENT));
				ViewUtil.setViewPadding(ll_image,ViewUtil.getViewWidth(ll_image),ViewUtil.getViewHeight(ll_image), 0.05, 0, 0, 0);
				iv_image.setImageResource(R.drawable.loginbg);
			} else {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 1, 0.75);
				ll_image.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getXmlResource() {
		return R.layout.containeritemd;
	}

}
