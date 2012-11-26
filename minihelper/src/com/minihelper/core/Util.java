/**
 * Util.java 主要提供时间格式、图片处理、网络请求等静态方法
 * 
 * @user comger
 * @mail comger@gmail.com 2012-3-26
 */
package com.minihelper.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.minihelper.ClientApp;
import com.minihelper.logic.ClientApi;

public class Util {

	// 初始化路径,无则创建
	public static void initPath(String filepath) {
		File mypic_dirs = new File(filepath);
		if (!mypic_dirs.exists()) {
			mypic_dirs.mkdirs();
		}
	}

	/*public String getRes(int resId) {
		return BarfooApp.resources.getString(resId);
	}*/

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
	 * 获取更新地址，如果没有更新地址为null
	 * 
	 * @return
	 */
	public static String getUpdatePath() {

		try {
			JSONObject appUpdate = ClientApi.getAppUpdate();
			int verSion = appUpdate.getInt("varcode");
			if (Util.getAppVersionInfo(ClientApp.mContext).versionCode < verSion) {
				return appUpdate.getString("app_path");
			} else {
				return null;
			}

		} catch (JSONException e) {
			return null;
		} catch (HttpRequstError e) {
			return null;
		}
	}

	/**
	 * 生成用户请求的host地址
	 * 
	 * @param apihost
	 * @return
	 */
	public static String buildApiHost(String apihost) {
		return String.format("http://%s:88/11011098service/", apihost);
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
	public static String build_api(String api, Bundle params) {
		StringBuffer sBuffer = new StringBuffer();
		if (!api.startsWith("http")) {
			sBuffer.append(ClientApp.API_HOST);
		}

		sBuffer.append(api);
		if (!api.endsWith("?")) {
			sBuffer.append("?");
		}
		
		if (ClientApp.isDebug) {
			if (params != null) {
				for (String key : params.keySet()) {
					if (params.getString(key) != null) {
						sBuffer.append(key + "=" + URLEncoder.encode(params.getString(key)) + "&");
					}
				}
			}

			/*if (!api.equals(ApiConfig.m_account_login)) {// 当接口不为登录的时候需要验证
				sBuffer.append("token=" + ClientApp.mLoginUser.getString("token", null) + "&");
				sBuffer.append("uid=" + ClientApp.mLoginUser.getString("uid", "4dd4ad4d194f990914000005") + "&");
			}*/
		} else {
			JSONObject post_data = new JSONObject();
			try {
				if (params != null) {
					for (String key : params.keySet()) {
						if (params.getString(key) != null) {
							post_data.put(key, params.getString(key));
						}
					}
				}

				/*if (!api.equals(ApiConfig.m_account_login)) { // 当接口不为登录的时候需要验证
					post_data.put("token", ClientApp.mLoginUser.getString("token", null));
					post_data.put("uid", ClientApp.mLoginUser.getString("uid", null));
				}*/
				sBuffer.append("post_data=" + URLEncoder.encode(Des.encryptDES(post_data.toString(), ClientApp.ENCRYPT_KEY)));
			} catch (Exception e) {
				Log.i("build+api", e.getMessage());
			}
		}
		return sBuffer.toString();
	}

	/**
	 * 通过GET请求服务获取数据
	 * 
	 * @param url
	 * @param params
	 * @return JSONObject
	 * @throws HttpRequstError
	 * @throws JSONException
	 */
	public static JSONObject httpGet(String url, Bundle params) throws HttpRequstError, JSONException {
		String urlstring = build_api(url, params);
		if (urlstring.startsWith("https")) {
			return httpsGet(urlstring, ClientApp.isDebug);
		} else {
			return httpGet(urlstring, ClientApp.isDebug);
		}

	}

	/**
	 * isdebug
	 * 
	 * @param url
	 * @param isDebug
	 *            为false 时启用加密
	 * @return
	 * @throws HttpRequstError
	 * @throws JSONException
	 */
	public static JSONObject httpGet(String url, Boolean isDebug) throws HttpRequstError, JSONException {
		String urlstring = url;
		StringBuilder document = new StringBuilder();

		try {
			URL _url = new URL(urlstring);
			Log.w("Util-HttpGet", urlstring);
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(ClientApp.HttpTimeOut);
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()), 5 * 1024);
			String line = null;
			while ((line = reader.readLine()) != null)
				document.append(line);

			reader.close();
			
			if (document.toString().equals("")) {
				throw new HttpRequstError("服务异常或返回格式有误", urlstring);
			}

			line = null;
			Log.w("Util-HttpGet", "done");
			if (!isDebug) {
				try {
					return new JSONObject(Des.decryptDES(document.toString(), ClientApp.ENCRYPT_KEY));
				} catch (Exception e) {
					throw new HttpRequstError("信息解密出错",e, urlstring);
				}
			} else {
				return new JSONObject(document.toString());
			}

		} catch (FileNotFoundException e) {
			throw new HttpRequstError("无法找到此服务或服务中断，问问是不是服务报错啦!!",e, urlstring);
		} catch (MalformedURLException e) {
			throw new HttpRequstError("URL解析出错,看看是不是接口拼错啦～～",e, urlstring);
		} catch (IOException e) {
			throw new HttpRequstError("无法连接服务,请刷新重试！",e, urlstring);
		} catch (JSONException e) {
			throw new HttpRequstError("返回格式有误",e, urlstring);
		} finally {
			document = null;
			urlstring = null;
		}

	}

	/**
	 * 请求https
	 * 
	 * @param url
	 * @param isDebug
	 * @return
	 * @throws HttpRequstError
	 * @throws JSONException
	 */
	public static JSONObject httpsGet(String url, Boolean isDebug) throws HttpRequstError, JSONException {
		
		String urlString=url;
		StringBuilder document = new StringBuilder();
		Log.w("Util-HttpsGet", urlString);
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { new MyTrustManager() }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
			HttpsURLConnection conn = (HttpsURLConnection) new URL(urlString).openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()), 5 * 1024);
			String line = null;
			while ((line = reader.readLine()) != null)
				document.append(line);
			
			reader.close();
			
			if (document.toString().equals("")) {
				throw new HttpRequstError("服务异常或返回格式有误", new Exception(), urlString);
			}
			
			line=null;
			Log.w("Util-HttpsGet", "done");
			if (!isDebug) {
				try {
					return new JSONObject(Des.decryptDES(document.toString(), ClientApp.ENCRYPT_KEY));
				} catch (Exception e) {
					throw new HttpRequstError("信息解密出错",e, url);
				}
			} else {
				return new JSONObject(document.toString());
			}
		} catch (NoSuchAlgorithmException e) {
			throw new HttpRequstError("不知道东东～～",e, url);
		} catch (KeyManagementException e) {
			throw new HttpRequstError("证书过时～～",e, url);
		} catch (MalformedURLException e) {
			throw new HttpRequstError("URL解析出错,看看是不是接口拼错啦～～",e, url);
		} catch (IOException e) {
			throw new HttpRequstError("无法连接服务,请刷新重试！",e, url);
		}finally{
			document=null;
			urlString=null;
		}
	}

	/***
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws JSONException
	 * @throws HttpRequstError
	 */
	public static JSONObject httpPost(String url, Bundle params) throws JSONException, HttpRequstError {
		StringBuilder document = new StringBuilder();
		Log.i("msg", "" + params);
		try {
			URL _url = new URL(url);
			URLConnection conn = _url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
			printWriter.print(params);
			printWriter.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()), 5 * 1024);
			String line = null;
			while ((line = reader.readLine()) != null)
				document.append(line);

			reader.close();

			if (document.toString().equals("")) {
				throw new HttpRequstError("服务异常或返回格式有误", url);
			}

		} catch (MalformedURLException e) {
			document = null;
			throw new HttpRequstError("无法找到此服务或服务中断，问问是不是服务报错啦!!", url);
		} catch (IOException e) {
			document = null;
			throw new HttpRequstError("无法连接服务,服务关了？", url);
		}
		return new JSONObject(document.toString());
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
			return new java.security.cert.X509Certificate[] {};
		}
	}

	// 判断JSON字符产中是否有有这个字段以及该字段时候为空
	public static String isJSONStrNull(JSONObject obj, String str) throws JSONException {
		if (obj.has(str) && obj.getString(str).length() > 0 && !obj.getString(str).equals("null")) {
			return obj.getString(str);
		} else {
			return "null";
		}
	}

	// Html.fromHtml("<font color='#cc6600'>" +
	// BarfooApp.resources.getString(R.string.receiver) + " </font>" +
	// sb.substring(0, sb.length()-1))
	// 将数据HTML化
	public static String appendHtmlData(int color, String data) {
		String htmldataString = "<font color='" + color + "'>" + data + "</font>";
		return htmldataString;

	}

	public static String getStringData(int stringData) {
		return ClientApp.resources.getString(stringData);
	}

	public static void updateContext() {
		List<PackageInfo> packs;
		PackageInfo pi = null;
		String skinpackage = ClientApp.mSkin.getString("skinpackage", "");
		try {
			packs = ClientApp.mContext.getPackageManager().getInstalledPackages(0);
			for (PackageInfo p : packs) {
				if (p.packageName.equals(skinpackage)) {
					pi = p;
					break;
				}
			}

			ClientApp.skinContext = ClientApp.mContext.createPackageContext(pi.packageName, Context.CONTEXT_IGNORE_SECURITY);

		} catch (Exception e) {
			ClientApp.skinContext = ClientApp.mContext;
		}
	}

	/**
	 * 通过头像的URL确定来源(截取URL获取来源信息)
	 * 
	 * @param imageurlstr
	 *            (头像URL地址)
	 * @return
	 */
	public static String getSource(String imageurlstr) {

		String sourcestr = imageurlstr;
		if (sourcestr.length() > 0) {
			if (sourcestr.contains("twimg")) {
				return "twitter";
			} else if (sourcestr.contains("qlogo")) {
				sourcestr = "腾讯网";
			} else if (sourcestr.contains("sina")) {
				sourcestr = "新浪网";
			} else if (sourcestr.contains("cr.itc")||sourcestr.contains("sohu")) {// s5.cr.itc/s4.cr.itc
				sourcestr = "搜狐网";
			} else if (sourcestr.contains("ydstatic")) {// oimagea5/oimageb5.ydstatic
				sourcestr = "网易";
			}else{
				sourcestr = "未知";
			}
		} else {
			sourcestr = "未知";
		}

		// String sourcestr=null;
		// if(imageurlstr.length()>0){
		// if(imageurlstr.contains("/")){
		// sourcestr=imageurlstr.substring(imageurlstr.indexOf("/")+2,imageurlstr.length());
		// if(sourcestr.contains("/")){
		// sourcestr=sourcestr.substring(0, sourcestr.indexOf("/")-1);
		// if(sourcestr.contains("twimg")){
		// return "twitter";
		// }else if(sourcestr.contains("qlogo")){
		// sourcestr="腾讯网";
		// }else if(sourcestr.contains("sina")){
		// sourcestr="新浪网";
		// }else if(sourcestr.contains("cr.itc")){//s5.cr.itc/s4.cr.itc
		// sourcestr="搜狐网";
		// }else if(sourcestr.contains("ydstatic")){//oimagea5/oimageb5.ydstatic
		// sourcestr="网易";
		// }
		// }
		// }
		// }else {
		// sourcestr="未知";
		// }
		return sourcestr;
	}
	
	
}
