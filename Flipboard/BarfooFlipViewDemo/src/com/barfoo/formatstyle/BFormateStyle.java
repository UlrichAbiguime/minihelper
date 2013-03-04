package com.barfoo.formatstyle;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.barfoo.container.CContainerFormate;
import com.barfoo.container.DContainerFormate;
import com.barfoo.container.DashedLine;
import com.barfoo.flipview.demo.R;

public class BFormateStyle extends BaseFormat implements IFormat{

	Context mContext;
	public BFormateStyle(Context context) {
		super(context);
	}

	public BFormateStyle(Context context,AttributeSet attrs,JSONArray array) {
		super(context,attrs);
		mContext=context;
		LayoutInflater.from(context).inflate(R.layout.baseviewmain, this, true);
		buildFormat(array);
		
	}
	
	@Override
	public void buildFormat(JSONArray array) {
		try {
			for(int i=0;i<3;i++){
				getCenterLeftLinear().addView(new DContainerFormate(mContext,null,array.getJSONObject(i)));
				getCenterLeftLinear().addView(new DashedLine(mContext));
			}
			
			//getCenterLeftLinear().addView(new CContainerFormate(mContext,null,array.getJSONObject(1)));
			//getCenterLeftLinear().addView(new CContainerFormate(mContext,null,array.getJSONObject(2)));
			
			//getCenterRightLinear().addView(new CContainerFormate(mContext,null,array.getJSONObject(3)));
			//getCenterRightLinear().addView(new CContainerFormate(mContext,null,array.getJSONObject(4)));
			//getCenterRightLinear().addView(new CContainerFormate(mContext,null,array.getJSONObject(5)));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
