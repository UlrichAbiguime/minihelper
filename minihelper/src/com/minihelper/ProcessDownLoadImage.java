/**
 * ProcessDownLoadImage.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-9-10
 */
package com.minihelper;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.minihelper.callback.OnDownLoadCallBack;
import com.minihelper.core.CustomHttpClient;

/**
 * @author comger
 * 
 */
public class ProcessDownLoadImage extends LinearLayout {

	String status;
	RelativeLayout rliv;
	ImageView ivdown;
	LinearLayout wrap;
	DownloadTask task;
	Bitmap bitmap = null;
	boolean isSave = true;
	String rootDir = "";
	String filepath;
	String filename;
	OnDownLoadCallBack callback = null;

	public ProcessDownLoadImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.processdownload, this, true);
		rootDir = initPath("barfooimgtemp");
		ivdown = (ImageView) findViewById(R.id.ivdown);
		rliv = (RelativeLayout) findViewById(R.id.rliv);
		wrap = (LinearLayout) findViewById(R.id.ivdemowrap);
		task = new DownloadTask();
	}

	// 初始化路径,无则创建
	/**
	 * if has sdcard: dir = sdcard + filepath else: dir = root +filepath
	 * 
	 * @param filepath
	 * @return
	 */
	private String initPath(String filepath) {
		String dir = null;
		status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			dir = Environment.getExternalStorageDirectory().getPath() + "/" + filepath + "/";
			File mypic_dirs = new File(dir);
			if (!mypic_dirs.exists()) {
				mypic_dirs.mkdirs();
			}
		} else {
			dir = ClientApp.mContext.getFilesDir() + "/";
		}
		return dir;
	}

	public void setIsSave(boolean issave) {
		this.isSave = issave;
	}

	public void setImageView(ImageView showImageView) {
		ivdown = showImageView;
	}

	public void setOnDownLoadCallBack(OnDownLoadCallBack callback) {
		this.callback = callback;
	}

	public void show(final String imgUrl) {
		// 需要判定是否以下载图片，如果已下载，从本地读取
		filename = getHashString(imgUrl);
		filepath = rootDir + filename;
		File file = new File(filepath);
		if (file.exists()) {
			callback.OnDownLoaded(null, filepath, true);
		} else {
			task.execute(imgUrl);
		}
	}

	class DownloadTask extends AsyncTask<String, Integer, Bitmap> {

		private byte[] images;

		@Override
		protected Bitmap doInBackground(String... params) {
			String imgUrl = params[0];
			try {
				if (imgUrl.startsWith("https")) {
					SSLContext sc = SSLContext.getInstance("TLS");
					sc.init(null, new TrustManager[] { new MyTrustManager() }, new SecureRandom());
					HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
					HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
					HttpsURLConnection conn = (HttpsURLConnection) new URL(imgUrl).openConnection();
					//conn.setDoOutput(true);
					conn.setDoInput(true);
					conn.connect();

					byte[] buf = new byte[1024 * 4];
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 
					InputStream is = conn.getInputStream();
					//bitmap = BitmapFactory.decodeStream(is);
					
					int len = 0;
					int readcount = 0;
					int processVal = 0;
					int max = conn.getContentLength();
					while ((len = is.read(buf)) != -1) {
						outputStream.write(buf, readcount, len);
						processVal += len;
						publishProgress(processVal * 100 / max);// 通知要更新processBar
					}
					images = outputStream.toByteArray();
					outputStream.flush();
					outputStream.close();
					
				} else {
					HttpClient httpClient = CustomHttpClient.getHttpClient();
					HttpGet request = new HttpGet(imgUrl);
					publishProgress(25);
					HttpResponse response = httpClient.execute(request);
					publishProgress(30);
					images = EntityUtils.toByteArray(response.getEntity());
					publishProgress(50);
				}

				BitmapFactory.Options options = new Options();
				options.inDither = false;
				options.inPreferredConfig = null;
				if (!isSave) {
					if (images.length >= 2097152) {
						options.inSampleSize = 4;
					} else {
						options.inSampleSize = 2;
					}
				}

				bitmap = BitmapFactory.decodeByteArray(images, 0, images.length, options);
				publishProgress(100);
			} catch (IOException e) {
				return null;
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return bitmap;
		}

		protected void onPostExecute(Bitmap result) {
			// 后台任务执行完之后被调用，在ui线程执行
			if (result == null) {
				Toast.makeText(ClientApp.mContext, "图片地址错误！", Toast.LENGTH_SHORT).show();
				callback.OnTypeError();
				return;
			}

			if (!isSave) {
				showSelf(result);
			} else {
				if (writeFileNation(filepath, result, 100)) {
					callback.OnDownLoaded(result, filepath, true);
				} else {
					showSelf(result);
				}
			}

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int value = values[0];
			ViewGroup.LayoutParams lparams = wrap.getLayoutParams();
			lparams.height = (int) (rliv.getLayoutParams().height * value * 0.01);
			wrap.setLayoutParams(lparams);
		}

	}

	private void showSelf(Bitmap result) {
		rliv.setVisibility(View.GONE);
		ivdown.setImageBitmap(result);
		ivdown.setVisibility(View.VISIBLE);

		if (callback != null) {
			callback.OnDownLoaded(result, null, false);
		}
	}

	private String getHashString(String url) {
		try {
			MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(url.getBytes());
			StringBuilder builder = new StringBuilder();

			for (byte b : mDigest.digest()) {
				builder.append(Integer.toHexString((b >> 4) & 0xf));
				builder.append(Integer.toHexString(b & 0xf));
			}
			return builder.toString() + ".jpg";
		} catch (NoSuchAlgorithmException e) {
			return "defultimg";
		}
	}

	private boolean writeFileNation(String file, Bitmap bitmap, int quality) {
		boolean flag = false;
		if (bitmap == null) {
			return flag;
		}

		BufferedOutputStream bos = null;
		try {
			if (status.equals(Environment.MEDIA_MOUNTED)) {
				bos = new BufferedOutputStream(new FileOutputStream(file));
			} else {
				bos = new BufferedOutputStream(ClientApp.mContext.openFileOutput(filename, Context.MODE_WORLD_READABLE));
			}

			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos); // PNG
			flag = true;
		} catch (IOException ioe) {
			Log.i("ioerror", ioe.getMessage());
		} finally {
			try {
				if (bos != null) {
					bitmap.recycle();
					bos.flush();
					bos.close();
				}
			} catch (IOException e) {
			}
		}
		return flag;
	}

	private static class MyHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	private static class MyTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

}