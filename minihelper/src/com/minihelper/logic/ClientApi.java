/**
 * Copyright 2012 minihelper Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
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
