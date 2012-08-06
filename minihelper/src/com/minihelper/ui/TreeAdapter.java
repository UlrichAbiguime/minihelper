/**
 * TreeAdapter.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-6-4
 * @Description:Get users list of trees Adapter
 */
package com.minihelper.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.minihelper.ClientApp;
import com.minihelper.R;

public class TreeAdapter extends BaseAdapter {

	Context context;
	JSONArray jsonArray;
	LayoutInflater layoutinfla;
	/**
	 * The state of the storage Checkbox 存储Checkbox的状态
	 */
	Map<String, Boolean> valcheck;
	/**
	 * The bottom of the pop-up to the View 底部弹出来的View
	 */
	View buttonView;

	EditText et_forwardcontent;

	public TreeAdapter(Context context, JSONArray jsonArray) {
		this.context = context;
		this.jsonArray = new JSONArray();
		valcheck = new HashMap<String, Boolean>();

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				this.jsonArray.put(jsonArray.getJSONObject(i).put("childflag", false));
				JSONArray childData = jsonArray.getJSONObject(i).getJSONArray("users");
				for (int n = 0; n < childData.length(); n++) {
					JSONObject child = childData.getJSONObject(n);
					if (!child.getString("id").equals(ClientApp.mLoginUser.getString("uid", ""))) {
						this.jsonArray.put(child.put("childflag", true).put("name", child.getString("loginname")));
					}
				}
			}
		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		layoutinfla = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Empty the list of items 清空列表项
	 */
	public void clearSelectData() {
		valcheck = new HashMap<String, Boolean>();
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return jsonArray.length();
	}

	@Override
	public Object getItem(int arg0) {
		try {
			return jsonArray.getJSONObject(arg0);
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	/**
	 * Access to the selected value of the string concatenation (splicing in
	 * listView list of selected text to assemble into a string separated by
	 * commas) 获取选中的值的拼接字符串(拼接在listView列表中选中文本用逗号隔开拼接成字符串)
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
		Log.i("receiver", vals);
		if (vals != null && vals.lastIndexOf(',') == vals.length() - 1 && vals.lastIndexOf(',') > -1) {
			return vals.substring(0, vals.length() - 1);
		} else {
			return vals;
		}

	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertview == null) {
			convertview = layoutinfla.inflate(R.layout.treenode, null);
			holder = new ViewHolder();
			holder.cbtn = (CheckBox) convertview.findViewById(R.id.treenodeid);
			holder.tv = (TextView) convertview.findViewById(R.id.nodelable);
			holder.line = (DashedLine) convertview.findViewById(R.id.viewLine);
			convertview.setTag(holder);
		} else {
			holder = (ViewHolder) convertview.getTag();
		}

		final JSONObject json;
		try {

			json = jsonArray.getJSONObject(position);
			if (!json.getBoolean("childflag")) {
				holder.cbtn.setVisibility(View.GONE);
				holder.tv.setVisibility(View.VISIBLE);
				holder.tv.setText(json.getString("name"));
				holder.tv.setTextSize(18);

			} else {

				holder.cbtn.setVisibility(View.VISIBLE);
				holder.tv.setVisibility(View.GONE);
				holder.cbtn.setText(json.getString("name"));
				holder.cbtn.setTextSize(16);

				boolean flag = false;
				if (valcheck.containsKey(json.getString("id"))) {
					flag = valcheck.get(json.getString("id"));
				}

				holder.cbtn.setChecked(flag);

				convertview.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							CheckBox cbtn = (CheckBox) v.findViewById(R.id.treenodeid);
							cbtn.setChecked(!cbtn.isChecked());
							/**
							 * Save the state of the CheckBox selected
							 * 保存选中的CheckBox的状态
							 */
							valcheck.put(json.getString("id"), cbtn.isChecked());
							String vals = getCheckedValString();
							Log.i("vals", vals);

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return convertview;
	}

	public final class ViewHolder {
		public CheckBox cbtn;
		public TextView tv;
		public DashedLine line;

	}
}
