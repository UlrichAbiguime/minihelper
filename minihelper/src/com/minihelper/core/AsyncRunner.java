/**
 * AsyncRunner.java
 * @author zn 
 * Aug 3, 2012  9:22:42 AM
 * @Description：Exception interface program to provide start state, and run to completion and exception handling
 */
package com.minihelper.core;

import org.json.JSONException;

import android.os.Looper;

public class AsyncRunner {

	/**
	 * Request listener interface
	 *
	 */

	public interface RequestListener {

		/**
		 * Before the request
		 */
		public void onReading();

		/**
		 * Request is complete and return the request data
		 * @throws HttpRequstError
		 * @throws JSONException
		 */
		public void onRequesting() throws HttpRequstError, JSONException;

		/**
		 * Processing is complete
		 */
		public void onDone();

		/**
		 * Exception handling request
		 */
		public void onBarfooError(HttpRequstError e);

	}

	
	public static void HttpGet(final RequestListener listener) {
		listener.onReading(); // Prepare the request
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				try {
					listener.onRequesting();//Send the request and returns data
				} catch (HttpRequstError e) {
					listener.onBarfooError(e);
				} catch (JSONException e) {
					listener.onBarfooError(new HttpRequstError("Request exception:"
							+ e.getMessage()));
				}
				Looper.loop();
			}
		}.start();
		listener.onDone();
	}
}
