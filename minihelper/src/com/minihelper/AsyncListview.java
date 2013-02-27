package com.minihelper;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.minihelper.R;
import com.minihelper.core.HttpRequstError;
import com.minihelper.core.LazyAdapter;
import com.minihelper.logic.ClientApi;

public class AsyncListview extends Activity implements OnClickListener {

	ListView list;
	LazyAdapter adapter;
	private View loadMoreView;
	private Button loadMoreButton;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asynclistview);
		loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);  
        loadMoreButton = (Button) loadMoreView.findViewById(R.id.loadMoreButton);
        loadMoreButton.setOnClickListener(this);
		list = (ListView) findViewById(R.id.listView1);
		list.addFooterView(loadMoreView);
		new TastTask().execute(null);

		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(listener);
	}

	class TastTask extends AsyncTask<JSONArray, JSONArray, JSONArray> {
		JSONArray jsonArray;

		@Override
		protected void onPreExecute() {
			Toast.makeText(AsyncListview.this, "开始执行！", Toast.LENGTH_LONG).show();
		}

		protected JSONArray doInBackground(JSONArray... params) {
			try {
				jsonArray = ClientApi.getListView("1","15");
			} catch (JSONException e) {
				Toast.makeText(AsyncListview.this, e.getMessage(), Toast.LENGTH_LONG).show();
			} catch (HttpRequstError e) {
				Toast.makeText(AsyncListview.this, "网络请求失败！", Toast.LENGTH_LONG).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			adapter = new LazyAdapter(AsyncListview.this, jsonArray);
			list.setAdapter(adapter);
			Toast.makeText(AsyncListview.this, "执行完毕！", Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onDestroy() {
		list.setAdapter(null);
		super.onDestroy();
	}

	public OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if (adapter != null) {
				adapter.imageLoader.clearCache();
				adapter.notifyDataSetChanged();
			}
		}
	};


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			
			break;

		default:
			break;
		}
	}
	


}