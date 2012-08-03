/**
 * BaseRequestListener.java
 * 
 * @author zn Aug 2, 2012 6:43:22 PM
 * @mail wusheng198910@126.com
 * @Description:Process of the realization of the request to send the request
 *                      before the request, the request and the exception
 *                      handling process
 */
package com.minihelper.core;

import org.json.JSONException;

import com.minihelper.core.AsyncRunner.RequestListener;


public class BaseRequestListener implements RequestListener {

	@Override
	public void onReading() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onRequesting() throws HttpRequstError, JSONException {
		// TODO Auto-generated method stub
	}

	@Override
	public void onBarfooError(HttpRequstError e) {
		//TODO
	}

	@Override
	public void onDone() {
		//TODO
	}

}