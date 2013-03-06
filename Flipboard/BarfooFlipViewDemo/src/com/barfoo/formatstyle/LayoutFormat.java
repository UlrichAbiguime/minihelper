/**
 * LayoutFormat 每个页面基础框架(包括头部、中间左边部分、中间右边部分、底部)
 */
package com.barfoo.formatstyle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barfoo.flipview.demo.R;

public class LayoutFormat extends LinearLayout {

	Context mContext;
	
	RelativeLayout baseheader;
	LinearLayout basefooder;
	LinearLayout basefragment;
	
	TextView mTv_title;

	private Button mSearch_button;

	private Button mBack_button;

	public LayoutFormat(Context context) {
		super(context);
		this.mContext=context;
		LayoutInflater.from(context).inflate(R.layout.layoutmain, this, true);
		getBackButton().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
		
		getSearchButton().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
	}

	public LayoutFormat(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RelativeLayout getHeaderLinear() {
		if (baseheader == null) {
			baseheader = (RelativeLayout) findViewById(R.id.layoutheader);
		}
		return baseheader;
	}
	
	public Button getBackButton() {
		if (mBack_button == null) {
			mBack_button = (Button) findViewById(R.id.back_button);
		}
		return mBack_button;
	}
	
	public Button getSearchButton() {
		if (mSearch_button == null) {
			mSearch_button = (Button) findViewById(R.id.search_button);
		}
		return mSearch_button;
	}
	
	public TextView getTitle() {
		if (mTv_title == null) {
			mTv_title = (TextView) findViewById(R.id.tv_title);
		}
		return mTv_title;
	}

	public LinearLayout getFragmentLinear() {
		if (basefragment == null) {
			basefragment = (LinearLayout) findViewById(R.id.layoutfragment);
		}
		return basefragment;
	}

	public LinearLayout getFooderLinear() {
		if (basefooder == null) {
			basefooder = (LinearLayout) findViewById(R.id.layoutfooder);
		}
		return basefooder;
	}
	
}
