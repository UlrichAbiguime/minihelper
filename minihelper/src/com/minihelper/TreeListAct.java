/**
 * @author zn 
   Aug 6, 2012  12:48:38 PM
 *
 */

package com.minihelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.minihelper.core.AsyncRunner;
import com.minihelper.core.BaseRequestListener;
import com.minihelper.core.HttpRequstError;
import com.minihelper.ui.TreeAdapter;

public class TreeListAct extends Activity {

	private JSONArray array;
	private ListView lv;
	TreeAdapter adp;
	private JSONArray treeArray;
	private JSONArray userArray;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.arg1 == 1) {// 加载树列表
				lv.setAdapter(adp);
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.treemain);
		lv = (ListView) findViewById(R.id.lv_treelist);
		getDataList();
	}

	private void getDataList() {
		AsyncRunner.HttpGet(new BaseRequestListener() {

			@Override
			public void onRequesting() throws HttpRequstError, JSONException {
				super.onRequesting();
				Message msg = handler.obtainMessage();
				array = getTreeListData();
				adp = new TreeAdapter(TreeListAct.this, array);
				msg.arg1 = 1;
				handler.sendMessage(msg);
			}

			@Override
			public void HttpRequstError(HttpRequstError e) {
				super.HttpRequstError(e);
				Toast.makeText(TreeListAct.this, e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}

		});
	}

	//模拟从服务器获取数据
	private JSONArray getTreeListData() {
		
		treeArray = new JSONArray();
		userArray = new JSONArray();
		try {
			for (int i = 0; i < 20; i++) {// 子节点
				JSONObject object = new JSONObject();
				object.put("loginname", "aa" + i);
				object.put("id", i);
				userArray.put(i, object);
			}
			JSONObject treeobj = new JSONObject();//根节点
			treeobj.put("name", "admin");
			treeobj.put("users", userArray);
			treeArray.put(0, treeobj);
		} catch (JSONException e) {
			Toast.makeText(TreeListAct.this, e.getMessage(), Toast.LENGTH_SHORT)
					.show();

		}
		Log.i("jsonarray", treeArray.toString());
		return treeArray;
	}

}
