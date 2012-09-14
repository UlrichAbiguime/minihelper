/**
 * @author zn Sep 13, 2012 12:42:14 PM
 * @Description:
 对图片的处理
 	一、读取图片
		1.内存缓存获取:当图片已经下载,可从内存缓存中获取图片，但由于内存紧张致使内存的缓存中的图片可能被回收了,可以从sd中获取
		2.SD卡中获取:通过图片的路径获取图片,若SD卡中也没有该图片，可从网络上直接下载 
		3.网络上下载获取
	二、写入图片(将图片保存)
		1.内存缓存
		2.SD卡中
 */
package com.barfoo.core;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.barfoo.app.R;

import dalvik.system.VMRuntime;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;


public class ImageLoad {
	private static HashMap<String, SoftReference<Bitmap>> imageCache;
	private static ExecutorService pool;
	private final static int CWJ_HEAP_SIZE = 6* 1024* 1024 ;  
	private final static int DOWN_IMAGESIZE = 2* 1024* 1024 ;//下载超过2M进行压缩
	/**
	 * 内存图片软引用缓冲
	 */
	private static String IMAGEDIRPATH = "temodir";
	private Bitmap checkbitmap;// 查找是否存在Bitmap
	static int savesImagetate = 0;
	String filePath = null;
	String imagepathurl = null;

	static {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
		pool = Executors.newFixedThreadPool(3); // 固定线程池
	}

