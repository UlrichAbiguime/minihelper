/**
 * Util.java
 * 
 * @user zn
 * @mail wusheng198910@126.com
 * 2012-8-2
 */
package com.minihelper.core;

import java.io.BufferedReader;
import java.io.File;
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

import android.os.Bundle;
import android.util.Log;

public class Util {

	/**
	 * Set link timeout time
	 */
	public static int HttpTimeOut = 5 * 1000;
	/**
	 * Set read timeout time
	 */
	public static int ReadTimeOut = 5 * 1000;

	/**
	 * 初始化路径,无则创建
	 * 
	 * @param filepath
	 */
	public static void initPath(String filepath) {
		File mypic_dirs = new File(filepath);
		if (!mypic_dirs.exists()) {
			mypic_dirs.mkdirs();
		}
	}

	/**
	 * 转化时间格式
	 * 
	 * @param timelnMillis
	 * @return
	 */
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
	 * 
	 * @return
	 */
	public static long getNowTime() {
		return Calendar.getInstance().getTime().getTime();
	}

	/**
	 * Splicing interface parameters
	 * 
	 * @param api
	 * @param params
	 * @return String (URL address)
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
					sBuffer.append(key + "="
							+ URLEncoder.encode(params.getString(key)) + "&");
				}
			}
		}
		return sBuffer.toString();
	}

	/**
	 * To the server to send the request, and returns the string JSON
	 * 
	 * @param url
	 * @return
	 * @throws HttpRequstError
	 * @throws JSONException
	 */
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
				throw new HttpRequstError(
						"Services exceptions or return format error!",
						urlstring);
			}

			urlstring = null;
			line = null;
			Log.w("Util-HttpGet", "done");
			return new JSONObject(document.toString());

		} catch (FileNotFoundException e) {
			document = null;
			throw new HttpRequstError(
					"Could not find this service or interruption of service！",
					urlstring);
		} catch (MalformedURLException e) {
			document = null;
			throw new HttpRequstError(
					"URL parse error or splicing interface error！", urlstring);
		} catch (IOException e) {
			document = null;
			throw new HttpRequstError(
					"Unable to connect to service, please check whether the service closed！",
					urlstring);
		} catch (JSONException e) {
			document = null;
			throw new HttpRequstError("Returns the JSON format error！",
					urlstring);
		}

	}

	/**
	 * Send request, request to return data
	 * 
	 * @param url
	 * @param params
	 * @return JSONObject
	 * @throws HttpRequstError
	 * @throws JSONException
	 */
	public static JSONObject httpGet(String url, Bundle params)
			throws HttpRequstError, JSONException {
		String urlstring = build_api(url, params);
		return httpGet(urlstring);

	}

}
