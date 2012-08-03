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

package com.minihelper.core;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.minihelper.core.LazyImageLoader.ImageLoaderCallback;

/***
 * External program calls download pictures entrance Usage:
 * SimpleImageLoader.display(imageView,imageUrl);
 */
public class SimpleImageLoader {

	public static LazyImageLoader lazyimageloader = new LazyImageLoader();

	public static void display(final ImageView imageView, String url) {
		imageView.setTag(url);
		imageView.setImageBitmap(lazyimageloader.get(url, createImageViewCallback(imageView, url)));
	}

	public static void display(final ImageView imageView, String url, int width, DisplayCallBack dcb) {
		imageView.setTag(url);
		imageView.setImageBitmap(lazyimageloader.get(url, createImageViewCallback(imageView, url, width, dcb)));
	}

	public static ImageLoaderCallback createImageViewCallback(final ImageView imageView, String url) {
		return new ImageLoaderCallback() {
			@Override
			public void refresh(String url, Bitmap bitmap) {
				if (url.equals(imageView.getTag())) {
					imageView.setImageBitmap(bitmap);
				}
			}
		};
	}

	public static ImageLoaderCallback createImageViewCallback(final ImageView imageView, String url, final int width, final DisplayCallBack dcb) {
		return new ImageLoaderCallback() {
			@Override
			public void refresh(String url, Bitmap bitmap) {
				if (url.equals(imageView.getTag())) {
					imageView.setImageBitmap(bitmap);
					if (width > 0) {
						Matrix matrix = new Matrix();
						matrix.postScale((float) ((double) width / (double) bitmap.getWidth()), (float) ((double) width / (double) bitmap.getWidth()));
						imageView.setImageMatrix(matrix);
						dcb.onCallBack();
					}
				}
			}
		};
	}

	/**
	 * 保存图片 Save the image
	 * 
	 * @param url
	 * @throws IOException
	 */
	public static void save(String url) throws IOException {
		lazyimageloader.save(url);
	}

	public static Bitmap get(String url, ImageLoaderCallback callback) {
		return lazyimageloader.get(url, callback);
	}

	public interface DisplayCallBack {
		public void onCallBack();
	}

}
