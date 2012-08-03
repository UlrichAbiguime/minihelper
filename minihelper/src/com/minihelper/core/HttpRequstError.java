/**
 * HttpRequstError.java
 * @user zn
 * @mail wusheng198910@126.com
 * 2012-8-2
 */
package com.minihelper.core;

import android.util.Log;

public class HttpRequstError extends Throwable {

	private static final long serialVersionUID = 1L;

	private String mErrorType;
	private String mFailingUrl;

	/**
	 * Exception message
	 * 
	 * @param message
	 */
	public HttpRequstError(String message) {
		super(message);
	}

	/**
	 * Analysis of JSON data failed
	 * 
	 * @param message
	 * @param cls (Analytical model)
	 */
	public HttpRequstError(String message, @SuppressWarnings("rawtypes") Class cls) {
		super(message);
		mErrorType = cls.toString();
		Log.i("BarfooError", "when parse json to " + mErrorType + " error is:" + message);
	}

	/**
	 * Request service error
	 * 
	 * @param message
	 * @param failingUrl (Interface address)
	 */
	public HttpRequstError(String message, String failingUrl) {
		super(message);
		mFailingUrl = failingUrl;
		Log.i("HttpRequstError", "when open url " + failingUrl + " error is:" + message);
	}

	public String getErrorType() {
		return mErrorType;
	}

	public String getFailingUrl() {
		return mFailingUrl;
	}
}
