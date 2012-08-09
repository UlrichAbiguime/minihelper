/**
 * ViewPageDemo.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-7-9
 */
package com.minihelper;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.minihelper.ui.ViewPagerList;

/**
 * @author comger
 * 
 */
public class ViewPageMaster extends ViewPagerList{
	boolean isType = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.viewpagerlist);
		_create(savedInstanceState, this);

		AddActivity("推荐", MainActivity.class);
		AddActivity("回复", TreeListAct.class);

	}

	@Override
	public ViewPager getViewPager() {
		return (ViewPager) findViewById(R.id.vPager);
	}

	/*@Override
	public LinearLayout getTabs() {
		return null;
	}
*/
}
