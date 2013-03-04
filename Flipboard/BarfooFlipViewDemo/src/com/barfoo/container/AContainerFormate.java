/**
 * AContainerFormate
 * 功能:设定好container中的布局样式以及根据布局样式自动填充数据
 */
package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

public class AContainerFormate extends LinearLayout implements IContainer{
	
	Context mContext;
	public  TextView tva_content;
	public View view;
	public LinearLayout ll_image;
	public ImageView iv_image;
	public ImageView iv_sourceicon;
	public TextView tv_source;
	public LinearLayout ll_tvcontent;
	public TextView tv_content;
	public LinearLayout ll_tvsourceforlin;
	public LinearLayout ll_title;
	public LinearLayout ll_source;
	
	
	public AContainerFormate(Context context) {
		super(context);
	}

	public AContainerFormate(Context context,AttributeSet attrs,JSONObject json) {
		super(context,attrs);
		mContext=context;
		LayoutInflater.from(mContext).inflate(R.layout.containeritema, this, true);
		buildView(json);
	}

	@Override
	public void buildView(JSONObject json) {
		
		ViewUtil.setViewWidHeight(this, 1, 0.4);
		int itemviewW=ViewUtil.getViewWidth(this);
		int itemviewH=ViewUtil.getViewHeight(this);
		ll_image = (LinearLayout)findViewById(R.id.ll_image);
		iv_image = (ImageView)findViewById(R.id.iv_image);
		ll_title = (LinearLayout)findViewById(R.id.ll_title);
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		ll_source = (LinearLayout)findViewById(R.id.ll_source);
		LinearLayout ll_ivsourceforlin = (LinearLayout)findViewById(R.id.ll_ivsourceforlin);
		iv_sourceicon = (ImageView)findViewById(R.id.iv_sourceicon);
		ll_tvsourceforlin = (LinearLayout)findViewById(R.id.ll_tvsourceforlin);
		tv_source = (TextView)findViewById(R.id.tv_source);
		ll_tvcontent = (LinearLayout)findViewById(R.id.ll_tvcontent);
		tv_content = (TextView)findViewById(R.id.tv_content);
		
		ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 1, 0.45);
		//ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 1, 0.1);
		//ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH, 1, 0.05);
		//ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH, 1, 0.4);
		//设置来源一行
		//ViewUtil.setViewWidHeight(ll_ivsourceforlin, ViewUtil.getViewWidth(ll_source), ViewUtil.getViewHeight(ll_source),0.05,1);
		
		
		try {
			tv_title.setText(json.getString("title"));
			if(Util.isJsonNull(json, "sourceimage")){
				iv_sourceicon.setImageResource(R.drawable.feed_taggeduser_image);
			}
			tv_source.setText(json.getString("source"));
			
			tv_content.setText(json.getString("content"));
			truncate(tv_content,7);
			if(Util.isJsonNull(json, "image")){
				iv_image.setImageResource(R.drawable.newbg);
			}else{
				ll_image.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param str
	 */
	public void setContentLines(final String str){
	    ViewTreeObserver vto = tv_content.getViewTreeObserver();
	    vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

	        @Override
	        public void onGlobalLayout() {
	            ViewTreeObserver obs = tv_content.getViewTreeObserver();
	            obs.removeGlobalOnLayoutListener(this);
	            if(tva_content.getLineCount() > 8){
	                int lineEndIndex = tv_content.getLayout().getLineEnd(7);
	                String text = tv_content.getText().subSequence(0, lineEndIndex-3)+"...";
	                tv_content.setText(text);
	            }
	        }
	    });
	}
	
	/**
	 * 对textview进行设置最大行数，更多用省略号省略
	 * @param view
	 * @param maxLine
	 */
	public  void truncate(final TextView view, final int maxLine) {
		ViewTreeObserver vto = view.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			public void onGlobalLayout() {
				if (view.getLineCount() > maxLine) {
					int lineEndIndex = view.getLayout().getLineEnd(maxLine - 1);
					String text = view.getText().subSequence(0,lineEndIndex - 3)+ "...";
					view.setText(text);
				}
			}
		});
	}
}
