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
 * Email:namezheng@gmail.com
 */
package com.minihelper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 应用程序配置文件 Application Configuration file
 */
public class ClientApp extends Application {
	/**
	 * Global reference context
	 */
	public static Context mContext;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext=this.getApplicationContext();
		mLoginUser = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
		Editor editor=mLoginUser.edit();
		editor.putString("uid", "1");
		editor.commit();
		
	}
	//public static Resources res = mContext.getResources();
	/**
	 * 当前登录用户信息(username/password/nickname...)
	 */
	public static SharedPreferences mLoginUser;

}
