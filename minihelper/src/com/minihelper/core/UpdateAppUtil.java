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
 * Email：namezheng@gmail.com
 */
package com.minihelper.core;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class UpdateAppUtil {
	String app_path = null;
	UpdateManager mUpdateManager;
	Timer timer = new Timer();
	Context mContext;
	private int times=3000;

	public UpdateAppUtil(Context context) {
		this.mContext = context;
		timer.schedule(task, times);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				update_app();
				break;
			case 2:
				if (app_path != null) {
					mUpdateManager = new UpdateManager(mContext, app_path);
					mUpdateManager.checkUpdateInfo();
				}
			}
			super.handleMessage(msg);
		}
	};

	private void update_app() {
		AsyncRunner.HttpGet(new BaseRequestListener() {
			@Override
			public void onRequesting() throws HttpRequstError, JSONException {
				app_path = Util.getUpdatePath();
				Message msg = new Message();
				msg.what = 2;
				handler.sendMessage(msg);
			}
		});
	}

	TimerTask task = new TimerTask() {
		public void run() {
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	};

}
