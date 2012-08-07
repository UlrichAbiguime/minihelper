/**
 * ViewPagerList.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-7-9
 */
package com.minihelper.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.minihelper.R;

/**
 * @author comger
 * 
 */
public abstract class ViewPagerList extends Activity {

	Context mContext;
	int mIndex = 0;
	private ViewPager mPager;// 页卡内容
	TabPagerAdapter tpadp;

	List<TabHolder> holders = new ArrayList<ViewPagerList.TabHolder>();
	List<TextView> tabs = new ArrayList<TextView>();
	List<View> views = new ArrayList<View>();

	private LocalActivityManager manager = null;

	public abstract ViewPager getViewPager();

	//public abstract LinearLayout getTabs();

	int pageWidth = 0;

	protected void _create(Bundle savedInstanceState, Context context) {
		mContext = context;
		mPager = getViewPager();
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		pageWidth = metric.widthPixels;
		tpadp = new TabPagerAdapter(views);
		mPager.setAdapter(tpadp);
		mPager.setOnPageChangeListener(new TabOnPageChangeListener());

	}

	// 添加新页面
	@SuppressWarnings("rawtypes")
	public void AddActivity(String title, Class t) {
		AddActivity(title, t, null);
	}


	/**
	 * 创建Tab 项
	 * @param title
	 * @return
	 */
	public TextView getTab(String title){
		TextView tab1 = new TextView(mContext);
		tab1.setText(title);
		//tab1.setBackgroundResource(getTabResId());
		tab1.setHeight(60);
		tab1.setTextSize(15);
		return tab1;
	}
	
	public int getTabResId(){
		return R.drawable.tab;
	}
	
	public int getSelectTabResId(){
		return R.drawable.tab_selected;
	}
	
	
	// 添加新页面
	@SuppressWarnings("rawtypes")
	public void AddActivity(String title, Class t, Bundle params) {
		holders.add(new TabHolder(title, t, params));
		//TextView tab1 = getTab(title);
		//tabs.add(tab1);
		//getTabs().addView(tab1);

		//tab1.setOnClickListener(new TabOnClickListener(tabs.size() - 1));
		Intent intent = new Intent(mContext, t);
		if (params != null) {
			for (String key : params.keySet()) {
				if (params.getString(key) != null) {
					intent.putExtra(key, params.getString(key));
				}
			}
		}

		View view = manager.startActivity(t.getName(), intent).getDecorView();
		views.add(view);
		tpadp.notifyDataSetChanged();

		// 重写tab 宽度
		int count = tabs.size();
		for (int i = 0; i < count; i++) {
			tabs.get(i).setWidth(170 / count);
		}
		chenageIndex(0);
	}

	// 头标点击监听
	public class TabOnClickListener implements View.OnClickListener {
		private int index = 0;

		public TabOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
			onTabClick(v, index);
		}
	};
	
	

	public void onTabClick(View v, int index) {

	}

	public void onTabPageSelected(int index) {
	}

	public void onSelectTab(int index) {
		mPager.setCurrentItem(index);
		chenageIndex(index);
	}

	private void chenageIndex(int index) {
		//for (int i = 0; i < tabs.size(); i++) {
			//tabs.get(i).setBackgroundResource(getTabResId());
			//tabs.get(i).setTextColor(Color.WHITE);
		//}
		//tabs.get(index).setBackgroundResource(getSelectTabResId());
		//tabs.get(index).setTextColor(Color.parseColor("#118889"));
		mIndex = index;
	}

	// 返回当前Index
	public int getCurrentIndex() {
		return mIndex;
	}

	/**
	 * ViewPager适配器
	 */
	public class TabPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public TabPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), arg1);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}

	/**
	 * 页卡切换监听
	 */
	public class TabOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				break;

			case 1:
				break;
			}
			chenageIndex(arg0);
			onTabPageSelected(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@SuppressWarnings("rawtypes")
	public class TabHolder {
		String title;
		Class type;
		Bundle params;

		public TabHolder(String title, Class t, Bundle params) {
			this.title = title;
			this.type = t;
			this.params = params;
		}

		public String getTitle() {
			return title;
		}

		public Class getType() {
			return type;
		}

		public Bundle getParams() {
			return params;
		}

	}

}
