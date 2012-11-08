package com.minihelper.adapter;

import com.minihelper.R;
import com.minihelper.SidePageActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * MenuList的适配器
 * @author zxy
 *
 */
public class MenuListAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater listInflater;
	private boolean isPressed[];
	private int imageId = R.drawable.star_icon;
	private int pressedId;
	private String[] mStrings;

	/* 一个menu item中包含一个imageView和一个TextView */
	public final class ListItemsView {
		public ImageView menuIcon;
		public TextView menuText;
	}

	public MenuListAdapter(Context context, int pressedId, String[] mStrings) {
		this.context = context;
		this.pressedId = pressedId;
		this.mStrings = mStrings;
		this.init();
	}

	@Override
	public int getCount() {
		return mStrings.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemsView listItemsView;
		if (convertView == null) {
			listItemsView = new ListItemsView();
			listInflater = LayoutInflater.from(context);
			convertView = this.listInflater.inflate(R.layout.menu_list_item, null);
			listItemsView.menuIcon = (ImageView) convertView.findViewById(R.id.menuIcon);
			listItemsView.menuText = (TextView) convertView.findViewById(R.id.menuText);
			convertView.setTag(listItemsView);
		} else {
			listItemsView = (ListItemsView) convertView.getTag();
		}

		listItemsView.menuIcon.setBackgroundResource(imageId);
		listItemsView.menuText.setText(mStrings[position]);

		if (this.isPressed[position] == true)
			convertView.setBackgroundResource(R.drawable.menu_item_bg_sel);
		else
			convertView.setBackgroundColor(Color.TRANSPARENT);
		final int po = position;
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				changeState(po);
				gotoActivity(po);
				notifyDataSetInvalidated();
			}
		});

		return convertView;
	}

	private void gotoActivity(int position) {
		Intent intent = new Intent();
		intent.putExtra("keyPosition", position);
		switch (position) {
		case 0:
			intent.setClass(context, SidePageActivity.class);
			context.startActivity(intent);
			break;
		case 1:
			intent.setClass(context, SidePageActivity.class);
			context.startActivity(intent);
			break;
		default:
			intent.setClass(context, SidePageActivity.class);
			context.startActivity(intent);
		}
	}

	private void changeState(int position) {
		for (int i = 0; i < mStrings.length; i++) {
			isPressed[i] = false;
		}
		isPressed[position] = true;
	}

	private void init() {
		isPressed = new boolean[mStrings.length];
		for (int i = 0; i < this.mStrings.length; i++) {
			isPressed[i] = false;
		}
		isPressed[this.pressedId] = true;
	}
}
