/**
 * BFormateStyle功能:
 * 适合数据条数为 6 时的版式
 * 通过获取数据条数随机选择适合其数据的版式
 * 同时版式是中包括设定好在版式中各个container的样式
 * 目前可选择只有一项竖屏设置 DContainer
 * 目前可选择只有一项横屏设置 BHContainer
 */
package com.barfoo.formatstyle;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;

import com.barfoo.container.BHContainer;
import com.barfoo.container.DContainer;
import com.barfoo.flipview.demo.R;

public class BFormateStyle extends BaseFormat implements IFormat{

	Context mContext;
	int screenFlag;
	public BFormateStyle(Context context) {
		super(context);
	}

	public BFormateStyle(Context context,AttributeSet attrs,JSONArray array,int screenFlag) {
		super(context,attrs);
		mContext=context;
		this.screenFlag=screenFlag;
		Log.e("screenFlag", ""+screenFlag);
		LayoutInflater.from(context).inflate(R.layout.baseviewmain, this, true);
		buildFormat(array);
	}
	
	@Override
	public void buildFormat(JSONArray array) {
		try {
			
			
			if(screenFlag==0){
				//横屏
				for(int i=0;i<3;i++){
					getTopLinear().addView(new BHContainer(mContext,null,array.getJSONObject(i)));
				}
				
				for(int i=3;i<6;i++){
					getBottomLinear().addView(new BHContainer(mContext,null,array.getJSONObject(i)));
				}
				
			}else{
				//竖屏
				for(int i=0;i<3;i++){
					getCenterLeftLinear().addView(new DContainer(mContext,null,array.getJSONObject(i)));
				}
			
				for(int i=3;i<6;i++){
					getCenterRightLinear().addView(new DContainer(mContext,null,array.getJSONObject(i)));
				}
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getContainerSize() {
		return 6;
	}
}
