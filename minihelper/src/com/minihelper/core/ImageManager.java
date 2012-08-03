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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.http.HttpException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * 管理图标图像的检索和存储。使用put方法下载和存储图像。使用get方法来检索图像的经理。 
 * Manages retrieval and storage of icon images. Use the put method to download and store images. 
 * Use the get method to retrieve images from the manager.
 */
public class ImageManager implements ImageCache {
	private String TAG = "ImageManager";

	/***
	 * 支持596px以上最大宽度缩小，比去年同期。 1192px最大高度，超过从拦截。 
	 * The largest width of the support 596px more than is narrowing year on year. 
	 * The maximum height of 1192px, more than from interception.
	 */
	public int DEFAULT_COMPRESS_QUALITY = 90;
	public int IMAGE_MAX_WIDTH = 596;
	public int IMAGE_MAX_HEIGHT = 1192;

	private Context mContext;
	// In memory cache.
	private Map<String, SoftReference<Bitmap>> mCache;
	// MD5 hasher.
	private MessageDigest mDigest;

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public ImageManager(Context context) {
		mContext = context;
		mCache = new HashMap<String, SoftReference<Bitmap>>();

		try {
			mDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// This shouldn't happen.
			throw new RuntimeException("No MD5 algorithm.");
		}
	}

	public void setContext(Context context) {
		mContext = context;
	}

	private String getHashString(MessageDigest digest) {
		StringBuilder builder = new StringBuilder();

		for (byte b : digest.digest()) {
			builder.append(Integer.toHexString((b >> 4) & 0xf));
			builder.append(Integer.toHexString(b & 0xf));
		}

		return builder.toString();
	}

	// MD5 hases是用来关闭一个URL生成基于文件名。
	// MD5 hases are used to generate filenames based off a URL.
	private String getMd5(String url) {
		mDigest.update(url.getBytes());

		return getHashString(mDigest);
	}

