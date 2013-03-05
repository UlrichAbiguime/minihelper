package com.barfoo.formatstyle;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.barfoo.container.DContainerFormate;
import com.barfoo.flipview.demo.R;

public class BFormateStyle extends BaseFormat implements IFormat{

	Context mContext;
	boolean misScreen;
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
			}
			
			for(int i=3;i<6;i++){
				getCenterRightLinear().addView(new DContainerFormate(mContext,null,array.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
