package com.mundane.androidtechniqueapply.view.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by mundane on 2017/2/24 18:59
 */

public class HorizontalViewPager extends ViewPager {

	private int mTouchSlop;

	public HorizontalViewPager(Context context) {
		this(context, null);
	}

	public HorizontalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	private float mDownX;
	private float mDownY;
	private float mLastX;
	private float mLastY;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean intercept = super.onInterceptTouchEvent(ev);
		float x = ev.getX();
		float y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mDownX = x;
				mDownY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				float dx = Math.abs(x - mDownX);
				float dy = Math.abs(y - mDownY);
				if (!intercept && dx > mTouchSlop && dx * 0.5f > dy) {
					//	拦截事件的条件:父控件原本是不拦截的 && x方向上的滑动超过了阈值 && x方向上的滑动远大于y方向上的滑动
					intercept = true;
				}
				break;
			default:
				break;

		}

		//	下面这种写法也可以
		/*switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				float dx = Math.abs(x - mLastX);
				float dy = Math.abs(y - mLastY);
				if (dx > dy) {
					intercept = true;
				} else {
					intercept = false;
				}
				break;
			default:
				break;
		}
		mLastX = x;
		mLastY = y;*/
		return intercept;
	}
}
