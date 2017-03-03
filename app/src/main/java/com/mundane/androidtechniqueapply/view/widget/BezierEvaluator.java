package com.mundane.androidtechniqueapply.view.widget;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by mundane on 2017/3/1 16:02
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {

	private PointF mPointF1;
	private PointF mPointF2;

	public BezierEvaluator(PointF pointF1, PointF pointF2) {
		mPointF1 = pointF1;
		mPointF2 = pointF2;
	}

	@Override
	public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
		float timeLeft = 1.0f - fraction;
		PointF pointF = new PointF();
		pointF.x = timeLeft * timeLeft * timeLeft * (startValue.x)
				+ 3 * timeLeft * timeLeft * fraction * (mPointF1.x)
				+ 3 * timeLeft * fraction * fraction * (mPointF2.x)
				+ fraction * fraction * fraction * (endValue.x);
		pointF.y = timeLeft * timeLeft * timeLeft * (startValue.y)
				+ 3 * timeLeft * timeLeft * fraction * (mPointF1.y)
				+ 3 * timeLeft * fraction * fraction * (mPointF2.y)
				+ fraction * fraction * fraction * (endValue.y);
		return pointF;
	}
}
