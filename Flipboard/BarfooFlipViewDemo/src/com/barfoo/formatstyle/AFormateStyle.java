/**
 * AFormateStyle功能:
 * 适合数据条数为3时的版式
 * 通过获取数据条数随机选择适合其数据的版式
 * 同时版式是中包括设定好在版式中各个container的样式
 */
package com.barfoo.formatstyle;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.barfoo.container.AContainerFormate;
import com.barfoo.container.BContainerFormate;
import com.barfoo.container.CContainerFormate;
import com.barfoo.flipview.demo.R;

public  class  AFormateStyle extends BaseFormat implements IFormat{
	
	Context mContext;
	
	public AFormateStyle(Context context) {
		super(context);
	}

	public AFormateStyle(Context context,AttributeSet attrs,JSONArray array) {
		
		super(context,attrs);
		mContext=context;
		LayoutInflater.from(context).inflate(R.layout.baseviewmain, this, true);
		buildFormat(array);
		
	}
	
	@Override
	public void buildFormat(JSONArray array) {

		try {
			getTopLinear().addView(new AContainerFormate(mContext, null,array.getJSONObject(0)));
			getCenterLeftLinear().addView(new BContainerFormate(mContext, null,array.getJSONObject(1)));
			getCenterRightLinear().addView(new CContainerFormate(mContext, null,array.getJSONObject(2)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
