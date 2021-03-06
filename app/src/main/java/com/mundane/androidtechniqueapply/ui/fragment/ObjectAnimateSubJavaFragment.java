package com.mundane.androidtechniqueapply.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mundane.androidtechniqueapply.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ObjectAnimateSubJavaFragment extends Fragment {


	@BindView(R.id.btn_alpha)
	Button mBtnAlpha;
	@BindView(R.id.btn_translate)
	Button mBtnTranslate;
	@BindView(R.id.btn_color)
	Button mBtnScale;
	@BindView(R.id.btn_rotate)
	Button mBtnRotate;
	@BindView(R.id.btn_set)
	Button mBtnSet;
	@BindView(R.id.iv)
	ImageView mIv;
	private final String TAG = getClass().getSimpleName();

	public ObjectAnimateSubJavaFragment() {
		// Required empty public constructor
	}

	public static ObjectAnimateSubJavaFragment newInstance() {
		ObjectAnimateSubJavaFragment fragment = new ObjectAnimateSubJavaFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_object_animate_sub_java, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@OnClick({R.id.btn_alpha, R.id.btn_translate, R.id.btn_color, R.id.btn_rotate, R.id.btn_set})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_alpha:
				alpha();
				break;
			case R.id.btn_translate:
				translate();
				break;
			case R.id.btn_color:
				scale();
				break;
			case R.id.btn_rotate:
				rotation();
				break;
			case R.id.btn_set:
//				set1();
				set2();
				break;
		}
	}

	private void set2() {
		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator alpha = ObjectAnimator.ofFloat(mIv, "alpha", 1f, 0f, 1f);
		ObjectAnimator rotation = ObjectAnimator.ofFloat(mIv, "rotation", 0f, 360f);
		animatorSet.setDuration(2000);
		animatorSet.playTogether(alpha, rotation);
		animatorSet.start();
//		animatorSet.play(alpha).after(rotation).before(alpha).with(rotation);
//		animatorSet.setStartDelay(1000);
//		alpha.setStartDelay(1000);
//		animatorSet.playSequentially(alpha, rotation);
	}

	private void set1() {
		PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0.5f);
		PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f);
		PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f);
		ObjectAnimator.ofPropertyValuesHolder(mIv, alpha, scaleX, scaleY).setDuration(1000).start();
	}

	private void rotation() {
		//	这里设置的是绝对坐标, 这两个一定要同时设置, 只设置一个是无效的
		mIv.setPivotY(0.5f * mIv.getHeight());
		mIv.setPivotX(0);
		ObjectAnimator.ofFloat(mIv, "rotation", 0f, 360f).setDuration(2000).start();

//		ObjectAnimator.ofFloat(mIv, "rotationY", 0f, 180f).setDuration(1000).start();//绕着图片中心的Y轴旋转

	}

	private void scale() {
//		ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 0.5f, 1.0f);
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIv, "scaleX", 1, 0.5f).setDuration(2000);
		objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float animatedValue = (float) animation.getAnimatedValue();
				float animatedFraction = animation.getAnimatedFraction();
				Log.d(TAG, "Fraction = " + animatedFraction);
				mIv.setScaleX(animatedValue);
				mIv.setScaleY(animatedValue);
			}
		});
		objectAnimator.start();
	}

	private void translate() {
		ObjectAnimator.ofFloat(mIv, "translationX", 0, 500, 0, 500, 0).setDuration(4000).start();

	}

	private void alpha() {
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIv, "alpha", 1, 0);
		objectAnimator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}
		});
		objectAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
			}
		});
		objectAnimator.setDuration(3000);
//		objectAnimator.setFloatValues(mIv.getAlpha(), 1);
		objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
		objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
//		objectAnimator.setStartDelay(1000);//延时开始
		objectAnimator.start();
	}
}
