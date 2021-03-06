/**
 *图片放大缩小页面
 *@author zxy
 *@Date 2012.7.25 pm
 *@Description 点击图片进入图片详细信息页面
 */
package com.minihelper;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.minihelper.callback.OnDownLoadCallBack;

/**
 * 图片浏览、缩放、拖动、自动居中
 */
public class ProessTouchAct extends Activity implements OnTouchListener, OnClickListener, OnDownLoadCallBack {

	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	DisplayMetrics dm;
	ImageView imgView;
	ImageView iv_back;
	Bitmap bitmap;

	float minScaleR;// 最小缩放比例
	static final float MAX_SCALE = 4f;// 最大缩放比例

	static final int NONE = 0;// 初始状态
	static final int DRAG = 1;// 拖动
	static final int ZOOM = 2;// 缩放
	int mode = NONE;

	PointF prev = new PointF();
	PointF mid = new PointF();
	float dist = 1f;
	private String imageUrl;
	private ProcessDownLoadImage mProcessDownLoadImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proesstouch);

		if (getIntent().hasExtra("ImageUrl")) {
			imageUrl = getIntent().getStringExtra("ImageUrl");
		} else {
			finish();
		}
		mProcessDownLoadImage = (ProcessDownLoadImage) findViewById(R.id.imag);

		imgView = (ImageView) findViewById(R.id.image);// 获取控件
		imgView.setOnTouchListener(this);// 设置触屏监听
		imgView.setVisibility(View.GONE);
		mProcessDownLoadImage.setOnDownLoadCallBack(this);
		mProcessDownLoadImage.show(imageUrl);

		iv_back = (ImageView) findViewById(R.id.image_back);
		iv_back.setOnClickListener(this);

	}
	

	/**
	 * 触屏监听
	 */
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// 主点按下
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			prev.set(event.getX(), event.getY());
			mode = DRAG;
			break;
		// 副点按下
		case MotionEvent.ACTION_POINTER_DOWN:
			dist = spacing(event);
			// 如果连续两点距离大于10，则判定为多点模式
			if (spacing(event) > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - prev.x, event.getY() - prev.y);
			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				if (newDist > 10f) {
					matrix.set(savedMatrix);
					float tScale = newDist / dist;
					matrix.postScale(tScale, tScale, mid.x, mid.y);
				}
			}
			break;
		}
		imgView.setImageMatrix(matrix);
		center();
		return true;
	}

	/**
	 * 最小缩放比例，最大为100%
	 */
	private void minZoom() {
		double sc = 0.9;// 80%
		minScaleR = Math.min(((float) sc) * (float) dm.widthPixels / (float) bitmap.getWidth(), ((float) sc) * (float) dm.heightPixels / (float) bitmap.getHeight());
		matrix.postScale(minScaleR, minScaleR);
	}

	private void center() {
		center(true, true);
	}

	/**
	 * 横向、纵向居中
	 */
	protected void center(boolean horizontal, boolean vertical) {

		Matrix m = new Matrix();
		m.set(matrix);
		RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		m.mapRect(rect);

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {
			// 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下放留空则往下移
			int screenHeight = dm.heightPixels;
			if (height < screenHeight) {
				deltaY = (screenHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < screenHeight) {
				deltaY = imgView.getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int screenWidth = dm.widthPixels;
			if (width < screenWidth) {
				deltaX = (screenWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < screenWidth) {
				deltaX = screenWidth - rect.right;
			}
		}
		matrix.postTranslate(deltaX, deltaY);
	}

	/**
	 * 两点的距离
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/**
	 * 两点的中点
	 */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (!bitmap.isRecycled() && bitmap != null) {
			bitmap.recycle();
			System.gc();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_back:
			/*
			 * if (!bitmap.isRecycled() && bitmap != null) { bitmap.recycle();
			 * System.gc(); }
			 */
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void OnDownLoaded(Bitmap bitmap, String filepath, boolean openSelf) {
		if (openSelf) {
			File file = new File(filepath);
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri = Uri.fromFile(file);
			intent.setDataAndType(uri, "image/*");
			startActivity(intent);
			
			this.finish();
		} else {
			this.bitmap = bitmap;
			dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);// 获取分辨率
			minZoom();
			center();
			imgView.setImageMatrix(matrix);
		}
	}

	@Override
	public void OnTypeError() {
		this.finish();
	}

	@Override
	protected void onDestroy() {
		imageUrl=null;
		if (bitmap != null) {
			bitmap.recycle();
		}
		System.gc();
		this.finish();
		super.onDestroy();
	}

}