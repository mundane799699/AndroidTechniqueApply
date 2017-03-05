package com.mundane.androidtechniqueapply.view.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mundane.androidtechniqueapply.utils.AnimatorUtil;

/**
 * Created by mundane on 2017/3/5 9:10
 */

public class ScaleDownShowBehavior extends FloatingActionButton.Behavior {

	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
		return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
	}

	public ScaleDownShowBehavior(Context context, AttributeSet attrs) {
		super();
	}

	/**
	 * 退出动画是否正在执行。
	 */
	private boolean isAnimatingOut = false;

	private ViewPropertyAnimatorListener mViewPropertyAnimatorListener = new ViewPropertyAnimatorListener() {
		@Override
		public void onAnimationStart(View view) {
			isAnimatingOut = true;
		}

		@Override
		public void onAnimationEnd(View view) {
			isAnimatingOut = false;
			view.setVisibility(View.GONE);
		}

		@Override
		public void onAnimationCancel(View view) {
			isAnimatingOut = false;
		}
	};

	@Override
	public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
		if ((dyConsumed>0||dyUnconsumed>0) && !isAnimatingOut && child.getVisibility()==View.VISIBLE) {//手指上滑, 隐藏FAB
			AnimatorUtil.scaleHide(child, mViewPropertyAnimatorListener);
			if (mOnStateChangeListener != null) {
				mOnStateChangeListener.onChanged(false);
			}
		} else if ((dyConsumed < 0 || dyUnconsumed < 0) && child.getVisibility() != View.VISIBLE) {//手指下滑, 显示FAB
			AnimatorUtil.scaleShow(child, null);
			if (mOnStateChangeListener != null) {
				mOnStateChangeListener.onChanged(true);
			}
		}
	}

	//	<V extends View>并不是返回值类型, 返回值类型是ScaleDownShowBehavior, <V extends View>是对入参的类型V进行解释说明
	public static <V extends View> ScaleDownShowBehavior from(V view) {
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
			throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
		}
		CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
		if (!(behavior instanceof ScaleDownShowBehavior)) {
			throw new IllegalArgumentException("The view is not associated with ScaleDownShowBehavior");
		}
		return (ScaleDownShowBehavior) behavior;
	}

	public void setOnStateChangedListener(OnStateChangeListener onStateChangedListener) {
		mOnStateChangeListener = onStateChangedListener;
	}

	private OnStateChangeListener mOnStateChangeListener;

	public interface OnStateChangeListener {
		void onChanged(boolean isShow);
	}

}
