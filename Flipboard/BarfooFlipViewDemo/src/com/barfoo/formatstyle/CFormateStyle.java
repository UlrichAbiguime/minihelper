/**
 * CFormateStyle功能: 适合数据条数为 5 时的版式 通过获取数据条数随机选择适合其数据的版式
 * 同时版式是中包括设定好在版式中各个container的样式
 */
package com.barfoo.formatstyle;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.barfoo.container.EContainer;
import com.barfoo.container.EHContainer;
import com.barfoo.container.FContainer;
import com.barfoo.container.FHContainer;
import com.barfoo.container.GContainer;
import com.barfoo.container.HContainer;
import com.barfoo.container.JContainer;
import com.barfoo.flip.demo.IFormat;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class CFormateStyle extends BaseFormat implements IFormat {

	Context mContext;
	int screenFlag;// 0为横屏 ;1为竖屏

	public static int containSize = 5;

	public CFormateStyle(Context context) {
		super(context);

	}

	public CFormateStyle(Context context, AttributeSet attrs, JSONArray array,Integer screenFlag) {
		super(context, attrs);
		mContext = context;
		this.screenFlag = (int)screenFlag;
		LayoutInflater.from(context).inflate(R.layout.baseviewmain, this, true);
		buildFormat(array);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void buildFormat(JSONArray array) {
		try {

			if (screenFlag == 0) {
				// 横屏
				View leftView = new EHContainer(mContext, null, array.getJSONObject(0));
				getCenterLeftLinear().addView(leftView);

				getCenterLine().setVisibility(View.VISIBLE);
				getCenterLine().setLayoutParams(new LayoutParams(1, ViewUtil.getViewHeight(leftView)));

				getCenterRightLinear().addView(new EHContainer(mContext, null, array.getJSONObject(1)));
				getBottomline().setVisibility(View.VISIBLE);
				getBottomline().setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));

				View bottomview = new FHContainer(mContext, null, array.getJSONObject(2));
				getBottomLinear().addView(bottomview);
				getBottomLinear().addView(ViewUtil.addVerticalViewlines(mContext, ViewUtil.getViewHeight(bottomview)));
				getBottomLinear().addView(new FHContainer(mContext, null, array.getJSONObject(3)));
				getBottomLinear().addView(ViewUtil.addVerticalViewlines(mContext, ViewUtil.getViewHeight(bottomview)));
				getBottomLinear().addView(new FHContainer(mContext, null, array.getJSONObject(4)));

			} else {
				// 竖屏
				View view = new EContainer(mContext, null, array.getJSONObject(0));
				getCenterLeftLinear().addView(view);
				getCenterLeftLinear().addView(ViewUtil.addViewlines(mContext, ViewUtil.getViewWidth(view), ViewUtil.getViewHeight(view)));
				getCenterLeftLinear().addView(new FContainer(mContext, null, array.getJSONObject(1)));
				getCenterLeftLinear().addView(ViewUtil.addViewlines(mContext, ViewUtil.getViewWidth(view), ViewUtil.getViewHeight(view)));
				getCenterLeftLinear().addView(new GContainer(mContext, null, array.getJSONObject(2)));

				getCenterLine().setVisibility(View.VISIBLE);
				getCenterLine().setLayoutParams(new LayoutParams(1, LayoutParams.FILL_PARENT));

				View rightView = new HContainer(mContext, null, array.getJSONObject(3));
				getCenterRightLinear().addView(rightView);
				getCenterRightLinear().addView(ViewUtil.addViewlines(mContext, ViewUtil.getViewWidth(rightView), ViewUtil.getViewHeight(rightView)));
				getCenterRightLinear().addView(new JContainer(mContext, null, array.getJSONObject(4)));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
