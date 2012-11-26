package com.minihelper;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.minihelper.adapter.AsyncListAdapter;
import com.minihelper.core.HttpRequstError;
import com.minihelper.logic.ClientApi;

public class AsyncListview extends Activity {

	private JSONArray jsonArray;
	private AsyncListAdapter mAdapter;
	/** 
	* 设置布局显示为目标有多大就多大 
	*/
	private LayoutParams WClayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	/** 
	 * 设置布局显示目标最大化 
	 */
	private LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asynclistview);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.HORIZONTAL);

		Button button = new Button(this);
		button.setText("点击加载下五条...");
		button.setGravity(Gravity.CENTER_VERTICAL);

		layout.addView(button, FFlayoutParams);
		layout.setGravity(Gravity.CENTER);
		LinearLayout loadingLayout = new LinearLayout(this);
		loadingLayout.addView(layout, WClayoutParams);
		loadingLayout.setGravity(Gravity.CENTER);

		try {
			jsonArray = ClientApi.getListView();
		} catch (JSONException e) {
		} catch (HttpRequstError e) {
		}
		mAdapter = new AsyncListAdapter(AsyncListview.this, jsonArray);
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.addFooterView(loadingLayout);
		listView.setAdapter(mAdapter);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mAdapter.mCount+=10;
				mAdapter.notifyDataSetChanged();
			}
		});
		
	}

}