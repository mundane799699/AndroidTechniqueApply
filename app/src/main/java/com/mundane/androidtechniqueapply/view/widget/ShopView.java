package com.mundane.androidtechniqueapply.view.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.utils.Utils;

import java.util.Random;

/**
 * Created by mundane on 2017/3/1 13:46
 */

public class ShopView extends RelativeLayout implements RefreshHeader{

	private Context mContext;
	private Drawable[] mDrawables;
	private View mIvTrans;
	private View mIvRongYi;
	private Bezier mBezierLine;
	private LayoutParams lp;
	private int mWidth;
	private int mHeight;

	public ShopView(Context context) {
		this(context, null);
	}

	public ShopView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ShopView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			addShop();
			mHandler.sendEmptyMessageDelayed(0, 250);
		}
	};

	public void start() {
		mHandler.sendEmptyMessage(0);
	}

	public void stop() {
		mHandler.removeCallbacksAndMessages(null);
	}

	private void init(Context context) {
		mContext = context;
		mDrawables = new Drawable[6];
		mDrawables[0] = getResources().getDrawable(R.drawable.refresh_item1);
		mDrawables[1] = getResources().getDrawable(R.drawable.refresh_item2);
		mDrawables[2] = getResources().getDrawable(R.drawable.refresh_item3);
		mDrawables[3] = getResources().getDrawable(R.drawable.refresh_item4);
		mDrawables[4] = getResources().getDrawable(R.drawable.refresh_item5);
		mDrawables[5] = getResources().getDrawable(R.drawable.refresh_item6);

		View view = View.inflate(getContext(), R.layout.widget_rongyi, this);
		mIvTrans = view.findViewById(R.id.iv_trans);
		mIvRongYi = view.findViewById(R.id.iv_rongyi);
		mBezierLine = (Bezier) view.findViewById(R.id.bezier_line);

		lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.addRule(CENTER_HORIZONTAL, TRUE);
		lp.addRule(ALIGN_PARENT_BOTTOM, TRUE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
		lp.setMargins(0, 0, 0, mIvRongYi.getHeight() + Utils.dp2px(getContext(), 20) + 8);
	}

	private Random mRandom = new Random();

	public void addShop() {
		ImageView imageView = new ImageView(mContext);
		int i = mRandom.nextInt(6);
		imageView.setImageDrawable(mDrawables[i]);
		addView(imageView, lp);
		Animator set = getAnimator(imageView);
		set.addListener(new AnimationEndListener(imageView));
		set.start();
		isLeft = !isLeft;
	}

	private class AnimationEndListener extends AnimatorListenerAdapter {
		private View target;

		public AnimationEndListener(View target) {
			this.target = target;
		}

		@Override
		public void onAnimationEnd(Animator animation) {
			removeView(target);
		}
	}


	private Interpolator acc = new AccelerateInterpolator();
	private Animator getAnimator(View target) {
		Animator set = getEnterAnimator(target);
		ValueAnimator bezierValueAnimator = getBezierValueAnimator(target);
		AnimatorSet finalSet = new AnimatorSet();
		finalSet.playSequentially(set);
		finalSet.playSequentially(set, bezierValueAnimator);
		finalSet.setInterpolator(acc);
		finalSet.setTarget(target);
		return finalSet;
	}

	/**
	 *
	 * 获取中间的那个点
	 */
	private ValueAnimator getBezierValueAnimator(View target) {
		int nextInt = mRandom.nextInt(50);
		int x = mWidth / 2;
		BezierEvaluator evaluator = new BezierEvaluator(getPointF(false), getPointF(true));
		ValueAnimator animator = ValueAnimator.ofObject(evaluator,
				new PointF((mWidth - mDrawables[1].getIntrinsicWidth()) / 2, mIvRongYi.getY() - mDrawables[0].getIntrinsicHeight() + 8),
				new PointF(isLeft ? x - 150 : x + 150 - mDrawables[0].getIntrinsicWidth() - nextInt, mHeight - nextInt - Utils.dp2px(getContext(), 20)));
		animator.addUpdateListener(new BezierListener(target));
		animator.setTarget(target);
		animator.setDuration(800);
		return animator;
	}

	private class BezierListener implements ValueAnimator.AnimatorUpdateListener {

		private View target;

		public BezierListener(View target) {
			this.target = target;
		}

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			PointF pointF = (PointF) animation.getAnimatedValue();
			target.setX(pointF.x);
			target.setY(pointF.y);
			target.setAlpha(1 - animation.getAnimatedFraction() / 2);
		}
	}

	private boolean isLeft;
	private PointF getPointF(boolean isSecond) {
		PointF pointF = new PointF();
		if (!isSecond) {
			pointF.x = mWidth / 2 + (isLeft ? -50 : 50);
			pointF.y = 0;
		} else {
			pointF.x = mWidth / 2 + (isLeft ? -100 : 100);
			pointF.y = 20;
		}
		return pointF;
	}



	private AnimatorSet getEnterAnimator(View target) {
		ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
		ObjectAnimator trans = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 10, 0);
		AnimatorSet enter = new AnimatorSet();
		enter.setDuration(5)
				.playTogether(alpha, trans);
		enter.setInterpolator(new LinearInterpolator());
		enter.setTarget(target);
		return enter;
	}

	@Override
	public void reset() {
		mBezierLine.refreshFinish();
	}

	@Override
	public void pull() {

	}

	@Override
	public void refreshing() {
		mBezierLine.setControlDown();
		ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.0f, 0.95f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(800);
		scaleAnimation.setRepeatCount(100);
		mIvRongYi.startAnimation(scaleAnimation);
		start();
	}

	@Override
	public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, RefreshLayout.State state) {
		mBezierLine.setControlY(currentPos);
		int dp20 = Utils.dp2px(getContext(), 20);
		if (currentPos > mHeight) {
			int translationY = (int) (currentPos - mHeight);

			if (translationY > dp20) {
				translationY = dp20;
			}
			mIvTrans.setTranslationY(dp20 - translationY);
		} else {
			mIvTrans.setTranslationY(dp20);
		}
	}

	@Override
	public void complete() {
		stop();
		Animation animation = mIvRongYi.getAnimation();
		if (animation != null) {
			animation.cancel();
		}
	}
}
