/**
 * AFormateStyle功能:
 * 适合数据条数为 3 时的版式
 * 通过获取数据条数随机选择适合其数据的版式
 * 同时版式是中包括设定好在版式中各个container的样式
 * 目前可选择只有一项竖屏设置 AContainer、BContainer、CContainer
 * 目前可选择只有一项横屏设置 AHContainer、DHContainer、CHContainer
 */
package com.barfoo.formatstyle;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.barfoo.container.AHContainer;
import com.barfoo.container.CHContainer;
import com.barfoo.container.DHContainer;
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
			//竖屏设置AContainer、BContainer、CContainer
			//getTopLinear().addView(new AContainer(mContext, null,array.getJSONObject(0)));
			//getCenterLeftLinear().addView(new BContainer(mContext, null,array.getJSONObject(1)));
			//getCenterRightLinear().addView(new CContainer(mContext, null,array.getJSONObject(2)));
			
			getCenterLeftLinear().addView(new AHContainer(mContext, null,array.getJSONObject(0)));
			getCenterRightLinear().addView(new DHContainer(mContext, null,array.getJSONObject(1)));
			getCenterRightLinear().addView(new CHContainer(mContext, null,array.getJSONObject(2)));
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static int getContainerSize(){
		return 3;
	}
	
	
}
