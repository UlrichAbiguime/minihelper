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
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.minihelper.ClientApp;
import com.minihelper.R.id;
import com.minihelper.R.layout;
import com.minihelper.R.string;

public class UpdateManager {

	private Context mContext;
	Resources resources = null;

	// 返回的安装包url
	private String apkUrl = "";

	public Dialog noticeDialog;

	private Dialog downloadDialog;
	/* 下载包安装路径 */
	private static final String savePath = "/sdcard/UpdateMiniHelper/";

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
		apkUrl = Util.Hosts + url;
		mContext = context;
		resources = mContext.getResources();
	}

	// 外部接口让主Activity调用
	public void checkUpdateInfo() {
		try {
			URL url = new URL(apkUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			//showNoticeDialog();
		} catch (MalformedURLException e) {
			// TODO
		} catch (IOException e) {
			Toast.makeText(mContext, resources.getString(string.nohasdownload), Toast.LENGTH_LONG).show();
		}
	}

	@SuppressWarnings("unused")
	private void showNoticeDialog() {

		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(resources.getString(string.softupdate));
		builder.setMessage(resources.getString(string.softupdateyesorno));
		builder.setPositiveButton(resources.getString(string.softdownload), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton(resources.getString(string.downloadlater), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Editor editor = ClientApp.mPref.edit();
				editor.putString("updateApp", "1");//1
				editor.commit();

			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	private void showDownloadDialog() {

		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(resources.getString(string.softupdate));
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(id.progress);

		builder.setView(v);
		builder.setNegativeButton(resources.getString(string.downloadcancel), new OnClickListener() {
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
	 * @param url
	 */
	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
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