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

	public DContainerFormate(Context context) {
		super(context);
	}

	public DContainerFormate(Context context, AttributeSet attrs,JSONObject json) {
		super(context, attrs);
		mContext = context;
		LayoutInflater.from(mContext).inflate(R.layout.containeritemd, this,true);
		buildView(json);
	}

	@Override
	public void buildView(JSONObject json) {
		View view = this;
		ViewUtil.setViewWidHeight(this, 0.5, 0.3);
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);
		ll_titlepic = (LinearLayout) findViewById(R.id.ll_titlepic);
		ivpicc = (ImageView) findViewById(R.id.ivpicc);
		tvc_title = (TextView) findViewById(R.id.tvc_title);
		tvc_source = (TextView) findViewById(R.id.tvc_source);
		ivc_source = (ImageView) findViewById(R.id.ivc_source);
		ViewUtil.setViewWidHeight(ll_titlepic, itemviewW, itemviewH, 1, 0.5);
		tvc_content = (TextView) findViewById(R.id.tvc_content);

		try {
			tvc_title.setText(json.getString("title"));
			if (Util.isJsonNull(json, "sourceimage")) {
				ivc_source.setImageResource(R.drawable.feed_taggeduser_image);
			}
			tvc_source.setText(json.getString("source"));
			tvc_content.setText(json.getString("content"));
			truncate(tvc_content,25);

			if (Util.isJsonNull(json, "titleimage")) {
				ivpicc.setImageResource(R.drawable.bg);
			} else {
				ll_titlepic.setVisibility(View.GONE);
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
