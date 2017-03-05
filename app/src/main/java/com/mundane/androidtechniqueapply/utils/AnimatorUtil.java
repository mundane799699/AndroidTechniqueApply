package com.mundane.androidtechniqueapply.utils;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;

/**
 * Created by mundane on 2017/3/5 9:31
 */

public class AnimatorUtil {
	public static final LinearOutSlowInInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();

	//	显示View
	public static void scaleShow(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
		view.setVisibility(View.VISIBLE);
		ViewCompat.animate(view)
				.scaleX(1.0f)
				.scaleY(1.0f)
				.alpha(1.0f)
				.setDuration(300)
				.setListener(viewPropertyAnimatorListener)
				.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
				.start();
	}

	//	隐藏View
	public static void scaleHide(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
		ViewCompat.animate(view)
				.scaleY(0.0f)
				.scaleY(0.0f)
				.alpha(0.0f)
				.setDuration(300)
				.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
				.setListener(viewPropertyAnimatorListener)
				.start();
	}
}
