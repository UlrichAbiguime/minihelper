/**
 * BaseFormat 每个页面基础框架(包括头部、中间左边部分、中间右边部分、底部)
 */
package com.barfoo.formatstyle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.barfoo.flipview.demo.R;

public class LayoutFormat extends LinearLayout {

	Context mContext;
	
	RelativeLayout baseheader;
	LinearLayout basefooder;
	LinearLayout basefragment;
	
	Button Button01;

	public LayoutFormat(Context context) {
		super(context);
		this.mContext=context;
		LayoutInflater.from(context).inflate(R.layout.layoutmain, this, true);
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
		if (Button01 == null) {
			Button01 = (Button) findViewById(R.id.Button01);
		}
		return Button01;
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