	// 看起来如果图像是在文件系统中。
	// Looks to see if an image is in the file system.
	private Bitmap lookupFile(String url) {
		String hashedUrl = getMd5(url);
		FileInputStream fis = null;

		try {
			fis = mContext.openFileInput(hashedUrl);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			// Not there.
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// Ignore.
				}
			}
		}
	}

	/**
	 * 下载文件 Downloads a file
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public Bitmap downloadImage(String url) throws IOException {
		Log.d(TAG, "Fetching image: " + url);
		return BitmapFactory.decodeStream(GetStream(url));
	}

	public InputStream GetStream(String fileUrl) throws IOException {

		URL myFileUrl = null;
		try {
			myFileUrl = new URL(fileUrl);
			Log.v("file_url", fileUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.v("error", "no url");
		}

		HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
		conn.setDoInput(true);
		conn.connect();
		InputStream is = conn.getInputStream();
		return is;

	}

	public Bitmap downloadImage2(String url) throws IOException {
		Log.d(TAG, "????[NEW]Fetching image: " + url);
		String file = writeToFile(GetStream(url), getMd5(url));
		return BitmapFactory.decodeFile(file);
	}

	/**
	 * 下载远程图片 - >转换位图 - >写入缓冲区。 
	 * Download remote images -> converted to the Bitmap -> write buffer.
	 * 
	 * @param url
	 * @param image
	 *            quality 1～100
	 * @throws IOException
	 * @throws HttpException
	 */
	public void put(String url, int quality, boolean forceOverride) throws IOException {
		if (!forceOverride && contains(url)) {
			// Image already exists.
			return;
		}

		Bitmap bitmap = downloadImage(url);
		if (bitmap != null) {
			put(url, bitmap, quality); // file cache
		} else {
			Log.w(TAG, "Retrieved bitmap is null.");
		}
	}

	/**
	 * 重载 put(String url, int quality) Overloaded put(String url, int quality)
	 * 
	 * @param url
	 * @throws IOException
	 * @throws HttpException
	 */
	public void put(String url) throws IOException {
		put(url, DEFAULT_COMPRESS_QUALITY, false);
	}

	/**
	 * 转换本地文件 - >位图 - >写入缓冲区大小的图片，超过MAX_WIDTH/ MAX_HEIGHT如果将图像缩放。 
	 * Convert Local File -> Bitmap -> write buffer if the size of the picture to exceed
	 * MAX_WIDTH / MAX_HEIGHT will be on image scaling.
	 * 
	 * @param file
	 * @param Picture
	 *            quality (0 to 100)
	 * @param forceOverride
	 * @throws IOException
	 */
	public void put(File file, int quality, boolean forceOverride) throws IOException {
		if (!file.exists()) {
			Log.w(TAG, file.getName() + " is not exists.");
			return;
		}
		if (!forceOverride && contains(file.getPath())) {
			// 图片已经存在。
			// Image already exists.
			Log.d(TAG, file.getName() + " is exists");
			return;
		}

		Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
		// bitmap = resizeBitmap(bitmap, MAX_WIDTH, MAX_HEIGHT);

		if (bitmap == null) {
			Log.w(TAG, "Retrieved bitmap is null.");
		} else {
			put(file.getPath(), bitmap, quality);
		}
	}

	/**
	 * 位图写入缓冲区。
	 * 
	 * @param file
	 *            path
	 * @param bitmap
	 * @param quality
	 *            1~100
	 */
	public void put(String file, Bitmap bitmap, int quality) {
		synchronized (this) {
			mCache.put(file, new SoftReference<Bitmap>(bitmap));
		}

		writeFile(file, bitmap, quality);
	}

	/**
	 * put(String file, Bitmap bitmap, int quality)
	 * 
	 * @param file  path
	 * @param bitmap
	 * @param quality 1~100
	 */
	public void put(String file, Bitmap bitmap) {
		put(file, bitmap, DEFAULT_COMPRESS_QUALITY);
	}

	/**
	 * 保存图像 Save the image
	 * 
	 * @param file
	 * @param bitmap
	 */
	public void save(String file, Bitmap bitmap) {
		writeFileNation(file, bitmap, DEFAULT_COMPRESS_QUALITY);
	}

	/**
	 * 位图写入到本地缓存文件。 Bitmap written to the local cache file.
	 * 
	 * @param file
	 *            URL/PATH
	 * @param bitmap
	 * @param quality
	 */
	private void writeFile(String file, Bitmap bitmap, int quality) {
		if (bitmap == null) {
			Log.w(TAG, "Can't write file. Bitmap is null.");
			return;
		}

		BufferedOutputStream bos = null;
		try {
			String hashedUrl = getMd5(file);

			bos = new BufferedOutputStream(mContext.openFileOutput(hashedUrl, Context.MODE_PRIVATE));
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos); // PNG
			Log.d(TAG, "Writing file: " + file);
		} catch (IOException ioe) {
			Log.e(TAG, ioe.getMessage());
		} finally {
			try {
				if (bos != null) {
					bitmap.recycle();
					bos.flush();
					bos.close();
				}
				// bitmap.recycle();
			} catch (IOException e) {
				Log.e(TAG, "Could not close file.");
			}
		}
	}

	private void writeFileNation(String file, Bitmap bitmap, int quality) {
		if (bitmap == null) {
			return;
		}

		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos); // PNG
		} catch (IOException ioe) {
			Log.e(TAG, ioe.getMessage());
		} finally {
			try {
				if (bos != null) {
					bitmap.recycle();
					bos.flush();
					bos.close();
				}
				// bitmap.recycle();
			} catch (IOException e) {
				Log.e(TAG, "Could not close file.");
			}
		}
	}

	/**
	 * 写入文件 write To File
	 * 
	 * @param is
	 * @param filename
	 * @return
	 */
	private String writeToFile(InputStream is, String filename) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(is);
			out = new BufferedOutputStream(mContext.openFileOutput(filename, Context.MODE_PRIVATE));
			byte[] buffer = new byte[1024];
			int l;
			while ((l = in.read(buffer)) != -1) {
				out.write(buffer, 0, l);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		Log.e("filedir:", mContext.getFilesDir().toString());
		return mContext.getFilesDir() + "/" + filename;
	}

	public Bitmap get(File file) {
		return get(file.getPath());
	}

	/**
	 * 判断存在相应的位图缓存文件 
	 * Judgment the existence of the file corresponding to the bitmap cache with
	 * 
	 * @param file
	 * @return
	 */
	public boolean isContains(String file) {
		return mCache.containsKey(file);
	}

	/**
	 * 指定相应的位图文件/ URL，查找本地文件，如果直接使用，或去网上获取 
	 * Designation for the file / URL corresponding to Bitmap, Find a local file, 
	 * if the direct use, or go online to obtain
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Bitmap safeGet(String file) throws IOException {
		Log.e("will find sys file....", "");
		Bitmap bitmap = lookupFile(file); // first try file.
		Log.e("find sys file....", bitmap + "");
		if (bitmap != null) {
			synchronized (this) { // memory cache
				mCache.put(file, new SoftReference<Bitmap>(bitmap));
			}
			return bitmap;

		} else { // get from web
			String url = file;
			bitmap = downloadImage2(url);

			// Comment out to test the new written to the file
			// put(file, bitmap); // file Cache
			return bitmap;
		}
	}

	/**
	 * 从缓存中读取文件 Read the file from the cache
	 * 
	 * @param File
	 *            URL/file PATH
	 * @param bitmap
	 * @param quality
	 */
	public Bitmap get(String file) {
		SoftReference<Bitmap> ref;
		Bitmap bitmap;

		// 先看看内存中。
		// Look in memory first.
		synchronized (this) {
			ref = mCache.get(file);
		}

		if (ref != null) {
			bitmap = ref.get();

			if (bitmap != null) {
				return bitmap;
			}
		}

		// 现在尝试文件
		// Now try file.
		bitmap = lookupFile(file);

		if (bitmap != null) {
			synchronized (this) {
				mCache.put(file, new SoftReference<Bitmap>(bitmap));
			}
			return bitmap;
		}

		Log.w(TAG, "Image is missing: " + file);
		// 返回的默认照片
		// return the default photo
		return null;
	}

	public boolean contains(String url) {
		return get(url) != null;
	}

	/**
	 * delete file 删除文件
	 */
	public void clear() {
		String[] files = mContext.fileList();

		for (String file : files) {
			mContext.deleteFile(file);
		}

		synchronized (this) {
			mCache.clear();
		}
	}

	/**
	 * 查找文件 Find Files
	 * 
	 * @param keepers
	 */
	public void cleanup(HashSet<String> keepers) {
		String[] files = mContext.fileList();
		HashSet<String> hashedUrls = new HashSet<String>();

		for (String imageUrl : keepers) {
			hashedUrls.add(getMd5(imageUrl));
		}

		for (String file : files) {
			if (!hashedUrls.contains(file)) {
				mContext.deleteFile(file);
			}
		}
	}

	/**
	 * 压缩和调整图像 Compress and resize the Image
	 * 
	 * <br />
	 * Regardless of image size and dimensions, will the picture is a lossy
	 * compression, so the local compression should consider the loss of picture
	 * quality caused by the pictures will be of secondary compression
	 * 
	 * @param targetFile
	 * @param quality
	 *            , 0~100, recommend 100
	 * @return
	 * @throws IOException
	 */
	public File compressImage(File targetFile, int quality) throws IOException {
		String filepath = targetFile.getAbsolutePath();

		// 1. 计算规模
		// 1. Calculate scale
		int scale = 1;
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, o);
		if (o.outWidth > IMAGE_MAX_WIDTH || o.outHeight > IMAGE_MAX_HEIGHT) {
			scale = (int) Math.pow(2.0, (int) Math.round(Math.log(IMAGE_MAX_WIDTH / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
			// scale = 2;
		}
		Log.d(TAG, scale + " scale");

		// 2. 文件 - >位图（选举较小的图片）
		// 2. File -> Bitmap (Returning a smaller image)
		o.inJustDecodeBounds = false;
		o.inSampleSize = scale;
		Bitmap bitmap = BitmapFactory.decodeFile(filepath, o);

		// 2. 调整位图
		// 2. Resize Bitmap
		// bitmap = resizeBitmap(bitmap, IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT);

		// 3. 位图 - >文件
		// 3. Bitmap -> File
		writeFile(filepath, bitmap, quality);

		// 4. 调整大小的图像文件
		// 4. Get resized Image File
		String filePath = getMd5(targetFile.getPath());
		File compressedImage = mContext.getFileStreamPath(filePath);
		return compressedImage;
	}

	/**
	 * 保持长宽比缩小Bitmap
	 * 
	 * @param bitmap
	 * @param maxWidth
	 * @param maxHeight
	 * @param quality
	 *            1~100
	 * @return
	 */
	public Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {

		int originWidth = bitmap.getWidth();
		int originHeight = bitmap.getHeight();

		// no need to resize
		// 没有需要调整
		if (originWidth < maxWidth && originHeight < maxHeight) {
			return bitmap;
		}

		int newWidth = originWidth;
		int newHeight = originHeight;

		// 若图片过宽, 则保持长宽比缩放图片
		// If the picture is too wide, you maintain the aspect ratio to scale
		// the image
		if (originWidth > maxWidth) {
			newWidth = maxWidth;

			double i = originWidth * 1.0 / maxWidth;
			newHeight = (int) Math.floor(originHeight / i);

			bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
		}

		// 若图片过长, 则从中部截取
		// If the picture is too long, from the Central interception
		if (newHeight > maxHeight) {
			newHeight = maxHeight;

			int half_diff = (int) ((originHeight - maxHeight) / 2.0);
			bitmap = Bitmap.createBitmap(bitmap, 0, half_diff, newWidth, newHeight);
		}

		Log.d(TAG, newWidth + " width");
		Log.d(TAG, newHeight + " height");

		return bitmap;
	}

}
