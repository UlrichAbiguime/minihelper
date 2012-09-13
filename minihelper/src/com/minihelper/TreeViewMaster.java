/*
 * TreeViewDemo.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-8-9
 */
package com.minihelper;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.minihelper.ui.TreeViewListAdp;

/**
 * @author comger
 * 
 */
public class TreeViewMaster extends Activity {

	ExpandableListView elv;
	TreeViewListAdp adp;

	JSONArray array;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.treeviewlist);

		elv = (ExpandableListView) findViewById(R.id.elv);
		adp = new TreeViewListAdp(getUserGroupData(), this, elv);
		elv.setAdapter(adp);
		elv.expandGroup(0);

	}

	private JSONArray getUserGroupData() {
		JSONArray grouplist = new JSONArray();
		try {
			for (int i = 0; i < 10; i++) {
				JSONObject group = new JSONObject();
				group.put("name", "Group" + i);
				group.put("id", "gid" + i);
				JSONArray users = new JSONArray();
				for (int n = 0; n < 10; n++) {
					JSONObject user = new JSONObject();
					user.put("name", "user" + i + n);
					user.put("id", "uid" + i + n);
					users.put(user);
				}
				group.put("users", users);
				grouplist.put(group);
			}
		} catch (Exception e) {
			// TODO
		}
		return grouplist;
	}

}
