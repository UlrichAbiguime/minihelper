/**
 * CustomHttpClient.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-9-10
 */
package com.barfoo.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.barfoo.app.ApiConfig;
import com.barfoo.app.BarfooApp;
import com.barfoo.app.BarfooError;

/**
 * @author comger
 * 
 */
public class CustomHttpClient {
	private static HttpClient customHttpClient;

	public static synchronized HttpClient getHttpClient() {

		if (customHttpClient != null) {
			return customHttpClient;
		}

		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);

		HttpProtocolParams.setUseExpectContinue(params, true);
		ConnManagerParams.setTimeout(params, 1000);

		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 10000);

		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

		customHttpClient = new DefaultHttpClient(conMgr, params);
		return customHttpClient;

	}

	public static JSONObject httpGet(String url, Bundle params) throws JSONException, BarfooError {
		String httpgeturl = build_api(url, params);
		return getContent(httpgeturl);
	}

	public static JSONObject httpPost(String url, Bundle params) throws JSONException, BarfooError {
		return post(url, params);
	}

	/**
	 * HttpClient GET获取参数
	 * 
	 * @param url
	 * @return
	 * @throws JSONException
	 * @throws BarfooError
	 */
	public static JSONObject getContent(String url) throws JSONException, BarfooError {
		StringBuilder sb = new StringBuilder();
		HttpClient httpClient = CustomHttpClient.getHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			Log.w("Util-HttpGet", url);
			HttpResponse response = httpClient.execute(httpGet);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line);

			reader.close();
			if (sb.toString().equals("")) {
				throw new BarfooError("服务异常或返回格式有误！", url);
			}
			httpClient.getConnectionManager().shutdown();
			Log.w("Util-HttpGet", "done");
		} catch (ClientProtocolException e) {
			sb = null;
			throw new BarfooError("协议错误！", url);
		} catch (IOException e) {
			sb = null;
			throw new BarfooError("无法链接服务！", url);
		}
		return new JSONObject(sb.toString());
	}

	/**
	 * HttpClient POST传递
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws JSONException
	 * @throws BarfooError
	 */
	public static JSONObject post(String url, Bundle params) throws JSONException, BarfooError {
		StringBuilder sb = new StringBuilder();
		HttpClient httpClient = CustomHttpClient.getHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			Log.w("Util-HttpPost", url);
			StringEntity se = new StringEntity(params.toString(), HTTP.UTF_8);
			httpPost.setEntity(se);
			HttpResponse response = httpClient.execute(httpPost);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line);

			reader.close();
			if (sb.toString().equals("")) {
				throw new BarfooError("服务异常或返回格式有误！", url);
			}
			httpClient.getConnectionManager().shutdown();
			Log.w("Util-HttpPost", "done");
		} catch (MalformedURLException e) {
			sb = null;
			throw new BarfooError("URL解析错误！", url);
		} catch (ClientProtocolException e) {
			sb = null;
			throw new BarfooError("协议错误！", url);
		} catch (IOException e) {
			sb = null;
			throw new BarfooError("无法链接服务！", url);
		}
		return new JSONObject(sb.toString());
	}

	/**
	 * HttpClient GET获取参数携带Cookie
	 * 
	 * @param url
	 * @return
	 * @throws JSONException
	 * @throws BarfooError
	 */
	public static JSONObject getContent(String url,boolean isCookie) throws JSONException, BarfooError {
		StringBuilder sb = new StringBuilder();
		HttpClient httpClient = CustomHttpClient.getHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			Log.w("Util-HttpGet", url);
			httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
			HttpResponse response = httpClient.execute(httpGet);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line);

			reader.close();
			if (sb.toString().equals("")) {
				throw new BarfooError("服务异常或返回格式有误！", url);
			}
			httpClient.getConnectionManager().shutdown();
			Log.w("Util-HttpGet", "done");
		} catch (ClientProtocolException e) {
			sb = null;
			throw new BarfooError("协议错误！", url);
		} catch (IOException e) {
			sb = null;
			throw new BarfooError("无法链接服务！", url);
		}
		return new JSONObject(sb.toString());
	}
	
	
	/**
	 * HttpClient POST传递代Cookie
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws JSONException
	 * @throws BarfooError
	 */
	public static JSONObject post(String url, Bundle params, boolean isCookie) throws JSONException, BarfooError {
		StringBuilder sb = new StringBuilder();
		HttpClient httpClient = CustomHttpClient.getHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {

			StringEntity se = new StringEntity(params.toString(), HTTP.UTF_8);
			httpPost.setEntity(se);
			HttpResponse response = httpClient.execute(httpPost);
			httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line);

			reader.close();
			if (sb.toString().equals("")) {
				throw new BarfooError("服务异常或返回格式有误！", url);
			}
			httpClient.getConnectionManager().shutdown();
			Log.w("Util-HttpPost", "done");
		} catch (MalformedURLException e) {
			sb = null;
			throw new BarfooError("URL解析错误！", url);
		} catch (ClientProtocolException e) {
			sb = null;
			throw new BarfooError("协议错误！", url);
		} catch (IOException e) {
			sb = null;
			throw new BarfooError("无法链接服务！", url);
		}
		return new JSONObject(sb.toString());
	}

	/**
	 * 编译接口地址
	 * 
	 * @param api
	 * @param params
	 * @return
	 * @throws JSONException
	 */
	public static String build_api(String api, Bundle params) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(BarfooApp.API_HOST);
		sBuffer.append(api);
		if (!api.endsWith("?")) {
			sBuffer.append("?");
		}
		if (params != null) {
			for (String key : params.keySet()) {
				if (params.getString(key) != null) {
					sBuffer.append(key + "=" + URLEncoder.encode(params.getString(key)) + "&");
				}
			}
		}

		if (!api.equals(ApiConfig.m_account_login)) { // 当接口不为登录的时候需要验证
			sBuffer.append("token=" + BarfooApp.mLoginUser.getString("token", null) + "&");
			sBuffer.append("uid=" + BarfooApp.mLoginUser.getString("uid", "4dd4ad4d194f990914000005") + "&");
		}

		return sBuffer.toString();
	}
	
	
	
	/**
	 * 获取更新地址，如果没有更新地址为null
	 * 
	 * @return
	 */
	/***
	public static String getUpdatePath() {

		try {
			JSONObject appUpdate = BarfooApi.getAppUpdate();
			int verSion = appUpdate.getInt("varcode");
			if (Util.getAppVersionInfo(BarfooApp.mContext).versionCode < verSion) {
				return appUpdate.getString("app_path");
			} else {
				return null;
			}

		} catch (JSONException e) {
			return null;
		} catch (BarfooError e) {
			return null;
		}
	}
	**/

}
