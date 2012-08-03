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

import android.graphics.Bitmap;
/***
 * 为获取图片转换bitmap提供接口
 * Providing an interface for access to image convert bitmap
 */
public interface ImageCache {

	public static Bitmap mDefaultBitmap = null;

	public Bitmap get(String url);

	public void put(String url, Bitmap bitmap);
}
