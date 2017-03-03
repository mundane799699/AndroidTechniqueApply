package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mundane.androidtechniqueapply.view.transforms.DefaultTransformer;

/**
 * Created by mundane on 2017/2/24 14:50
 */

public class VerticalViewPager extends ViewPager {
	public VerticalViewPager(Context context) {
		this(context, null);
	}

	public VerticalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setPageTransformer(false, new DefaultTransformer());
		setOverScrollMode(View.OVER_SCROLL_NEVER);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(swapTouchEvent(MotionEvent.obtain(ev)));
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return super.onTouchEvent(swapTouchEvent(MotionEvent.obtain(ev)));
	}

	private MotionEvent swapTouchEvent(MotionEvent event) {
		int width = getWidth();
		int height = getHeight();
		event.setLocation((event.getY() / height) * width, (event.getX() / width) * height);
		return event;
	}
}
