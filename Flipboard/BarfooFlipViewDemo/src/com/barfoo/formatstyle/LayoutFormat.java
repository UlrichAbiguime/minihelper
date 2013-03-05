/**
 * BaseFormat 每个页面基础框架(包括头部、中间左边部分、中间右边部分、底部)
 */
package com.barfoo.formatstyle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.barfoo.flipview.demo.R;

public class LayoutFormat extends LinearLayout {

	RelativeLayout baseheader;
	LinearLayout basefooder;
	LinearLayout basefragment;

	public LayoutFormat(Context context) {
		super(context);
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
