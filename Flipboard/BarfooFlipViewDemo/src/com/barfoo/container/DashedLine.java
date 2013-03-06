/**
 * DashedLine.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-4-9
 */
package com.barfoo.container;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author comger 虚线类
 */
public class DashedLine extends View {
	public DashedLine(Context context) {
		super(context);
	}

	public DashedLine(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.parseColor("#d4d4d4"));
		Path path = new Path();
		path.moveTo(10, 10);
		path.lineTo(800, 10);
		PathEffect effects = new DashPathEffect(new float[] { 0, 0, 0, 0 }, 1);
		paint.setPathEffect(effects);
		canvas.drawPath(path, paint);
	}

	
}
