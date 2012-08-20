/**
 * TreeViewListAdp.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-8-9
 */
package com.minihelper.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.minihelper.R;

/**
 * @author comger
 * 
 */
public class TreeViewListAdp extends BaseExpandableListAdapter {

	ExpandableListView elv;
	JSONArray array = new JSONArray();
	LayoutInflater mGroupInflater;
	public Map<String, Boolean> valcheck;// 存储Checkbox的状态
	public Map<String, Boolean> groupcheck;

	public TreeViewListAdp(JSONArray treedata, Context context, ExpandableListView elv) {
		array = treedata;
		this.elv = elv;
		valcheck = new HashMap<String, Boolean>();
		groupcheck = new HashMap<String, Boolean>();
		mGroupInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		try {
			return array.getJSONObject(groupPosition).getJSONArray("users").getJSONObject(childPosition);
		} catch (JSONException e) {
			return null;
		}
	}

	public JSONArray getChildArray(int groupPosition) {
		try {
			return array.getJSONObject(groupPosition).getJSONArray("users");
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	ExpandableListHolder holder = null;

	@Override
	public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		ExpandableListHolder holder = null;
		if (view == null) {
			view = mGroupInflater.inflate(R.layout.treechilditem, null);
			holder = new ExpandableListHolder(view);
			view.setTag(holder);
		} else {
			holder = (ExpandableListHolder) view.getTag();
		}

		final JSONObject user = (JSONObject) getChild(groupPosition, childPosition);
		try {

			holder.getTitle().setText(user.getString("name"));

			boolean flag = false;
			if (valcheck.containsKey(user.getString("id"))) {
				flag = valcheck.get(user.getString("id"));
			}

			holder.getSelector().setChecked(flag);

			// 子项选择，更改选中数据
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					CheckBox cbtn = (CheckBox) arg0.findViewById(R.id.cb_select);
					cbtn.setChecked(!cbtn.isChecked());
					try {
						valcheck.put(user.getString("id"), cbtn.isChecked());
						if (!cbtn.isChecked()) {
							// 用户取消选择子项，父项也取消
							String groupkey = ((JSONObject) getGroup(groupPosition)).getString("id");
							if (groupcheck.containsKey(groupkey) && groupcheck.get(groupkey) == true) {

								groupcheck.put(groupkey, false);
								// notifyDataSetChanged();
								// modify at 2012-08-10 by comger
								// 不能直接notifyDataSetChanged(), 直接获取子项更新；而不刷新所有
								CheckBox gcb = (CheckBox) elv.getChildAt(groupPosition).findViewById(R.id.cb_select);
								gcb.setChecked(false);
							}
						}
					} catch (JSONException e) {
						// TODO
					}
				}
			});

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		try {
			return array.getJSONObject(groupPosition).getJSONArray("users").length();
		} catch (JSONException e) {
			return 1;
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		try {
			return array.getJSONObject(groupPosition);
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public int getGroupCount() {
		return array.length();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ExpandableListHolder holder = null;
		View view = convertView;

		if (view == null) {
			view = mGroupInflater.inflate(R.layout.treegroupitem, null);
			holder = new ExpandableListHolder(view);
			view.setTag(holder);
		} else {
			holder = (ExpandableListHolder) view.getTag();
		}

		final JSONObject group = (JSONObject) getGroup(groupPosition);
		try {
			holder.getTitle().setText(group.getString("name"));

			boolean flag = false;
			if (groupcheck.containsKey(group.getString("id"))) {
				flag = groupcheck.get(group.getString("id"));
			}

			holder.getSelector().setChecked(flag);

			// 子级全选，不触发项展开与关闭
			holder.getSelector().setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					try {
						CheckBox cbtn = (CheckBox) v;
						groupcheck.put(group.getString("id"), cbtn.isChecked());
						JSONArray users = getChildArray(groupPosition);
						for (int i = 0; i < users.length(); i++) {
							valcheck.put(users.getJSONObject(i).getString("id"), cbtn.isChecked());
						}

						notifyDataSetChanged();
					} catch (Exception e) {
						// TODO
					}

				}
			});

			final ImageView iv_sign = (ImageView) view.findViewById(R.id.iv_sign);
			if (isExpanded) {
				iv_sign.setImageResource(R.drawable.down_gray);
			} else {
				iv_sign.setImageResource(R.drawable.right_gray);

			}

			// 项点击，展开与关闭子级
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					for (int i = 0; i < getGroupCount(); i++) {
						if (i != groupPosition) {
							elv.collapseGroup(i);
						}
					}
					if (elv.isGroupExpanded(groupPosition)) {
						elv.collapseGroup(groupPosition);
						iv_sign.setImageResource(R.drawable.right_gray);

					} else {
						elv.expandGroup(groupPosition);
						iv_sign.setImageResource(R.drawable.down_gray);
					}

				}
			});

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	// 清空列表项
	public void clearSelectData() {
		valcheck = new HashMap<String, Boolean>();
		// this.notifyDataSetChanged();
	}

	/**
	 * 获取选中的值的拼接字符串
	 * 
	 * @return
	 */
	public String getCheckedValString() {
		StringBuilder sb = new StringBuilder();
		Set<String> ids = valcheck.keySet();
		for (String string : ids) {
			if (valcheck.get(string).booleanValue()) {
				sb.append(string + ",");
			}
		}

		String vals = sb.toString();
		if (vals != null && vals.lastIndexOf(',') == vals.length() - 1 && vals.lastIndexOf(',') > -1) {
			return vals.substring(0, vals.length() - 1);
		} else {
			return vals;
		}

	}

	class ExpandableListHolder {

		View base;
		TextView title;
		CheckBox selector;

		public ExpandableListHolder(View base) {
			this.base = base;
		}

		public TextView getTitle() {
			if (title == null) {
				title = (TextView) base.findViewById(R.id.textgroup);
			}
			return title;
		}

		public CheckBox getSelector() {
			if (selector == null) {
				selector = (CheckBox) base.findViewById(R.id.cb_select);
			}
			return selector;
		}

	}

}
