/**
 *
 *@Class ClientApi.java
 *@author zxy
 *@date 2012-2012-8-6-下午2:48:40
 *@Description 说明
 */
package com.minihelper.logic;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.minihelper.core.HttpRequstError;
import com.minihelper.core.Util;

public class ClientApi {
	
	
	/**
	 * add by zxy update at 2012-07-05 软件更新
	 * */
	public static JSONObject getAppUpdate() throws HttpRequstError, JSONException {
		JSONObject jsonobj = Util.httpGet(ApiConfig.AppUpdate, null);
		Log.i("app_msg", "" + jsonobj);
		if (!jsonobj.getBoolean("status")) {
			throw new HttpRequstError(jsonobj.getString("ErrorMessage"));
		}
		return jsonobj.getJSONObject("msg");
	}

}
