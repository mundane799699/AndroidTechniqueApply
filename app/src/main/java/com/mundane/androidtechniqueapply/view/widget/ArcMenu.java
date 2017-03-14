package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.mundane.androidtechniqueapply.R;

/**
 * Created by fangyuan.zhou on 2017/3/14 9:46
 */

public class ArcMenu extends ViewGroup {

	private int mRadius;

	public ArcMenu(Context context) {
		this(context, null);
	}

	public ArcMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private static final int POS_LEFT_TOP = 0;
	private static final int POS_LEFT_BOTTOM = 1;
	private static final int POS_RIGHT_TOP = 2;
	private static final int POS_RIGHT_BOTTOM = 3;

	/**
	 * 菜单的位置枚举类
	 */
	public enum Position {
		LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
	}

	public enum Status {
		OPEN, CLOSE
	}

	private Status mCurrentStatus = Status.CLOSE;


	private Position mPosition = Position.RIGHT_BOTTOM;

	private final int DEFAULT_RADIUS = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());


	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
//		获取自定义属性的两种写法
//		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArcMenu);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);
		int pos = a.getInt(R.styleable.ArcMenu_positon, POS_RIGHT_BOTTOM);
		switch (pos) {
			case POS_LEFT_TOP:
				mPosition = Position.LEFT_TOP;
				break;
			case POS_LEFT_BOTTOM:
				mPosition = Position.LEFT_BOTTOM;
				break;
			case POS_RIGHT_TOP:
				mPosition = Position.RIGHT_TOP;
				break;
			case POS_RIGHT_BOTTOM:
				mPosition = Position.RIGHT_BOTTOM;
				break;
		}
		mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius, DEFAULT_RADIUS);
		Log.d("TAG", "position = " + mPosition + " , radius =  " + mRadius);
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			layoutCenterButton();
			int childCount = getChildCount();
			for (int i = 0; i < childCount - 1; i++) {
				View child = getChildAt(i + 1);
				child.setVisibility(GONE);
				//	子按钮总共有childCount - 1个
				// 将90度平均分成90/(childCount - 2) 个
				//	Math.PI/2就是90度
				//	以左下为例, 子按钮位置顺序从上方往左下角排列
				int childLeft = (int) (mRadius * Math.sin(Math.PI / 2 / (childCount - 2) * i));
				int childTop = (int) (mRadius * Math.cos(Math.PI / 2 / (childCount - 2) * i));
				int childWidth = child.getMeasuredWidth();
				int childHeight = child.getMeasuredHeight();
				//	如果菜单位置在底部, 左下, 右下
				if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
					childTop = getMeasuredHeight() - childHeight - childTop;
				}
				//	右上, 右下
				if (mPosition == Position.RIGHT_TOP || mPosition == mPosition.RIGHT_BOTTOM) {
					childLeft = getMeasuredWidth() - childWidth - childLeft;
				}
				child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
			}
		}
	}


	/**
	 * 主菜单按钮
	 */
	private View mCenterButton;
	/**
	 * 定位主菜单按钮
	 */
	private void layoutCenterButton() {
		mCenterButton = getChildAt(0);
		mCenterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rotateCenterButton();
				toggleMenu();
			}
		});
		int left = 0;
		int top = 0;
		int width = mCenterButton.getMeasuredWidth();
		int height = mCenterButton.getMeasuredHeight();

		switch (mPosition) {
			case LEFT_TOP:
				left = 0;
				top = 0;
				break;
			case LEFT_BOTTOM:
				left = 0;
				top = getMeasuredHeight() - height;
				break;
			case RIGHT_TOP:
				left = getMeasuredWidth() - width;
				top = 0;
				break;
			case RIGHT_BOTTOM:
				left = getMeasuredWidth() - width;
				top = getMeasuredHeight() - height;
				break;
		}
		mCenterButton.layout(left, top, left + width, top + height);
	}

	//为menuItem添加平移动画和旋转动画

	private final int ANIMATION_DURATION = 1000;
	public void toggleMenu() {
		int childCount = getChildCount();
		for (int i = 0; i < childCount - 1; i++) {
			final View childView = getChildAt(i + 1);
			childView.setVisibility(VISIBLE);
			int childLeft = (int) (mRadius * Math.sin(Math.PI / 2 / (childCount - 2) * i));
			int childTop = (int) (mRadius * Math.cos(Math.PI / 2 / (childCount - 2) * i));

			int xflag = 1;
			int yflag = 1;
			if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
				xflag = -1;
			}

			if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
				yflag = -1;
			}

			AnimationSet animSet = new AnimationSet(true);
			Animation tranAnim = null;
			//	to open
			if (mCurrentStatus == Status.CLOSE) {
				tranAnim = new TranslateAnimation(xflag * childLeft, 0, yflag * childTop, 0);
				childView.setClickable(true);
				childView.setFocusable(true);
			} else {//	to close
				tranAnim = new TranslateAnimation(0, xflag * childLeft, 0, yflag * childTop);
				childView.setClickable(false);
				childView.setFocusable(false);
			}
			tranAnim.setFillAfter(true);
			tranAnim.setDuration(ANIMATION_DURATION);
			tranAnim.setStartOffset((i * 100) / childCount);
			tranAnim.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					if (mCurrentStatus == Status.CLOSE) {
						childView.setVisibility(GONE);
					}
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}
			});

			//旋转动画
			RotateAnimation rotateAnim = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim.setDuration(ANIMATION_DURATION);
			rotateAnim.setFillAfter(true);

			animSet.addAnimation(tranAnim);
			animSet.addAnimation(rotateAnim);
			childView.startAnimation(animSet);

			final int pos = i + 1;
			childView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mOnMenuItemClickListener != null) {
						mOnMenuItemClickListener.onClick(childView, pos);
					}

					menuItemAnim(pos - 1);
					changeStatus();
				}
			});

		}

		//	切换菜单状态
		changeStatus();

	}

	private void changeStatus() {
		mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
	}

	public boolean isOpen() {
		return mCurrentStatus == Status.OPEN;
	}

	private void menuItemAnim(int pos) {
		for (int i = 0; i < getChildCount() - 1; i++) {
			View childView = getChildAt(i + 1);
			if (i == pos) {
				childView.startAnimation(scaleBigAnim(300));
			} else {
				childView.startAnimation(scaleSmallAnim(300));
			}

			childView.setClickable(false);
			childView.setFocusable(false);

		}
	}

	private Animation scaleSmallAnim(int duration) {
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.0f);//从完全不透明到完全透明
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(alphaAnimation);
		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		return animationSet;
	}

	private Animation scaleBigAnim(int duration) {
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.0f);//从完全不透明到全透明
		animationSet.addAnimation(scaleAnim);
		animationSet.addAnimation(alphaAnimation);
		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		return animationSet;
	}

	public interface OnMenuItemClickListener {
		void onClick(View view, int pos);
	}

	private OnMenuItemClickListener mOnMenuItemClickListener;

	public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
		mOnMenuItemClickListener = onMenuItemClickListener;
	}



	private void rotateCenterButton() {
		RotateAnimation anim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(300);
		anim.setFillAfter(true);
		mCenterButton.startAnimation(anim);
	}
}
