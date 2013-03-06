/**
 * AContainerFormate 功能:设定好container中的布局样式以及根据布局样式自动填充数据
 */
package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

@SuppressLint("WrongCall")
public class AContainer extends LinearLayout implements IContainer {

	Context mContext;
	public TextView tva_content;
	public View view;
	public LinearLayout ll_image;
	public ImageView iv_image;
	public ImageView iv_sourceicon;
	public TextView tv_source;
	public LinearLayout ll_tvcontent;
	public TextView tv_content;
	public LinearLayout ll_tvsourceforlin;
	public LinearLayout ll_title;
	public LinearLayout ll_source;
	public LinearLayout ll_ivsourceforlin;
	public TextView tv_title;
	public int maxlines;

	public AContainer(Context context) {
		super(context);
	}

	public AContainer(Context context, AttributeSet attrs, JSONObject json) {
		super(context, attrs);
		mContext = context;
		LayoutInflater.from(mContext).inflate(getXmlResource(), this, true);
		initWidget();
		buildView(json);
		setData(json);
		setTextLin();
	}

	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH, 1, 0.4);
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 1, 0.15);
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 1, 0.05);
		ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 1, 0.4);
		Log.i("message", "Ac_bu");
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 1, 0.4);

			} else {
				ll_image.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getXmlResource() {
		return R.layout.containeritema;
	}

	/**maxlines
	 * 初始化组件
	 */
	public void initWidget() {
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
	}

	/**
	 * 对获取的数据进行赋值操作
	 * 
	 * @param json
	 */
	public void setData(JSONObject json) {

		maxlines = Util.getMaxLines(ViewUtil.getViewHeight(ll_tvcontent), tv_content.getTextSize());

		try {
			tv_title.setText(json.getString("title"));
			if (Util.isJsonNull(json, "sourceimage")) {
				iv_sourceicon.setImageResource(R.drawable.feed_taggeduser_image);
			}
			tv_source.setText(json.getString("source"));
			tv_content.setText(json.getString("content"));

			if (Util.isJsonNull(json, "titleimage")) {
				iv_image.setImageResource(R.drawable.newbg);
			} else {
				ll_image.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setTextLin() {
		tv_content.setMaxLines(2);
		tv_content.setEllipsize(TruncateAt.END);
	}

}
