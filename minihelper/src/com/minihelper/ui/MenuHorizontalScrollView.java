package com.minihelper.ui;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import com.minihelper.R;
import com.minihelper.callback.SizeCallBack;

public class MenuHorizontalScrollView extends HorizontalScrollView {

	/* 当前控件 */
	private MenuHorizontalScrollView me;

	/* 菜单 */
	private ListView menu;

	/* 菜单状态 */
	private boolean menuOut;

	/* 扩展宽度 */
	private final int ENLARGE_WIDTH = 80;

	/* 菜单的宽度 */
	private int menuWidth;

	/* 按钮 */
	private Button menuBtn;

	/* 当前滑动的位置 */
	private int current;

	private int scrollToViewPos;

	public MenuHorizontalScrollView(Context context) {
		super(context);
		init();
	}

	public MenuHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MenuHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		this.setHorizontalFadingEdgeEnabled(false);
		this.setVerticalFadingEdgeEnabled(false);
		this.me = this;
		this.me.setVisibility(View.INVISIBLE);
		this.menuOut = false;
	}

	public void initViews(View[] children, SizeCallBack sizeCallBack, ListView menu) {
		this.menu = menu;
		ViewGroup parent = (ViewGroup) getChildAt(0);

		for (int i = 0; i < children.length; i++) {
			children[i].setVisibility(View.INVISIBLE);
			parent.addView(children[i]);
		}
		OnGlobalLayoutListener onGlLayoutistener = new MenuOnGlobalLayoutListener(parent, children, sizeCallBack);
		getViewTreeObserver().addOnGlobalLayoutListener(onGlLayoutistener);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	/**
	 * 设置按钮
	 * @param btn 
	 */
	public void setMenuBtn(Button btn) {
		this.menuBtn = btn;
	}

	public void clickMenuBtn() {

		if (!this.menuOut) {
			this.menuWidth = 0;
		} else {
			this.menuWidth = this.menu.getMeasuredWidth() - this.menuBtn.getMeasuredWidth() - this.ENLARGE_WIDTH;
		}
		menuSlide();
	}

	/**
	 * 滑动出菜单
	 */
	private void menuSlide() {

		if (this.menuWidth == 0) {
			this.menuOut = true;
		} else {
			this.menuOut = false;
		}
		me.smoothScrollTo(this.menuWidth, 0);
		if (this.menuOut == true)
			this.menuBtn.setBackgroundResource(R.drawable.menu_fold);
		else
			this.menuBtn.setBackgroundResource(R.drawable.menu_unfold);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {

		super.onScrollChanged(l, t, oldl, oldt);
		if (l < (this.menu.getMeasuredWidth() - this.menuBtn.getMeasuredWidth() - this.ENLARGE_WIDTH) / 2) {
			this.menuWidth = 0;
		} else {
			this.menuWidth = this.menu.getWidth() - this.menuBtn.getMeasuredWidth() - this.ENLARGE_WIDTH;
		}
		this.current = l;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		int x = (int) ev.getRawX();
		if ((this.current == 0 && x < this.scrollToViewPos) || (this.current == this.scrollToViewPos * 2 && x > this.ENLARGE_WIDTH)) {
			return false;
		} else {
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				menuSlide();
				return false;
			}
		}
		return super.onTouchEvent(ev);
	}

	public class MenuOnGlobalLayoutListener implements OnGlobalLayoutListener {

		private ViewGroup parent;
		private View[] children;
		private SizeCallBack sizeCallBack;

		public MenuOnGlobalLayoutListener(ViewGroup parent, View[] children, SizeCallBack sizeCallBack) {

			this.parent = parent;
			this.children = children;
			this.sizeCallBack = sizeCallBack;
		}

		@Override
		public void onGlobalLayout() {
			me.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			this.sizeCallBack.onGlobalLayout();
			this.parent.removeViewsInLayout(0, children.length);
			int width = me.getMeasuredWidth();
			int height = me.getMeasuredHeight();

			int[] dims = new int[2];
			scrollToViewPos = 0;

			for (int i = 0; i < children.length; i++) {
				this.sizeCallBack.getViewSize(i, width, height, dims);
				children[i].setVisibility(View.VISIBLE);
				parent.addView(children[i], dims[0], dims[1]);
				if (i == 0) {
					scrollToViewPos += dims[0];
				}
			}

			new Handler().post(new Runnable() {
				@Override
				public void run() {
					me.scrollBy(scrollToViewPos, 0);

					/* 视图不是中间视图 */
					me.setVisibility(View.VISIBLE);
					menu.setVisibility(View.VISIBLE);
				}
			});
		}
	}

}
