package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DContainerFormate extends LinearLayout implements IContainer {

	Context mContext;
	public  TextView tva_content;
	public View view;
	public LinearLayout ll_titlepic;
	public ImageView ivpicc;
	public TextView tvc_title;
	public TextView tvc_source;
	public ImageView ivc_source;
	public TextView tvc_content;
	private LinearLayout ll_bodyforlin;
	boolean misScreen=true;

	public DContainerFormate(Context context) {
		super(context);
	}

	public DContainerFormate(Context context, AttributeSet attrs,JSONObject json) {
		super(context, attrs);
		mContext = context;
		//misScreen=isScreen;
		LayoutInflater.from(mContext).inflate(R.layout.containeritemd, this,true);
		buildView(json);
	}

	@Override
	public void buildView(JSONObject json) {
		View view = this;
		ViewUtil.setViewWidHeight(this, 0.5, 0.34);//竖屏0.35
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);
		ll_titlepic = (LinearLayout) findViewById(R.id.ll_titlepic);
		ll_bodyforlin = (LinearLayout) findViewById(R.id.ll_contextforlin);
		ivpicc = (ImageView) findViewById(R.id.ivpicc);
		//ViewUtil.setViewWidth(ll_bodyforlin, groupviewWidth, width)
		tvc_title = (TextView) findViewById(R.id.tvc_title);
		tvc_source = (TextView) findViewById(R.id.tvc_source);
		ivc_source = (ImageView) findViewById(R.id.ivc_source);
		tvc_content = (TextView) findViewById(R.id.tvc_content);

		try {
			tvc_title.setText(json.getString("title"));
			if (Util.isJsonNull(json, "sourceimage")) {
				ivc_source.setImageResource(R.drawable.feed_taggeduser_image);
			}
			tvc_source.setText(json.getString("source"));
			tvc_content.setText(json.getString("content"));
			if(misScreen){
				truncate(tvc_content,5);
				if (Util.isJsonNull(json, "titleimage")) {
					ll_titlepic.setLayoutParams(new LayoutParams((int)(itemviewW * 0.35), LayoutParams.WRAP_CONTENT));
					ll_bodyforlin.setLayoutParams(new LayoutParams((int)(itemviewW * 0.6), LayoutParams.WRAP_CONTENT));
					ViewUtil.setViewPadding(ll_titlepic, ViewUtil.getViewWidth(ll_titlepic), ViewUtil.getViewHeight(ll_titlepic), 0.05, 0, 0, 0);
					ivpicc.setImageResource(R.drawable.loginbg);
				} else {
					//ViewUtil.setViewWidHeight(ll_titlepic, itemviewW, itemviewH,0.5, 0.5);
					ll_titlepic.setVisibility(View.GONE);
				}
			}else{
				truncate(tvc_content,11);
				if (Util.isJsonNull(json, "titleimage")) {
					
					ll_titlepic.setLayoutParams(new LayoutParams((int)(itemviewW * 0.45), LayoutParams.WRAP_CONTENT));
					ll_bodyforlin.setLayoutParams(new LayoutParams((int)(itemviewW * 0.55), LayoutParams.WRAP_CONTENT));
					ViewUtil.setViewPadding(ll_titlepic, ViewUtil.getViewWidth(ll_titlepic), ViewUtil.getViewHeight(ll_titlepic), 0.05, 0, 0, 0);
					ivpicc.setImageResource(R.drawable.loginbg);
				} else {
					//ViewUtil.setViewWidHeight(ll_titlepic, itemviewW, itemviewH,0.5, 0.5);
					ll_titlepic.setVisibility(View.GONE);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对textview进行设置最大行数，更多用省略号省略
	 * @param view
	 * @param maxLine
	 */
	public  void truncate(final TextView view, final int maxLine) {
		ViewTreeObserver vto = view.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			public void onGlobalLayout() {
				if (view.getLineCount() > maxLine) {
					int lineEndIndex = view.getLayout().getLineEnd(maxLine - 1);
					String text = view.getText().subSequence(0,lineEndIndex - 3)+ "...";
					view.setText(text);
				}
			}
		});
	}
}
