/**
 * BarfooError.java barfoo 接口请求异常类描述
 * 
 * @user comger
 * @mail comger@gmail.com 2012-3-26
 */
package com.minihelper.core;

import android.util.Log;

public class HttpRequstError extends Throwable {

	private static final long serialVersionUID = 1L;
	private String mFailingUrl;
	private Exception e = null;
	private Class cls = null;

	/**
	 * 简单异常信息提示
	 * @param message
	 */
	public HttpRequstError(String message) {
		super(message);
	}

	/**
	 * 请求服务失败，无法接连网络
	 * @param message
	 * @param e
	 * @param failingUrl
	 */
	public HttpRequstError(String message, Exception e, String failingUrl) {
		super(message);
		this.e = e;
		this.cls = e.getClass();
		mFailingUrl = failingUrl;
		Log.i("BarfooError", "when open url " + failingUrl + " error is:" + message);
	}

	/**
	 * 请求服务出错
	 * @param message
	 * @param failingUrl 接口地址
	 */
	public HttpRequstError(String message, String failingUrl) {
		super(message);
		mFailingUrl = failingUrl;
		Log.i("BarfooError", "when open url " + failingUrl + " error is:" + message);
	}

	public Exception getException() {
		return this.e;
	}

	public Class getExceptionClass() {
		return this.cls;
	}

	public String getFailingUrl() {
		return mFailingUrl;
	}
}
