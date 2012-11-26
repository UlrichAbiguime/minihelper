package com.minihelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.minihelper.adapter.MenuListAdapter;
import com.minihelper.callback.SizeCallBackForMenu;
import com.minihelper.ui.MenuHorizontalScrollView;

public class SidePageActivity extends Activity implements OnClickListener {

	private MenuHorizontalScrollView scrollView;
	private ListView menuList;
	private View view;
	private Button menuBtn;
	private MenuListAdapter menuListAdapter;
	private String[] mStrings = { "one", "two", "Three" };

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		setContentView(inflater.inflate(R.layout.menu_scroll_view, null));

		Intent intent = getIntent();
		int keyValue = intent.getIntExtra("keyPosition", 0);

		scrollView = (MenuHorizontalScrollView) findViewById(R.id.scrollView);

		menuListAdapter = new MenuListAdapter(this, keyValue, mStrings);
		menuList = (ListView) findViewById(R.id.menuList);
		menuList.setAdapter(menuListAdapter);

		view = inflater.inflate(R.layout.sidepage, null);
		menuBtn = (Button) view.findViewById(R.id.menuBtn);
		menuBtn.setOnClickListener(this);

		View leftView = new View(this);
		leftView.setBackgroundColor(Color.TRANSPARENT);
		final View[] children = new View[] { leftView, view };
		scrollView.initViews(children, new SizeCallBackForMenu(menuBtn), menuList);
		scrollView.setMenuBtn(menuBtn);
	}

	@Override
	public void onClick(View v) {
		scrollView.clickMenuBtn();
	}
}