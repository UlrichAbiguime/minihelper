/**
 * 软件更新
 * @author Zhengxy
 * */
package com.minihelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UpdateManager {

	private Context mContext;
	Resources resources = null;

	// 返回的安装包url
	private String apkUrl = "";

	public Dialog noticeDialog;

	private Dialog downloadDialog;
	/* 下载包安装路径 */
	private static final String savePath = "/sdcard/updatebarfoo/";

	private static final String saveFileName = savePath + "ppf.apk";

	/* 进度条与通知ui刷新的handler和msg常量 */
	private ProgressBar mProgress;

	private static final int DOWN_UPDATE = 1;

	private static final int DOWN_OVER = 2;

	private int progress;

	private Thread downLoadThread;

	private boolean interceptFlag = false;

	private HttpURLConnection conn;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context, String url) {
		apkUrl = ClientApp.API_HOST + url;
		Log.i("skinapk", apkUrl);
		this.mContext = context;
		resources = this.mContext.getResources();
	}

	// 外部接口让主Activity调用
	public void checkUpdateInfo(boolean flag) {
		try {
			URL url = new URL(apkUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			if (flag) {
				showNoticeDialog();
			} else {
				showDownloadDialog(false);
			}
		} catch (MalformedURLException e) {
			// TODO
		} catch (IOException e) {
			Toast.makeText(mContext, resources.getString(R.string.nohasdownload), Toast.LENGTH_LONG).show();
		}
	}

	private void showNoticeDialog() {

		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(resources.getString(R.string.softupdate));
		builder.setMessage(resources.getString(R.string.softupdateyesorno));
		builder.setPositiveButton(resources.getString(R.string.softdownload), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog(true);
			}
		});
		builder.setNegativeButton(resources.getString(R.string.downloadlater), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	private void showDownloadDialog(boolean flag) {

		AlertDialog.Builder builder = new Builder(mContext);
		if (flag) {

			builder.setTitle(resources.getString(R.string.softupdate));
		} else {
			builder.setTitle(resources.getString(R.string.downloadlater));
		}
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);

		builder.setView(v);
		builder.setNegativeButton(resources.getString(R.string.downloadcancel), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();
		downloadApk();
	}

	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成通知安装
						downloadDialog.dismiss();
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}

					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载.

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				Log.i("message", e.getMessage());
			} catch (IOException e) {
				// TODO
			}
		}
	};

	/**
	 * 下载apk
	 * 
	 * @param url
	 */
	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
	 * 
	 * @param url
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}