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
 * Email:namezheng@gmail.com
 */

package com.minihelper.core;

import java.io.IOException;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.minihelper.ClientApp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/***
 * 得到的图片调用主程序 Get the picture call main program
 */
public class LazyImageLoader {

	public final int HANDLER_MESSAGE_ID = 1;

	private String TAG = "ProfileImageCacheManager";
	public String EXTRA_BITMAP = "extra_bitmap";
	public String EXTRA_IMAGE_URL = "extra_image_url";
	public String MACCHA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/barfoo/imagetemp/";

	private ImageManager mImageManager = new ImageManager(ClientApp.mContext);
	private BlockingQueue<String> mUrlList = new ArrayBlockingQueue<String>(50);
	private CallbackManager mCallbackManager = new CallbackManager();
	private GetImageTask mTask = new GetImageTask();

	/**
	 * 得到的图片，可直接从高速缓存，或下载图片返回返回
	 * 
	 * Get the picture, may be directly returned from the cache, or download
	 * pictures and returned to
	 * 
	 * @param url
	 * @param callback
	 * @return
	 */
	public Bitmap get(String url, ImageLoaderCallback callback) {
		Bitmap bitmap = null;
		if (mImageManager.isContains(url)) {
			bitmap = mImageManager.get(url);
		} else {
			// 位图不存在，启动任务下载
			// The bitmap does not exist, start the Task download
			mCallbackManager.put(url, callback);
			startDownloadThread(url);
		}
		return bitmap;
	}

	/***
	 * 异步保存的图像 Asynchronous to save the image
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void save(String url) throws IOException {
		Bitmap bitmap = null;
		try {
			bitmap = mImageManager.safeGet(url);
			Util.initPath(MACCHA_PATH);
			String file = MACCHA_PATH + Util.getNowTime() + "_.jpg";
			Log.i("save_path", file);
			mImageManager.save(file, bitmap);

		} catch (IOException e) {
			throw e;
		}

	}

	private void startDownloadThread(String url) {
		if (url != null) {
			addUrlToDownloadQueue(url);
		}

		// 启动线程
		// Start thread
		State state = mTask.getState();
		if (Thread.State.NEW == state) {
			mTask = new GetImageTask();
			mTask.start(); // first start
		} else if (Thread.State.TERMINATED == state) {
			mTask = new GetImageTask(); // restart
			mTask.start();
		}
	}

	private void addUrlToDownloadQueue(String url) {
		if (!mUrlList.contains(url)) {
			try {
				mUrlList.put(url);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/***
	 * 低级别的接口得到
	 * ImageManager Low-level interface to get the ImageManager
	 * 
	 * @return mImageManager
	 */
	public ImageManager getImageManager() {
		return mImageManager;
	}

	private class GetImageTask extends Thread {
		private volatile boolean mTaskTerminated = false;
		private static final int TIMEOUT = 3 * 60;
		private boolean isPermanent = true;

		@Override
		public void run() {
			try {
				while (!mTaskTerminated) {
					String url;
					if (isPermanent) {
						url = mUrlList.take();
					} else {
						url = mUrlList.poll(TIMEOUT, TimeUnit.SECONDS); // 等待
						if (null == url) {
							break;
						} // No more closed
					}

					// Bitmap bitmap = ImageCache.mDefaultBitmap;
					final Bitmap bitmap = mImageManager.safeGet(url);
					// 为了处理回调处理程序
					// To handle the callback handler
					final Message m = handler.obtainMessage(HANDLER_MESSAGE_ID);
					Bundle bundle = m.getData();
					bundle.putString(EXTRA_IMAGE_URL, url);
					bundle.putParcelable(EXTRA_BITMAP, bitmap);
					handler.sendMessage(m);
				}
			} catch (InterruptedException e) {
				Log.w(TAG, e.getMessage());
			} catch (IOException e) {
			} finally {
				Log.v(TAG, "Get image task terminated.");
				mTaskTerminated = true;
			}
		}

		@SuppressWarnings("unused")
		public boolean isPermanent() {
			return isPermanent;
		}

		@SuppressWarnings("unused")
		public void setPermanent(boolean isPermanent) {
			this.isPermanent = isPermanent;
		}

		@SuppressWarnings("unused")
		public void shutDown() throws InterruptedException {
			mTaskTerminated = true;
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLER_MESSAGE_ID:
				final Bundle bundle = msg.getData();
				String url = bundle.getString(EXTRA_IMAGE_URL);
				Bitmap bitmap = (Bitmap) (bundle.get(EXTRA_BITMAP));
				// 回调
				// Callback
				mCallbackManager.call(url, bitmap);
				break;
			default:
				// do nothing.
				break;
			}
		}
	};

	public interface ImageLoaderCallback {
		void refresh(String url, Bitmap bitmap);
	}

	public static class CallbackManager {
		private ConcurrentHashMap<String, List<ImageLoaderCallback>> mCallbackMap;

		public CallbackManager() {
			mCallbackMap = new ConcurrentHashMap<String, List<ImageLoaderCallback>>();
		}

		public void put(String url, ImageLoaderCallback callback) {
			if (!mCallbackMap.containsKey(url)) {
				mCallbackMap.put(url, new ArrayList<ImageLoaderCallback>());
				// mCallbackMap.put(url, Collections.synchronizedList(new
				// ArrayList<ImageLoaderCallback>()));
			}

			mCallbackMap.get(url).add(callback);
		}

		public void call(String url, Bitmap bitmap) {
			List<ImageLoaderCallback> callbackList = mCallbackMap.get(url);
			if (callbackList == null) {
				// FIXME: 有时来到这里，因为我没有想明白
				// FIXME: Sometimes arrived here, because I did not want to
				// understand
				return;
			}
			for (ImageLoaderCallback callback : callbackList) {
				if (callback != null) {
					callback.refresh(url, bitmap);
				}
			}

			callbackList.clear();
			mCallbackMap.remove(url);
		}

	}

}