	public ImageLoad() {
		VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE); //设置最小heap内存为8MB大小
	}

	/**
	 * 判断是否已经下载过该图片，先从缓存中查找看是否存在，若没有在从SD卡中找，若没有则从网络上下载
	 * 
	 * @param imageURL
	 * @param imageCallBack
	 * @return
	 */
	public void loadBitmap(String imageURL, ImageView imageView,int imageQuality) {
		imagepathurl = imageURL;
		// 从内存缓存中查找图片,并返回图片
		filePath = getHashString(imageURL);
		Log.i("filePath", filePath);
		checkbitmap = getBitmapFromCache(filePath);

		if (checkbitmap != null) {
			imageView.setImageBitmap(checkbitmap);
		} else {// 下载图片同时设置保存在SD卡中的图片品质
			DownLoadImageFromNetWork(imageURL, imageView,imageQuality);
		}

	}

	/**
	 * 从网络上下载图片
	 * 
	 * @param imageUrl
	 * @param imageView
	 */
	public void DownLoadImageFromNetWork(final String imageUrl, final ImageView imageView,final int imageQuality) {
		/* Create handler in UI thread. */
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				imageView.setImageBitmap(checkbitmap);
				// 异步的形式将图片保存在SD卡中
				new Thread(new Runnable() {
					@Override
					public void run() {
						saveImageFile(checkbitmap, imageUrl,imageQuality);
					}
				}).start();
				Log.e("cache", imageCache.size() + "");
			}
		};

		final String _filePath = filePath;
		// 通过线程池的方式下载图片，下载完成后将通过Hander通知主线程刷新UI(显示图片)
		imageView.setImageResource(R.drawable.ic_action_search);
		pool.execute(new Runnable() {
			public void run() {
				Message message = Message.obtain();
				checkbitmap = downloadBitmap(imageUrl, _filePath);
				handler.sendMessage(message);
			}
		});
	}

	/**
	 * 通过URL将图片从内存的缓存中取出Bitmap图片
	 * 
	 * @param filepath
	 *            (图片的地址)
	 * @return
	 */
	public Bitmap getBitmapFromCache(String filepath) {
		// 判断是否存在imageURl,若存在从内存缓存中取出Bitmap
		imagepathurl = getSDImageDirPath() + "/" + filepath;
		if (imageCache.containsKey(filePath)) {
			Log.i("success", "cache");
			return imageCache.get(filePath).get();
		} else {
			File file = new File(imagepathurl);
			if (file.exists()) {
				Log.i("success", "SD");
				return BitmapFactory.decodeFile(imagepathurl);
			} else {
				Log.i("down", "download");
				return null;
			}
		}
	}

	/**
	 * 下载图片-可指定显示图片的高宽
	 * 
	 * @param url
	 * @param width
	 * @param height
	 */
	public Bitmap downloadBitmap(String imageUrl, String file) {
		// http加载图片
		checkbitmap = getImageBitmapFromNetWork(imageUrl);
		// 放入缓存
		imageCache.put(file, new SoftReference<Bitmap>(checkbitmap));
		Log.i("cache:", file);
		return checkbitmap;
	}

	/**
	 * 拼接文件夹路径
	 * 
	 * @return String(imagePath)保存图片的文件夹路径
	 */
	public String getSDImageDirPath() {
		return Environment.getExternalStorageDirectory().getPath() + "/"
				+ IMAGEDIRPATH;
	}

	/**
	 * 将ImageURL 加密后最为图片的名称
	 * 
	 * @param imageUrl
	 * @return
	 */
	private String getHashString(String url) {
		try {
			MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(url.getBytes());
			StringBuilder builder = new StringBuilder();

			for (byte b : mDigest.digest()) {
				builder.append(Integer.toHexString((b >> 4) & 0xf));
				builder.append(Integer.toHexString(b & 0xf));
			}

			Log.i(url, builder.toString());
			return builder.toString() + ".jpg";
		} catch (NoSuchAlgorithmException e) {
			return "defultimg";
		}
	}

	/**
	 * 创建图片保存完整路径
	 * 
	 * @return String (图片的完整路径)
	 */
	public String getImagePath(String imageUrl) {
		return getSDImageDirPath() + "/" + getHashString(imageUrl);
	}

	/**
	 * 保存图片
	 * 
	 * @param bm
	 * @param imagepath
	 * @param imageQuality(保存图片的品质)
	 * @throws IOException
	 * @return 0、没有剩余的空间可以存储；1、为保存成功；2.为图片已经存在
	 */
	public int saveFile(Bitmap bm, String imageUrl,int imageQuality) throws IOException {
		int savestate = 0;
		// getSDImageDirPath当其不为空,说明sd卡的存储设备良好,可以进行存储
		if (getSDImageDirPath() != null) {
			File dirFile = new File(getSDImageDirPath());
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			File myImageFile = new File(getImagePath(imageUrl));
			if (!myImageFile.exists()) {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(myImageFile));
				bm.compress(Bitmap.CompressFormat.JPEG, imageQuality, bos);
				bos.flush();
				bos.close();
				// bm.recycle();
				savestate = 1;
			} else {
				Log.i("exist", "图片已存在!");
				savestate = 2;
			}

		} else {
			savestate = 0;
			Log.i("error", "SD卡不能存储");
		}

		return savestate;
	}

	/**
	 * 通过异步的形式进行存储图片
	 * 
	 * @param bitmap
	 * @param imageName
	 */

	public void saveImageFile(final Bitmap bitmap, final String imageUrl,final int imageQuality ) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					savesImagetate = saveFile(bitmap, imageUrl,imageQuality);
					switch (savesImagetate) {
					case 0:
						Log.e("state", "SD卡没有可存储的空间！");
						break;
					case 1:
						Log.e("state", "存储成功！");
						break;
					case 2:
						Log.e("state", "图片已经存在！");
						break;
					}

				} catch (IOException e) {
					Log.e("error", "存储图片失败!" + e.getMessage());
				}finally{
					if(bitmap!=null){
						bitmap.recycle();
					}
				}
			}
		}).start();
	}

	/**
	 * 从SD卡中获取图片,根据图片的路径找到图片
	 * 
	 * @param imageUrl
	 * @return
	 */
	public Bitmap getImageFromSD(String imageUrl) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		return BitmapFactory.decodeFile(filePath,options);
	}

	/**
	 * 通过URL从网络上获取图片
	 * 
	 * @param httpURL
	 *            (图片地址)
	 * @return Bitmap(图片)
	 */
	public static Bitmap getImageBitmapFromNetWork(String httpURL) {
		Bitmap bitmap = null;
		String httpurl = httpURL;
		InputStream is = null;
		try {
			// 获取从服务器端的返回来的数据
			URL url = new URL(httpurl);
			Log.w("httpurl", httpURL);
			// 打开链接管道
			HttpURLConnection urlconn = (HttpURLConnection) url
					.openConnection();
			// urlconn.setRequestMethod("GET");
			urlconn.setDoInput(true);
			// 设置链接超时的时间(时间设置成可配)
			// TODO
			urlconn.setConnectTimeout(20000);
			// 建立链接管道
			urlconn.connect();
			// 获取字节流
			is = urlconn.getInputStream();
			BitmapFactory.Options options = new BitmapFactory.Options();
			byte[] bt = getBytes(is);

			if (bt.length >= DOWN_IMAGESIZE) {
				options.inSampleSize = 4;
			} else {
				options.inSampleSize = 2;
			}
			bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.length, options);
			is.close();
			urlconn.disconnect();
			Log.i("done", "done3");

		} catch (MalformedURLException e) {
			Log.e("error", e.getMessage());
		} catch (IOException e) {
			Log.e("error", e.getMessage());
		}
		return bitmap;
	}

	// 将字节流转化成带缓存的字节流
	private static byte[] getBytes(InputStream is) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] b = new byte[1024];
		int len = 0;
		while ((len = is.read(b, 0, 1024)) != -1) {
			baos.write(b, 0, len);
			baos.flush();
		}
		byte[] bytes = baos.toByteArray();
		return bytes;
	}
}
