package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by mundane on 2017/2/28 14:27
 */

public class MyScrollView extends ScrollView {
	public MyScrollView(Context context) {
		super(context);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private final String TAG = getClass().getSimpleName();

	private ListView mListView;
	private float mDownY;

	public void setListView(ListView listView) {
		mListView = listView;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean intercepted = false;
		float y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mDownY = y;
				intercepted = super.onInterceptTouchEvent(ev);
				break;
			case MotionEvent.ACTION_MOVE:
				if (isListViewReachTopEdge(mListView) && y > mDownY) {// 手指往下滑, 并且ListView到达了顶端, 让ScrollView滑动
					intercepted = true;
					Log.d(TAG, "intercepted - move: " + intercepted);
					break;
				} else if (isListViewReachBottomEdge(mListView) && y < mDownY) {//	手指往上滑, 并且ListView到达了末端, 让ScrollView滑动
					intercepted = true;
					Log.d(TAG, "intercepted - move: " + intercepted);
					break;
				}
				intercepted = false;
				Log.d(TAG, "intercepted - move: " + intercepted);
				break;
			case MotionEvent.ACTION_UP:
				intercepted = false;
				Log.d(TAG, "intercepted - up: " + intercepted);
				break;
			default:
				Log.d(TAG, "intercepted - default: " + intercepted);
				break;
		}
//		Log.d(TAG, "intercepted: " + intercepted);
		return intercepted;
	}

	private boolean isListViewReachTopEdge(ListView listView) {
		boolean isReachTop = false;
		if (listView.getFirstVisiblePosition() == 0) {
			View topChildView = listView.getChildAt(0);
			if (topChildView.getTop() == 0) {
				return true;
			}
		}
		return isReachTop;
	}

	private boolean isListViewReachBottomEdge(ListView listView) {
		boolean isReachBottom = false;
		if (listView.getLastVisiblePosition() == listView.getCount() - 1) {
			View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
			Log.d(TAG, "bottomChildView = " + bottomChildView);
			Log.d(TAG, "listView = " + listView);
			if (bottomChildView.getBottom() <= listView.getHeight()) {
				return true;
			}
		}
		return isReachBottom;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.d(TAG, "getScrollY() = " + getScrollY());
		return super.onTouchEvent(ev);
	}

}
