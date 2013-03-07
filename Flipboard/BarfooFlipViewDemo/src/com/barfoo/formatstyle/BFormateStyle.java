/**
  BFormateStyle功能:
  适合数据条数为 6 时的版式
  通过获取数据条数随机选择适合其数据的版式
  同时版式是中包括设定好在版式中各个container的样式
  目前可选择只有一项竖屏设置 DContainer
  目前可选择只有一项横屏设置 BHContainer
 */
package com.barfoo.formatstyle;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.barfoo.container.BHContainer;
import com.barfoo.container.DContainer;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class BFormateStyle extends BaseFormat implements IFormat {

	Context mContext;
	int screenFlag;

	public BFormateStyle(Context context) {
		super(context);
	}

	public BFormateStyle(Context context, AttributeSet attrs, JSONArray array,
			int screenFlag) {
		super(context, attrs);
		mContext = context;
		this.screenFlag = screenFlag;
		Log.e("screenFlag", "" + screenFlag);
		LayoutInflater.from(context).inflate(R.layout.baseviewmain, this, true);
		buildFormat(array);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void buildFormat(JSONArray array) {
		try {

			if (screenFlag == 0) {
				// 横屏
				View view = null;
				for (int i = 0; i < 3; i++) {

					view = new BHContainer(mContext, null,array.getJSONObject(i));
					getTopLinear().addView(view);
					if (i != 2) {
						//getTopLinear().addView(ViewUtil.addVerticalViewlines(mContext,ViewUtil.getViewWidth(view),ViewUtil.getViewHeight(view)));
					
						getTopLinear().addView(ViewUtil.addVerticalViewlines(mContext,ViewUtil.getViewHeight(view)));
					}
				}
				// 横向称满屏幕的直线
				getCenterLeftLinear().addView(ViewUtil.addViewlines(mContext));

				for (int i = 3; i < 6; i++) {
					view = new BHContainer(mContext, null,array.getJSONObject(i));
					getBottomLinear().addView(view);
					if (i != 5) {
						getBottomLinear().addView(ViewUtil.addVerticalViewlines(mContext,ViewUtil.getViewHeight(view)));
					}
				}

			} else {
				// 竖屏
				
				for (int i = 0; i < 3; i++) {
					View view = new DContainer(mContext, null,array.getJSONObject(i));
					getCenterLeftLinear().addView(view);

					if (i != 2) {
						getCenterLeftLinear().addView(ViewUtil.addViewlines(mContext,ViewUtil.getViewWidth(view),ViewUtil.getViewHeight(view)));
					}
				}

				for (int i = 3; i < 6; i++) {
					View view = new DContainer(mContext, null,array.getJSONObject(i));
					getCenterRightLinear().addView(new DContainer(mContext, null, array.getJSONObject(i)));
					if (i != 5) {
						getCenterRightLinear().addView(ViewUtil.addViewlines(mContext,ViewUtil.getViewWidth(view),ViewUtil.getViewHeight(view)));
					}
				}
				
				getCenterLine().setVisibility(View.VISIBLE);
				getCenterLine().setLayoutParams(new LayoutParams(1, LayoutParams.FILL_PARENT));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
