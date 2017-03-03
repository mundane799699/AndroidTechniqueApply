package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by mundane on 2017/2/27 19:58
 */

public class MyListView extends ListView {
	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private HorizontalScrollViewEx mHorizontalScrollViewEx;

	private int mLastX = 0;
	private int mLastY = 0;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				//	让他的父容器不允许拦截事件
				getParent().requestDisallowInterceptTouchEvent(true);
				//	因为在父布局的onInterceptTouchEvent()中ACTION_DOWN是false, 所以任何手指按下的事件都会走到这里
				//	getParent().requestDisallowInterceptTouchEvent(true)是后续的MOVE不会经过父布局的onInterceptTouchEvent()
				break;
			case MotionEvent.ACTION_MOVE:
				int deltaX = x - mLastX;
				int deltaY = y - mLastY;
				//	然后MOVE事件直接走这里

				// 如果x方向上的位移大于y方向上的位移, 应该使父布局移动, 所以应该使父布局可以拦截事件
				//	MOVE事件在父布局中都被拦截掉不会再传递到子View

				//如果x方向上的位移\小于y方向上的位移, 应该使ListView移动, 所以该怎么走就怎么走, 不需要处理
				if (Math.abs(deltaX) > Math.abs(deltaY)) {
					getParent().requestDisallowInterceptTouchEvent(false);
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
			default:
				break;
		}

		mLastX = x;
		mLastY = y;
		return super.dispatchTouchEvent(ev);
	}
}
