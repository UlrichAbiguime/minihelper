/**
 *
 *@Class TabActivity.java
 *@author zxy
 *@date 2012-2012-8-10-下午6:07:13
 *@Description 说明
 */
package com.minihelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class TabActivity extends android.app.TabActivity {
	
	private TabHost tabHost;
	Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottomtab);
		
		tabHost = getTabHost();
		tabHost.setPadding(tabHost.getPaddingLeft(), tabHost.getPaddingTop(), tabHost.getPaddingRight(), tabHost.getPaddingBottom() - 5);

		View niTab = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);  
		View woTab = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);  
		View taTab = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);
		
		TextView tv_one = (TextView) niTab.findViewById(R.id.tab_label);
		TextView tv_two = (TextView) woTab.findViewById(R.id.tab_label);
		TextView tv_three = (TextView) taTab.findViewById(R.id.tab_label);
		
		tv_one.setText("tab1");
		tv_two.setText("tab2");
		tv_three.setText("tab3");
		
		Intent oneIntent = new Intent(this, MainActivity.class);
		Intent twoIntent = new Intent(this, TreeViewDemo.class);
		Intent threeIntent = new Intent(this, ViewPageMaster.class);

		TabHost.TabSpec oneTabSpec = tabHost.newTabSpec("tab1").setIndicator(niTab).setContent(oneIntent);
		TabHost.TabSpec twoTabSpec = tabHost.newTabSpec("tab2").setIndicator(woTab).setContent(twoIntent);
		TabHost.TabSpec threeTabSpec = tabHost.newTabSpec("tab3").setIndicator(taTab).setContent(threeIntent);
		
		tabHost.addTab(oneTabSpec);
		tabHost.addTab(twoTabSpec);
		tabHost.addTab(threeTabSpec);
	}
	
}
