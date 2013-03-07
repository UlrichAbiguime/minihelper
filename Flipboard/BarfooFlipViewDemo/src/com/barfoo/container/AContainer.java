/**
 * AContainerFormate(为AFormateStyle的竖屏版式)
 * 功能:设定好container中的布局样式以及根据布局样式自动填充数据
 */
package com.barfoo.container;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barfoo.flip.demo.data.Util;
import com.barfoo.flip.demo.data.ViewUtil;
import com.barfoo.flipview.demo.R;

@SuppressLint("WrongCall")
public class AContainer extends LinearLayout implements IContainer {

	Context mContext;
	public TextView tva_content;
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
	public LinearLayout ll_ivsourceforlin;
	public TextView tv_title;
	public int maxlines;

	public AContainer(Context context) {
		super(context);
	}

	public AContainer(Context context, AttributeSet attrs, JSONObject json) {
		super(context, attrs);
		mContext = context;
		LayoutInflater.from(mContext).inflate(getXmlResource(), this, true);
		initWidget();
		buildView(json);
		setData(json);
		//setTextLin();
	}
	
	@Override
	public void buildView(JSONObject json) {
		ViewUtil.setViewWidHeight(this, ViewUtil.trueScreenW, ViewUtil.trueScreenH, 1, 0.4);//mobile:1, 0.4//pad: 1, 0.4
		int itemviewW = ViewUtil.getViewWidth(this);
		int itemviewH = ViewUtil.getViewHeight(this);
		ViewUtil.setViewPadding(this, itemviewW, itemviewH, 0.04, 0, 0.04, 0);//mobile: 0.04, 0, 0.04, 0//pad:0.04, 0, 0.04, 0
		ViewUtil.setViewWidHeight(ll_title, itemviewW, itemviewH, 0.91, 0.17);//mobile://pad:0.91, 0.15
		ViewUtil.setViewWidHeight(ll_source, itemviewW, itemviewH,0.91, 0.13);//mobile://pad:0.91, 0.13
		try {
			if (Util.isJsonNull(json, "titleimage")) {
				ViewUtil.setViewWidHeight(ll_image, itemviewW, itemviewH, 0.91, 0.37);//mobile:0.91, 0.38//pad: 0.91, 0.43
				ViewUtil.setViewPadding(ll_image, ViewUtil.getViewWidth(ll_image), ViewUtil.getViewHeight(ll_image), 0, 0.05, 0, 0.05);//mobile://pad:0, 0.05, 0, 0.05
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH,0.91, 0.24);//mobile://pad:0.91, 0.24

			} else {
				ViewUtil.setViewWidHeight(ll_tvcontent, itemviewW, itemviewH,0.91, 0.77);//mobile://pad:0.91, 0.77
				ll_image.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**maxlines
	 * 初始化组件
	 */
	public void initWidget() {
		ll_image = (LinearLayout) findViewById(R.id.ll_image);
		iv_image = (ImageView) findViewById(R.id.iv_image);
		ll_title = (LinearLayout) findViewById(R.id.ll_title);
		tv_title = (TextView) findViewById(R.id.tv_title);
		ll_source = (LinearLayout) findViewById(R.id.ll_source);
		ll_ivsourceforlin = (LinearLayout) findViewById(R.id.ll_ivsourceforlin);
		iv_sourceicon = (ImageView) findViewById(R.id.iv_sourceicon);
		ll_tvsourceforlin = (LinearLayout) findViewById(R.id.ll_tvsourceforlin);
		tv_source = (TextView) findViewById(R.id.tv_source);
		ll_tvcontent = (LinearLayout) findViewById(R.id.ll_tvcontent);
		tv_content = (TextView) findViewById(R.id.tv_content);
	}

	/**
	 * 对获取的数据进行赋值操作
	 * 
	 * @param json
	 */
	public void setData(JSONObject json) {

		maxlines = Util.getMaxLines(ViewUtil.getViewHeight(ll_tvcontent), tv_content.getTextSize());
		int textFont;
		if(ViewUtil.screenH>ViewUtil.screenW){
			textFont=ViewUtil.screenH;
		}else{
			textFont=ViewUtil.screenW;
		}
		
		//设置字体的大小随屏幕的宽高，进行调整(调整过程中是以pad的调整进行计算)
		//横竖屏切换过程中以竖屏的高度为准，对字体进行调整
		float textsize=((float)(textFont)/1920) *tv_title.getTextSize();
		tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX,textsize);
		tv_source.setTextSize(TypedValue.COMPLEX_UNIT_PX,((float)(textFont))/1920*tv_source.getTextSize());
		tv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX,((float)(textFont))/1920*tv_content.getTextSize());
		
		
		try {
			tv_title.setText(json.getString("title"));
			if (Util.isJsonNull(json, "sourceimage")) {
				iv_sourceicon.setImageResource(R.drawable.feed_taggeduser_image);
			}
			tv_source.setText(json.getString("source"));
			tv_content.setText(json.getString("content"));
			if(maxlines>0){
				Util.truncate(tv_content, maxlines);
			}

			if (Util.isJsonNull(json, "titleimage")) {
				iv_image.setImageResource(R.drawable.newbg);
			} else {
				ll_image.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getXmlResource() {
		return R.layout.containeritema;
	}
	
	/**
	private void setTextLin() {
		tv_content.setMaxLines(2);
		tv_content.setEllipsize(TruncateAt.END);
	}
	**/

}
