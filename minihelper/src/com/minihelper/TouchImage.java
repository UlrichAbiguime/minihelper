/**
 * Copyright 2012 minihelper Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Email：namezheng@gmail.com
 */
package com.minihelper;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.minihelper.core.AsyncRunner;
import com.minihelper.core.BaseRequestListener;
import com.minihelper.core.HttpRequstError;
import com.minihelper.core.LazyImageLoader.ImageLoaderCallback;
import com.minihelper.core.SimpleImageLoader;

/**
 * 图片浏览、缩放、拖动、自动居中
 */
public class TouchImage extends Activity implements OnTouchListener, OnClickListener {

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
	private ProgressDialog mDialog;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			SimpleImageLoader.get(imageUrl, new ImageLoaderCallback() {
				
				public void refresh(String url, Bitmap _bitmap) {
					bitmap = _bitmap;
					imgView.setImageBitmap(bitmap);// 填充控件
					dm = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(dm);// 获取分辨率
					minZoom();
					center();
					imgView.setImageMatrix(matrix);
					mDialog.dismiss();
				}
			});
			
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touch);
		mDialog = new ProgressDialog(this);
		mDialog.setTitle("请稍等");
		mDialog.setMessage("正在加载图片...");
		if (getIntent().hasExtra("ImageUrl")) {
			imageUrl = getIntent().getStringExtra("ImageUrl");
		} else {
			finish();
		}
		imgView = (ImageView) findViewById(R.id.imag);// 获取控件
		imgView.setOnTouchListener(this);// 设置触屏监听

		iv_back = (ImageView) findViewById(R.id.image_back);
		iv_back.setOnClickListener(this);

		loadImage();
	}

	private void loadImage() {
		AsyncRunner.HttpGet(new BaseRequestListener() {
			@Override
			public void onReading() {
				mDialog.show();
				super.onReading();
			}

			@Override
			public void onRequesting() throws HttpRequstError, JSONException {
				Message msg = handler.obtainMessage();
				handler.sendMessage(msg);
				super.onRequesting();
			}

			@Override
			public void HttpRequstError(HttpRequstError e) {
				mDialog.dismiss();
				super.HttpRequstError(e);
			}
		});
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
			if (!bitmap.isRecycled() && bitmap != null) {
				bitmap.recycle();
				System.gc();
			}
			finish();
			break;

		default:
			break;
		}
	}
}