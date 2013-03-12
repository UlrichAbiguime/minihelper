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

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.barfoo.container.AContainer;
import com.barfoo.container.AHContainer;
import com.barfoo.container.BContainer;
import com.barfoo.container.CContainer;
import com.barfoo.container.CHContainer;
import com.barfoo.container.DHContainer;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public  class  AFormateStyle extends BaseFormat implements IFormat{
	
	public static int containSize = 3;
	
	Context mContext;
	int screenFlag;//0为横屏 ;1为竖屏
	public AFormateStyle(Context context) {
		super(context);
	}

	public AFormateStyle(Context context,AttributeSet attrs,JSONArray array,Integer screenFlag) {
		super(context,attrs);
		mContext=context;
		this.screenFlag= (int)screenFlag;
		LayoutInflater.from(context).inflate(R.layout.baseviewmain, this, true);
		buildFormat(array);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void buildFormat(JSONArray array) {
		try {
			if(screenFlag==0){
				
				getCenterLeftLinear().addView(new AHContainer(mContext, null,array.getJSONObject(0)));
				getCenterLine().setVisibility(View.VISIBLE);
				getCenterLine().setLayoutParams(new LayoutParams(1,LayoutParams.FILL_PARENT));
				View view =new DHContainer(mContext, null,array.getJSONObject(1));
				getCenterRightLinear().addView(view);
				getCenterRightLinear().addView(ViewUtil.addViewlines(mContext, ViewUtil.getViewWidth(view),ViewUtil.getViewHeight(view)));
				getCenterRightLinear().addView(new CHContainer(mContext, null,array.getJSONObject(2)));

			}else{
				//竖屏设置AContainer、BContainer、CContainer
				getTopLinear().addView(new AContainer(mContext, null,array.getJSONObject(0)));
				getTopline().setVisibility(View.VISIBLE);
				getTopline().setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,1));
				View leftview =new BContainer(mContext, null,array.getJSONObject(1));
				getCenterLeftLinear().addView(leftview);
				getCenterLine().setVisibility(View.VISIBLE);
				getCenterLine().setLayoutParams(new LayoutParams(1,ViewUtil.getViewHeight(leftview)));
				
				getCenterRightLinear().addView(new CContainer(mContext, null,array.getJSONObject(0)));
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
