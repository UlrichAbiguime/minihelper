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
	public View view;
	private LinearLayout ll_image;
	private ImageView iv_image;
	private LinearLayout ll_title;
	private LinearLayout ll_source;
	private LinearLayout ll_ivsourceforlin;
	private ImageView iv_sourceicon;
	private LinearLayout ll_tvsourceforlin;
	private TextView tv_source;
	private LinearLayout ll_tvcontent;
	private TextView tv_content;
	private TextView tv_title;

	public DContainerFormate(Context context) {
		super(context);
	}

	public DContainerFormate(Context context, AttributeSet attrs,JSONObject json) {
		super(context, attrs, json);
		//LayoutInflater.from(mContext).inflate(getXmlResource(), this,true);
		mContext = context;
	}
	
	

	@Override
	public void buildView(JSONObject json) {
		View view = this;
		ViewUtil.setViewWidHeight(this, 0.5, 0.346);// 竖屏0.35
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);
		ll_image = (LinearLayout) findViewById(R.id.ll_image);
		iv_image = (ImageView) findViewById(R.id.iv_image);
		ll_title = (LinearLayout) findViewById(R.id.ll_title);
		tv_title = (TextView) findViewById(R.id.tv_title);
		ll_source = (LinearLayout) findViewById(R.id.ll_source);
		ll_ivsourceforlin = (LinearLayout) findViewById(R.id.ll_ivsourceforlin);
		iv_sourceicon = (ImageView) findViewById(R.id.iv_sourceicon);
		ll_tvsourceforlin = (LinearLayout) findViewById(R.id.ll_tvsourceforlin);
		tv_source = (TextView) findViewById(R.id.tv_source);
		ll_tvcontent = (LinearLayout) findViewById(R.id.ll_tvcontent);
		tv_content = (TextView) findViewById(R.id.tv_content);
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 1, 0.2);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 1, 0.05);
		
		/**
		int maxlines=Util.getMaxLines(ViewUtil.getViewHeight(ll_tvcontent), tv_content.getTextSize());
		try {
			tv_title.setText(json.getString("title"));
			if (Util.isJsonNull(json, "sourceimage")) {
				iv_image.setImageResource(R.drawable.feed_taggeduser_image);
			}
			tv_source.setText(json.getString("source"));
			tv_content.setText(json.getString("content"));

			Log.e("tv_content", maxlines+"");
			Util.truncate(tv_content, maxlines);
			Log.e("json", json.toString());
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
			Log.e("error", e.getMessage());
			e.printStackTrace();
		}
		**/
	}

	@Override
	public int getXmlResource() {
		return R.layout.containeritemd;
	}

}
