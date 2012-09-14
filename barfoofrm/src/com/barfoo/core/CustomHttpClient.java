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
import java.util.List;

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
import org.apache.http.cookie.Cookie;
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

import com.barfoo.app.BarfooApp;
import com.barfoo.app.BarfooError;

public class CustomHttpClient {
	private static final String TAG = "msg";
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
		return getContent(httpgeturl,true);
	}

	public static JSONObject httpPost(String url, Bundle params) throws JSONException, BarfooError {
		return post(url, params, true);
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
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			Log.w("Util-HttpGet", url);
			httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
			HttpResponse response = httpClient.execute(httpGet);
			/**
			List<Cookie> cookies = httpClient.getCookieStore().getCookies();
			if (cookies.isEmpty()) {
	            Log.i(TAG, "NONE");
	         } else {
	             for (int i = 0; i < cookies.size(); i++) {             
	               Log.i(TAG,"- domain " + cookies.get(i).getDomain());
	               Log.i(TAG,"- path " + cookies.get(i).getPath());
	               Log.i(TAG,"- value " + cookies.get(i).getValue());
	               Log.i(TAG,"- name " + cookies.get(i).getName());
	               Log.i(TAG,"- port " + cookies.get(i).getPorts());
	               Log.i(TAG,"- comment " + cookies.get(i).getComment());
	               Log.i(TAG,"- commenturl" + cookies.get(i).getCommentURL());
	             }
	         }
			**/
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
	/**
	public static String build_api(String api, Bundle params) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(BarfooApp.Host);
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
			sBuffer.append("uid=" + BarfooApp.mLoginUser.getString("uid", "4ff565c6b322d01f1c000008") + "&");
		}

		return sBuffer.toString();
	}
	**/
	public static String build_api(String api, Bundle params) throws JSONException {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(BarfooApp.Host);
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
		Log.i("sBuffer", sBuffer.toString());
		return sBuffer.toString();

	}

}
