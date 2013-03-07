package com.barfoo.flip.demo.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

public class Util {

	/**
	 * 判断当前的关键字是否为空
	 * @param obj
	 * @param str(关键字)
	 * @return
	 * @throws JSONException
	 */
	public static  boolean isJsonNull(JSONObject json,String str) throws JSONException{
		if(json.getString(str).length()>0 && !json.getString(str).equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 对TextView进行设置最大行数，更多用省略号省略
	 * @param view
	 * @param maxLine
	 */
	public static void truncate(final TextView view, final int maxLine) {
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
	
	public static int getFontHeight(float fontSize) {
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		FontMetrics fm = paint.getFontMetrics();
		return (int) Math.ceil(fm.descent - fm.top) + 2;
	}
	
	public static int getMaxLines(int contentHeight,float fontSize){
		return (int)(contentHeight/getFontHeight(fontSize));
	}
}
