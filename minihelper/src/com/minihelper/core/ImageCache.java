package com.minihelper.core;

import android.graphics.Bitmap;

public interface ImageCache {

	public static Bitmap mDefaultBitmap = null;

	public Bitmap get(String url);

	public void put(String url, Bitmap bitmap);
}
