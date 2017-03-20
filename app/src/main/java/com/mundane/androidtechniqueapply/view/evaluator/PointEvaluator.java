package com.mundane.androidtechniqueapply.view.evaluator;

import android.animation.TypeEvaluator;

import com.mundane.androidtechniqueapply.model.Point;

/**
 * Created by fangyuan.zhou on 2017/3/20 10:35
 */

public class PointEvaluator implements TypeEvaluator {
	@Override
	public Object evaluate(float fraction, Object startValue, Object endValue) {
		Point startPoint = (Point) startValue;
		Point endPoint = (Point) endValue;
		//	fraction的范围是0到1
		float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
		float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
		Point point = new Point(x, y);
		return point;
	}
}
