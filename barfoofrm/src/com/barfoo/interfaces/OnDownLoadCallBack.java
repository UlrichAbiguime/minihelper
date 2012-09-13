/**
 *
 *@Class OnDownLoadCallBack.java
 *@author zxy
 *@date 2012-2012-8-29-下午3:47:08
 *@Description 说明
 */
/**
 * zxy
 * 2012{date}{time}
 * OnDownLoadCallBack.java
 */
package com.barfoo.interfaces;

import android.graphics.Bitmap;

/**
 * 
 * @Class OnDownLoadCallBack.java
 * @author zxy
 * @data 2012-2012-8-29-下午3:47:08
 * @Description 说明
 */
public interface OnDownLoadCallBack {
	void OnDownLoaded(Bitmap bitmap, String filepath,boolean openSelf);
	void OnTypeError();
}

