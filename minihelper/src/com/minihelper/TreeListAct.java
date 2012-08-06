/**
 * @author zn 
   Aug 6, 2012  12:48:38 PM
 *
 */

package com.minihelper;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.minihelper.core.AsyncRunner;
import com.minihelper.core.BaseRequestListener;
import com.minihelper.core.HttpRequstError;
import com.minihelper.core.ToastUtil;
import com.minihelper.logic.ClientApi;
import com.minihelper.ui.TreeAdapter;

public class TreeListAct extends Activity {

	private JSONArray array;
	private ListView lv;
	TreeAdapter adp;


	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.arg1 == 1) {// 加载树形结构的数据列表(Load a list of the tree structure of the data)
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

	/**
	 * 获取数据列表(Get a list of data)
	 */
	private void getDataList() {
		AsyncRunner.HttpGet(new BaseRequestListener() {

			@Override
			public void onRequesting() throws HttpRequstError, JSONException {
				super.onRequesting();
				Message msg = handler.obtainMessage();
				array = ClientApi.getTreeListData();
				adp = new TreeAdapter(TreeListAct.this, array);
				msg.arg1 = 1;
				handler.sendMessage(msg);
			}

			@Override
			public void HttpRequstError(HttpRequstError e) {
				super.HttpRequstError(e);
				ToastUtil.show(TreeListAct.this,  e.getMessage());
			}

		});
	}

	

}
