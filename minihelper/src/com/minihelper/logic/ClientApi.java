/**
 * Copyright 2012 minihelper Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 */
package com.minihelper.logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.minihelper.core.HttpRequstError;
import com.minihelper.core.Util;

public class ClientApi {

	/**
	 * 处理URL地址,并返回请求的数据(Deal with the URL address and returns the requested data)
	 * @param url
	 * @param params
	 * @return
	 * @throws HttpRequstError
	 * @throws JSONException
	 */
	public static JSONObject getjson(String url, Bundle params) throws HttpRequstError, JSONException {
		JSONObject jsonObject = Util.httpGet(url, params);

		if (!jsonObject.getBoolean("status")) {
			throw new HttpRequstError(jsonObject.getString("errormsg"));
		}
		return jsonObject;
	}

	/**
	 * add by zxy update at 2012-07-05 软件更新
	 * */
	public static JSONObject getAppUpdate() throws HttpRequstError, JSONException {
		JSONObject jsonobj = Util.httpGet(ApiConfig.AppUpdate, new Bundle());
		Log.i("app_msg", "" + jsonobj);
		if (!jsonobj.getBoolean("status")) {
			throw new HttpRequstError(jsonobj.getString("ErrorMessage"));
		}
		return jsonobj.getJSONObject("msg");
	}
	

	public static JSONArray getListView() throws HttpRequstError, JSONException {
		JSONObject jsonobj = Util.httpGet(ApiConfig.ListHost, new Bundle());
		Log.i("app_msg", "" + jsonobj);
		if (!jsonobj.getBoolean("status")) {
			throw new HttpRequstError(jsonobj.getString("ErrorMessage"));
		}
		return jsonobj.getJSONArray("data");
	}

	/**
	 * 
	 * add by zn update at 2012-8-6 @Description:(获取用户列表)get user list 
	 * 
	 * @return UserList(JSONArray)
	 * @throws HttpRequstError
	 * @throws JSONException
	 */
	public static JSONArray getTreeListData() throws HttpRequstError, JSONException {
		/**
		 * 从网络上获取数据
		 * 
		 */
		// JSONObject obj = getjson("/userlist", null);
		// return obj.getJSONArray("data");

		/**
		 * 模拟从网络上获取数据
		 */
		JSONArray treeArray;
		JSONArray userArray;
		treeArray = new JSONArray();
		userArray = new JSONArray();

		for (int i = 0; i < 20; i++) {// 子节点
			JSONObject object = new JSONObject();
			object.put("loginname", "aa" + i);
			object.put("id", i);
			userArray.put(i, object);
		}
		JSONObject treeobj = new JSONObject();// 根节点
		treeobj.put("name", "admin");
		treeobj.put("users", userArray);
		treeArray.put(0, treeobj);

		Log.i("jsonarray", treeArray.toString());
		return treeArray;
	}

}
