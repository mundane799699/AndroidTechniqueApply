package com.mundane.androidtechniqueapply.ui.fragment;


import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.model.Point;
import com.mundane.androidtechniqueapply.model.ThrowPoint;
import com.mundane.androidtechniqueapply.utils.ScreenUtil;
import com.mundane.androidtechniqueapply.view.evaluator.PointEvaluator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ValueAnimationFragment extends Fragment {

	private final String TAG = "ValueAnimationFragment";
	@BindView(R.id.btn_vertical)
	Button mBtnVertical;
	@BindView(R.id.btn_throw)
	Button mBtnThrow;
	@BindView(R.id.iv_ball)
	ImageView mIvBall;
	@BindView(R.id.fl)
	FrameLayout mFl;
	private int mScreenHeight;
	private int mScreenWidth;

	public ValueAnimationFragment() {
		// Required empty public constructor
	}

	public static Fragment newInstance() {
		ValueAnimationFragment fragment = new ValueAnimationFragment();
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		Log.d(TAG, ScreenUtil.getScreenHeight(getContext()) + "");
		mScreenHeight = ScreenUtil.getScreenHeight(getContext());
		mScreenWidth = ScreenUtil.getScreenWidth(getContext());
		View view = inflater.inflate(R.layout.fragment_value_animation, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@OnClick({R.id.btn_vertical, R.id.btn_throw})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_vertical:
				verticalRun();
				break;
			case R.id.btn_throw:
//				throwRun();
				throwRun2();
				break;
		}
	}

	private void throwRun2() {
		final ValueAnimator anim = ValueAnimator.ofFloat(0, mScreenWidth - mIvBall.getWidth());
		anim.setTarget(mIvBall);
		anim.setDuration(600);
		final ThrowPoint point = new ThrowPoint(0);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float x = (float) animation.getAnimatedValue();
				point.setX(x);
				mIvBall.setTranslationX(point.getX());
				mIvBall.setTranslationY(point.getY());
			}
		});
		anim.setInterpolator(new LinearInterpolator());
		anim.start();
	}

	private void throwRun() {
		Point startPoint = new Point(0, 0);
		Point endPoint = new Point(mScreenWidth - mIvBall.getWidth(), mScreenHeight - mIvBall.getHeight());
		ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
		anim.setDuration(1500);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Point point = (Point) animation.getAnimatedValue();
				mIvBall.setX(point.getX());
				mIvBall.setY(point.getY());
				//	tranxlationX改变, left不变, 所以getX()跟着改变
				Log.d(TAG, "mIvBall.getX() = " + mIvBall.getX());
				Log.d(TAG, "mIvBall.getLeft() = " + mIvBall.getLeft());
				Log.d(TAG, "mIvBall.getTranslationX() = " + mIvBall.getTranslationX());
			}
		});
		anim.start();
	}

	private void verticalRun() {
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mScreenHeight - mIvBall.getHeight());
		valueAnimator.setTarget(mIvBall);
		valueAnimator.setDuration(1500);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mIvBall.setTranslationY((Float) animation.getAnimatedValue());
			}
		});
		valueAnimator.start();
	}
}
