/**
 * Util.java
 * 主要提供时间格式、图片处理、网络请求等静态方法
 * @user comger
 * @mail comger@gmail.com
 * 2012-3-26
 */
package com.minihelper.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;




public class Util {
	
	// 连接超时设置
	public static int HttpTimeOut = 5 * 1000;
	// 读取超时设置
	public static int ReadTimeOut = 5 * 1000;
	
	
	/**
	 * 返回当前程序版本信息
	 */
	public static PackageInfo getAppVersionInfo(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			return pi;
		} catch (Exception e) {
			return null;
		}
	}



	/**
	 * 转化时间格式
	 * */
	public static String getTimeString(long timelnMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timelnMillis);
		Date date = calendar.getTime();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String newTypeDate = f.format(date);
		return newTypeDate;
	}

	/**
	 * 获取所需当前时间
	 */
	public static long getNowTime() {
		return Calendar.getInstance().getTime().getTime();
	}

	/**
	 * 编译接口地址
	 * 
	 * @param api
	 * @param params
	 * @return
	 * @throws JSONException
	 */
	public static String build_api(String api, Bundle params)
			throws HttpRequstError, JSONException {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(api);
		if (!api.endsWith("?")) {
			sBuffer.append("?");
		}
		if (params != null) {
			for (String key : params.keySet()) {
				if (params.getString(key) != null) {
					sBuffer.append(key + "="+ URLEncoder.encode(params.getString(key)) + "&");
				}
			}
		}

		return sBuffer.toString();
	}

	/**
	 * 请求服务获取数据
	 * 
	 * @param url
	 * @param params
	 * @return JSONObject
	 * @throws HttpRequstError
	 * @throws JSONException
	 */
	public static JSONObject httpGet(String url, Bundle params)throws HttpRequstError, JSONException {
		String urlstring = build_api(url, params);
		return httpGet(urlstring);

	}

	public static JSONObject httpGet(String url) throws HttpRequstError,
			JSONException {
		String urlstring = url;
		StringBuilder document = new StringBuilder();

		try {
			URL _url = new URL(urlstring);
			Log.w("Util-HttpGet", urlstring);
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();

			conn.setConnectTimeout(HttpTimeOut);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()), 5 * 1024);

			String line = null;
			while ((line = reader.readLine()) != null)
				document.append(line);

			reader.close();

			if (document.toString().equals("")) {
				throw new HttpRequstError("服务异常或返回格式有误", urlstring);
			}

			urlstring = null;
			line = null;
			Log.w("Util-HttpGet", "done");
			return new JSONObject(document.toString());

		} catch (FileNotFoundException e) {
			document = null;
			throw new HttpRequstError("无法找到此服务或服务中断，问问是不是服务报错啦!!", urlstring);
		} catch (MalformedURLException e) {
			document = null;
			throw new HttpRequstError("URL解析出错,看看是不是接口拼错啦～～", urlstring);
		} catch (IOException e) {
			document = null;
			throw new HttpRequstError("无法连接服务,服务关了？", urlstring);
		} catch (JSONException e) {
			document = null;
			throw new HttpRequstError("返回格式有误", urlstring);
		}

	}


}
