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
 * Email:namezheng@gmail.com
 */
package com.minihelper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;

import com.minihelper.core.BaseLocation;

/**
 * 应用程序配置文件 Application Configuration file
 */
public class ClientApp extends Application {
	/**
	 * Global reference context
	 */
	public static Context mContext;

	public static String API_HOST = "https://192.168.1.172:8089";

	public static Context skinContext;

	public final static String ENCRYPT_KEY = "UfddHJa2"; // 需要与服务设置前8位配置一至

	public final static Boolean isDebug = true; // isDebug 为false 时启用加密
	// 连接超时设置
	public static int HttpTimeOut = 5 * 1000;

	/**
	 * 当前登录用户信息(username/password/nickname...)
	 */
	public static SharedPreferences mLoginUser;

	// 应用共享信息
	public static SharedPreferences mPref;
	public static BaseLocation mBaseLocation;

	public static Resources resources;

	public static SharedPreferences mSkin;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		mContext = this.getApplicationContext();
		mBaseLocation = new BaseLocation(mContext);

		resources = this.getResources();
		mLoginUser = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
		mPref = this.getSharedPreferences("sharedoc", Context.MODE_PRIVATE);
		mSkin = this.getSharedPreferences("shareskin", Context.MODE_PRIVATE);

		Editor editor = mLoginUser.edit();
		editor.putString("uid", "1");
		editor.commit();

		Editor editorUpdate = mPref.edit();
		editorUpdate.putString("updateApp", "0");
		editorUpdate.commit();

	}

}
