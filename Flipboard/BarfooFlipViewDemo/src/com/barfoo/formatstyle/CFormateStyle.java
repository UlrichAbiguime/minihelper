/**
 * CFormateStyle功能:
 * 适合数据条数为 5 时的版式
 * 通过获取数据条数随机选择适合其数据的版式
 * 同时版式是中包括设定好在版式中各个container的样式
 */
package com.barfoo.formatstyle;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.barfoo.container.BContainer;
import com.barfoo.container.DContainer;
import com.barfoo.container.EContainer;
import com.barfoo.container.EHContainer;
import com.barfoo.container.FContainer;
import com.barfoo.container.FHContainer;
import com.barfoo.container.GContainer;
import com.barfoo.container.HContainer;
import com.barfoo.container.JContainer;
import com.barfoo.flip.demo.IFormat;
import com.barfoo.flipview.demo.R;

public class CFormateStyle extends BaseFormat implements IFormat{

	Context mContext;
	
	public CFormateStyle(Context context) {
		super(context);
		
	}
	
	public CFormateStyle(Context context,AttributeSet attrs,JSONArray array) {
		super(context,attrs);
		mContext=context;
		LayoutInflater.from(context).inflate(R.layout.baseviewmain, this, true);
		buildFormat(array);
		
	}

	@Override
	public void buildFormat(JSONArray array) {
		try {
			//竖屏
			/**
			getCenterLeftLinear().addView(new EContainer(mContext, null, array.getJSONObject(0)));
			getCenterLeftLinear().addView(addViewlines(mContext));
			getCenterLeftLinear().addView(new FContainer(mContext, null, array.getJSONObject(1)));
			getCenterLeftLinear().addView(addViewlines(mContext));
			getCenterLeftLinear().addView(new GContainer(mContext, null, array.getJSONObject(2)));
			
			getCenterRightLinear().addView(new HContainer(mContext, null, array.getJSONObject(3)));
			getCenterRightLinear().addView(new JContainer(mContext, null, array.getJSONObject(4)));
			**/
			
			//横屏
			getCenterLeftLinear().addView(new EHContainer(mContext, null, array.getJSONObject(0)));
			getCenterRightLinear().addView(new EHContainer(mContext, null, array.getJSONObject(1)));
			getBottomLinear().addView(new FHContainer(mContext, null, array.getJSONObject(2)));
			getBottomLinear().addView(new FHContainer(mContext, null, array.getJSONObject(3)));
			getBottomLinear().addView(new FHContainer(mContext, null, array.getJSONObject(4)));
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public View addViewlines(Context context){
		View view=LayoutInflater.from(mContext).inflate(R.layout.viewline, null);
		return view;
	}
}
