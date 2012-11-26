package com.minihelper.listview;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.minihelper.R;
import com.minihelper.core.HttpRequstError;
import com.minihelper.logic.ClientApi;

public class AsyncListview extends Activity {

	ListView list;
	LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asynclistview);

		try {
			jsonArray = ClientApi.getListView();
		} catch (JSONException e) {
		} catch (HttpRequstError e) {
		}
		list = (ListView) findViewById(R.id.listView1);
		adapter = new LazyAdapter(this, jsonArray);
		list.setAdapter(adapter);

		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(listener);
	}

	@Override
	public void onDestroy() {
		list.setAdapter(null);
		super.onDestroy();
	}

	public OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			adapter.imageLoader.clearCache();
			adapter.notifyDataSetChanged();
		}
	};
	private JSONArray jsonArray;
}