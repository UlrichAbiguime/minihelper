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

public class LazyImageLoader {
	private String TAG = "ProfileImageCacheManager";
	public final int HANDLER_MESSAGE_ID = 1;
	public String EXTRA_BITMAP = "extra_bitmap";
	public String EXTRA_IMAGE_URL = "extra_image_url";
	public String MACCHA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/barfoo/imagetemp/";
	private ImageManager mImageManager = new ImageManager(ClientApp.mContext);

	private BlockingQueue<String> mUrlList = new ArrayBlockingQueue<String>(50);
	private CallbackManager mCallbackManager = new CallbackManager();

	private GetImageTask mTask = new GetImageTask();

	/**
	 * 取图片, 可能直接从cache中返回, 或下载图片后返回
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
			// bitmap不存在，启动Task进行下载
			mCallbackManager.put(url, callback);
			startDownloadThread(url);
		}
		return bitmap;
	}

	// 异步保存图片
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

		// Start Thread
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

	// Low-level interface to get ImageManager
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
						url = mUrlList.poll(TIMEOUT, TimeUnit.SECONDS); // waiting
						if (null == url) {
							break;
						} // no more, shutdown
					}

					// Bitmap bitmap = ImageCache.mDefaultBitmap;
					// Log.i("download pic...", url);
					final Bitmap bitmap = mImageManager.safeGet(url);
					// use handler to process callback
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
					// callback
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
				// FIXME: 有时会到达这里，原因我还没想明白
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
