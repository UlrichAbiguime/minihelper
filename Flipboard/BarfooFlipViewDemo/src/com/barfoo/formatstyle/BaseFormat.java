/**
 * BaseFormat
 * 每个页面基础框架(包括头部、中间左边部分、中间右边部分、底部)
 */
package com.barfoo.formatstyle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.barfoo.flipview.demo.R;

public abstract class BaseFormat extends LinearLayout {

	LinearLayout basetop;
	LinearLayout basebottom;
	LinearLayout basecenter;
	LinearLayout centerleft;
	LinearLayout centerright;
	View centerline;
	View topline;
	View bottomline;

	public BaseFormat(Context context) {
		super(context);
	}

	public BaseFormat(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public LinearLayout getTopLinear() {
		if (basetop == null) {
			basetop = (LinearLayout) findViewById(R.id.ll_basetop);
		}
		return basetop;
	}

	public LinearLayout getCenterLinear() {
		if (basecenter == null) {
			basecenter = (LinearLayout) findViewById(R.id.ll_basecenter);
		}
		return basecenter;
	}

	public LinearLayout getBottomLinear() {
		if (basebottom == null) {
			basebottom = (LinearLayout) findViewById(R.id.ll_basebottom);
		}
		return basebottom;
	}

	public LinearLayout getCenterLeftLinear() {
		if (centerleft == null) {
			centerleft = (LinearLayout) findViewById(R.id.ll_centerleft);
		}
		return centerleft;
	}

	public LinearLayout getCenterRightLinear() {
		if (centerright == null) {
			centerright = (LinearLayout) findViewById(R.id.ll_centerright);
		}
		return centerright;
	}

	public View  getTopline() {

		if (topline == null) {
			topline = (View)findViewById(R.id.topcenterline);
		}
		return topline;
	}

	public View getCenterLine() {

		if (centerline == null) {
			centerline = (View) findViewById(R.id.centerline);
		}
		return centerline;
	}

	public View getBottomline() {

		if (bottomline == null) {
			bottomline = (View) findViewById(R.id.centerbottomline);
		}
		return bottomline;
	}

}
