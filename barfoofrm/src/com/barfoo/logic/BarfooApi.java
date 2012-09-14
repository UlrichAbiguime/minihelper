/**
 *
 *@Class BarfooApi.java
 *@author zxy
 *@date 2012-2012-9-13-下午5:21:30
 *@Description 说明
 */
package com.barfoo.logic;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.barfoo.app.BarfooError;
import com.barfoo.core.CustomHttpClient;

public class BarfooApi {

	public static JSONObject getAppUpdate() throws BarfooError, JSONException {
		JSONObject jsonobj = CustomHttpClient.httpGet(ApiConfig.AppUpdate, null);
		Log.i("app_msg", "" + jsonobj);
		if (!jsonobj.getBoolean("status")) {
			throw new BarfooError(jsonobj.getString("ErrorMessage"));
		}
		return jsonobj.getJSONObject("msg");
	}
}
