/**
 * MyListView.java
 * @user comger
 * @mail comger@gmail.com
 * 2012-5-18
 */
package com.minihelper.ui;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minihelper.R;

public class MiuiListView extends ListView implements OnScrollListener {

	View btn_more; // 加载更的按钮
	private int page_index = 1;
	private int page_size = 10;
	private int lastPosition = 0;
	private ProgressBar pb;
	BaseAdapter adapter;

	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;

	private LayoutInflater inflater;

	private LinearLayout headView;

	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;

	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean isRecored;

	public int headContentWidth;
	private int headContentHeight;

	private int startY;
	private int firstItemIndex;

	private int state;

	private boolean isBack;

	public OnRefreshListener refreshListener;

	//private final static String TAG = "ListView";

	public MiuiListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		inflater = LayoutInflater.from(context);

		if (this.btn_more == null) { // 如果没有指定分页按钮，将使用程序默认的　checkmore　Layout
			this.btn_more = (LinearLayout) inflater.inflate(R.layout.loadmore, null);
			this.pb = (ProgressBar) this.btn_more.findViewById(R.id.pbmore);
		}
		this.addFooterView(this.btn_more);
		initBtnMore(this.btn_more);

		headView = (LinearLayout) inflater.inflate(R.layout.pullheader, null);

		arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(50);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		addHeaderView(headView);
		setOnScrollListener(this);

		animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(250);
		reverseAnimation.setFillAfter(true);

	}

	// 加载更多
	public void initBtnMore(View view) {
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);
				bindData();
			}
		});
	}

	public void setAdapter(BaseAdapter adapter) {
		super.setAdapter(adapter);
		this.adapter = adapter;
		onRefreshComplete();

		if (page_index == 1) {
			setSelection(0);
		} else {
			setSelection(lastPosition);
		}

		if (adapter.getCount() % page_size == 0 && adapter.getCount() >= 10) { // 刚好为分页码的倍数，有下一页
			btn_more.setVisibility(View.VISIBLE);
			page_index++;
		} else {
			btn_more.setVisibility(View.GONE);
			removeFooterView(btn_more);
		}
		pb.setVisibility(View.GONE);
	}

	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2, int arg3) {
		firstItemIndex = firstVisiableItem;
		lastPosition = this.getLastVisiblePosition() - this.getChildCount() + 2;
	}

	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		// TODO
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (firstItemIndex == 0 && !isRecored) {
				startY = (int) event.getY();
				isRecored = true;
				//在down时候记录当前位置
			}
			break;

		case MotionEvent.ACTION_UP:

			if (state != REFRESHING) {
				if (state == DONE) {
					// 什么都不做
				}
				if (state == PULL_To_REFRESH) {
					state = DONE;
					changeHeaderViewByState();
					//由下拉刷新状态，到done状态
				}
				if (state == RELEASE_To_REFRESH) {
					state = REFRESHING;
					changeHeaderViewByState();
					onRefresh();

					//由松开刷新状态，到done状态
				}
			}

			isRecored = false;
			isBack = false;

			break;

		case MotionEvent.ACTION_MOVE:
			int tempY = (int) event.getY();
			if (!isRecored && firstItemIndex == 0) {
				//在move时候记录下位置
				isRecored = true;
				startY = tempY;
			}
			if (state != REFRESHING && isRecored) {
				// 可以松手去刷新了
				if (state == RELEASE_To_REFRESH) {
					// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
					if ((tempY - startY < headContentHeight) && (tempY - startY) > 0) {
						state = PULL_To_REFRESH;
						changeHeaderViewByState();
						//由松开刷新状态转变到下拉刷新状态
					}
					// 一下子推到顶了
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();
						//由松开刷新状态转变到done状态
					}
					// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
					else {
						// 不用进行特别的操作，只用更新paddingTop的值就行了
					}
				}
				// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
				if (state == PULL_To_REFRESH) {
					// 下拉到可以进入RELEASE_TO_REFRESH的状态
					if (tempY - startY >= headContentHeight) {
						state = RELEASE_To_REFRESH;
						isBack = true;
						changeHeaderViewByState();

						//由done或者下拉刷新状态转变到松开刷新
					}
					// 上推到顶了
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();
						//由DOne或者下拉刷新状态转变到done状态
					}
				}

				// done状态下
				if (state == DONE) {
					if (tempY - startY > 0) {
						state = PULL_To_REFRESH;
						changeHeaderViewByState();
					}
				}

				// 更新headView的size
				if (state == PULL_To_REFRESH) {
					headView.setPadding(0, -1 * headContentHeight + (tempY - startY), 0, 0);
					headView.invalidate();
				}

				// 更新headView的paddingTop
				if (state == RELEASE_To_REFRESH) {
					headView.setPadding(0, tempY - startY - headContentHeight, 0, 0);
					headView.invalidate();
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText("松开刷新");

			//当前状态，松开刷新
			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText("下拉刷新");
			} else {
				tipsTextview.setText("下拉刷新");
			}
			
			//当前状态，下拉刷新
			break;

		case REFRESHING:

			headView.setPadding(0, 0, 0, 0);
			headView.invalidate();

			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText("正在刷新...");
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			//当前状态,正在刷新...
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			headView.invalidate();

			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
			tipsTextview.setText("下拉刷新");
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			//当前状态，done
			break;
		}
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}

	public interface OnRefreshListener {
		public void onRefresh(int page_index, int page_size);
	}

	public void onRefreshComplete() {
		state = DONE;
		lastUpdatedTextView.setText("最近更新:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	// 设置起始的分页数及数据
	public void setStartPageindex(int page_index) {
		this.page_index = page_index;
	}

	// 刷新调用
	public void onRefresh() {
		page_index = 1;
		if (refreshListener != null) {
			refreshListener.onRefresh(page_index, page_size);
		}
	}

	// 分页调用
	private void bindData() {
		if (refreshListener != null) {
			refreshListener.onRefresh(page_index, page_size);
		}
	}

	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
}
