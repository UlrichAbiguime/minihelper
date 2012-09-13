/**
 *
 *@Class TabActivity.java
 *@author zxy
 *@date 2012-2012-8-10-下午6:07:13
 *@Description 说明
 */
package com.minihelper.ui;

import com.minihelper.R;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public abstract class ViewTabAct extends TabActivity {

	private TabHost tabHost;
	Context mContext;

	protected void _create(Bundle savedInstanceState, Context context) {
		mContext = context;
	}

	public void AddActivity(String title, Class t) {
		AddActivity(title, t, null);
	}

	@SuppressWarnings("rawtypes")
	public void AddActivity(String title, Class t, Bundle params) {
		tabHost = getTabHost();
		tabHost.setPadding(tabHost.getPaddingLeft(), tabHost.getPaddingTop(), tabHost.getPaddingRight(), tabHost.getPaddingBottom() - 5);
		View view = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);
		TextView textView = (TextView) view.findViewById(R.id.tab_label);
		textView.setText(title);
		Intent intent = new Intent(this, t);
		if (params != null) {
			for (String key : params.keySet()) {
				if (params.getString(key) != null) {
					intent.putExtra(key, params.getString(key));
				}
			}
		}
		tabHost.addTab(tabHost.newTabSpec(t.getName()).setIndicator(view).setContent(intent));
	}

}