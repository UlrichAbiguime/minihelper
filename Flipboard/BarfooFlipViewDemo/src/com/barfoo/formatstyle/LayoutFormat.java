/**
 * BaseFormat 每个页面基础框架(包括头部、中间左边部分、中间右边部分、底部)
 */
package com.barfoo.formatstyle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.barfoo.flipview.demo.R;

public class LayoutFormat extends LinearLayout {

	LinearLayout basetop;
	LinearLayout basebottom;
	LinearLayout basecenter;
	LinearLayout centerleft;
	LinearLayout centerright;
	private viewHolder mHolder;

	public LayoutFormat(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.layoutmain, this, true);
		mHolder = new viewHolder();
	}

	public LayoutFormat(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public LinearLayout getHeaderLinear() {
		if (mHolder.mLayoutHeader == null) {
			mHolder.mLayoutHeader = (LinearLayout) findViewById(R.id.layoutheader);
		}
		return mHolder.mLayoutHeader;
	}

	public LinearLayout getFragmentLinear() {
		if (mHolder.mLayoutFragment == null) {
			mHolder.mLayoutFragment = (LinearLayout) findViewById(R.id.layoutfragment);
		}
		return mHolder.mLayoutFragment;
	}

	public LinearLayout getFooderLinear() {
		if (mHolder.mLayoutFooder == null) {
			mHolder.mLayoutFooder = (LinearLayout) findViewById(R.id.layoutfooder);
		}
		return mHolder.mLayoutFooder;
	}

	class viewHolder {
		LinearLayout mLayoutHeader;
		LinearLayout mLayoutFragment;
		LinearLayout mLayoutFooder;
	}

}
