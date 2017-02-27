package com.mundane.androidtechniqueapply.view.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalScrollViewEx2 extends ViewGroup {

    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx2(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

	private final String TAG = getClass().getSimpleName();

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
		boolean intercepted = false;//	默认不拦截, 将事件传递给子View
        int x = (int) event.getX();
        int y = (int) event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d(TAG, "onInterceptTouchEvent: ACTION_DOWN");
				intercepted = false;
				//这里一定要是false
				//根据的是, 某个View一旦决定拦截事件，那么这个事件序列之后的事件都会由它来处理，并且不会再调用onInterceptTouchEvent

//				if (!mScroller.isFinished()) {//	如果外层"ViewPager"还没有停止滑动, 就立刻停止滑动
//					mScroller.abortAnimation();
////					intercepted = true;
//				}
				break;
			case MotionEvent.ACTION_MOVE:
				Log.d(TAG, "onInterceptTouchEvent: ACTION_MOVE");
				int deltaX = x - mLastXIntercept;
				int deltaY = y - mLastYIntercept;
				if (Math.abs(deltaX) > Math.abs(deltaY)) {//	如果x方向上的位移超过了y方向上的位移, 就拦截该事件,
					intercepted = true;                   //	否则说明y方向上的位移超过了x方向上的位移, 不拦截该事件, 将事件传递给子View, 让ListView能够滑动
					//一旦这里返回为true, 如果手指一直按着屏幕来回移动而不抬手, 就一直拦截并且不会再调用onInterceptTouchEvent方法
				} else {
					intercepted = false;
				}
				break;
			case MotionEvent.ACTION_UP:
				Log.d(TAG, "onInterceptTouchEvent: ACTION_UP");
				intercepted = false;
				break;
			default:
				break;

		}
		Log.d(TAG, "onInterceptTouchEvent: intercepted = " + intercepted);
		mLastX = x;
		mLastY = y;
		mLastXIntercept = x;
		mLastYIntercept = y;
		return intercepted;
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent action:" + event.getAction());
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            int deltaX = x - mLastX;
            int deltaY = y - mLastY;
            Log.d(TAG, "move, deltaX:" + deltaX + " deltaY:" + deltaY);
            scrollBy(-deltaX, 0);
            break;
        }
        case MotionEvent.ACTION_UP: {
            int scrollX = getScrollX();
            int scrollToChildIndex = scrollX / mChildWidth;
            Log.d(TAG, "current index:" + scrollToChildIndex);
            mVelocityTracker.computeCurrentVelocity(1000);
            float xVelocity = mVelocityTracker.getXVelocity();
            if (Math.abs(xVelocity) >= 50) {
                mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
            } else {
                mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
            }
            mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
            int dx = mChildIndex * mChildWidth - scrollX;
            smoothScrollBy(dx, 0);
            mVelocityTracker.clear();
            Log.d(TAG, "index:" + scrollToChildIndex + " dx:" + dx);
            break;
        }
        default:
            break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;
        final int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize, childView.getMeasuredHeight());
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(measuredWidth, heightSpaceSize);
        } else {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "width:" + getWidth());
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;

        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft, 0, childLeft + childWidth,
                        childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
