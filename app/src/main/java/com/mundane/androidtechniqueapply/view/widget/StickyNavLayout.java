package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.mundane.androidtechniqueapply.R;

/**
 * Created by mundane on 2017/3/3 13:31
 */

public class StickyNavLayout extends LinearLayout implements NestedScrollingParent{

	private ViewPager mViewPager;
	private OverScroller mScroller;
	private int mTouchSlop;
	private int mMaximumFlingVelocity;
	private int mMinimumFlingVelocity;

	public StickyNavLayout(Context context) {
		this(context, null);
	}

	public StickyNavLayout(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public StickyNavLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		setOrientation(VERTICAL);
		mScroller = new OverScroller(context);
//		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//		mMaximumFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
//		mMinimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
	}

	@Override
	public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
		return true;
	}

	@Override
	public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
		boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;//往上滑并且topView还没有完全隐藏, dy表示现在的getScrollY()减去之前的getScrollY()
		boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);//往下滑, 子View(父View中的内容)往下移动(正在显示头布局)
		//	getScrollY() >= 0表示头布局还没有完全显示出来, 内部View已经无法继续往下拉
		if (hiddenTop || showTop) {
			scrollBy(0, dy);//	上面任何一种情况, 父View在原来的基础上移动dy的距离
			consumed[1] = dy;//	comsumed[0]表示x轴方向, consumed[1]表示y轴方向, 父View将子View的事件消耗掉
		}
	}

	private int mTopViewHeight;

	@Override
	public boolean onNestedPreFling(View target, float velocityX, float velocityY) {//return true则表示拦截掉内部View的事件
		if (getScrollY() >= mTopViewHeight) {//如果上面的头完全显示出来了, 不拦截, 反之, 如果头还没有完全显示出来, 拦截掉, 父View可是往上或者往下拖动
			return false;
		}
		fling((int) velocityY);
		return true;
	}

	private void fling(int velocityY) {
		mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//	设置整个父View的高度
//		getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
		params.height = getMeasuredHeight() - mNav.getMeasuredHeight();
		setMeasuredDimension(getMeasuredWidth(), mTop.getMeasuredHeight() + mNav.getMeasuredHeight() + mViewPager.getMeasuredHeight());
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(0, mScroller.getCurrY());
			invalidate();
		}
	}

	@Override
	public void scrollTo(@Px int x, @Px int y) {
		//表示视图内容左上角比控件左上角在Y轴上的要高的距离
		if (y < 0) {
			y = 0;
		}
		if (y > mTopViewHeight) {
			y = mTopViewHeight;
		}
		if (y != getScrollY()) {//有了新的变化
			super.scrollTo(x, y);
		}

	}

	private View mTop;
	private View mNav;

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mTop = findViewById(R.id.top_view);
		mNav = findViewById(R.id.indicator);
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mTopViewHeight = mTop.getMeasuredHeight();
	}
}
