package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.mundane.androidtechniqueapply.utils.Utils;

/**
 * Created by mundane on 2017/3/1 14:09
 */

public class Bezier extends View {

	private int DP20;
	private int mWidth;
	private Paint mPaint;
	private PointF start;
	private PointF end;
	private PointF control;
	private float max;
	private float min;

	public Bezier(Context context) {
		super(context);
		init(context);
	}

	public Bezier(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public Bezier(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setStrokeWidth(2);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setAntiAlias(true);
		start = new PointF(mWidth / 4, 8 + DP20);
		end = new PointF(mWidth / 4 * 3, 8 + DP20);
		control = new PointF(mWidth / 2, (float) (mWidth / 4 * 1.732) + DP20);
		max = (float) (mWidth / 4 * 1.732 + DP20);
		min = (float) (-mWidth / 4 * 1.732 + DP20);
	}

	private boolean isRefresh = false;

	public void refreshFinish() {
		isRefresh = false;
	}

	public void setControlY(float y) {
		if (isRefresh) {
			return;
		}
		if (y + min < max) {
			control.y = y + min;
		} else if (y + min > max && y + min < 2 * (max - min)) {
			control.y = 2 * max - min - y;
		} else {
			control.y = min;
		}
		invalidate();
	}

	public void setControlDown() {
		isRefresh = true;
		control.y = max;
		invalidate();
	}

	public void setControlUp() {
		control.y = min;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//	绘制贝塞尔曲线
		Path path = new Path();
		path.moveTo(start.x, start.y);
		path.quadTo(control.x, control.y, end.x, end.y);
		canvas.drawPath(path, mPaint);
	}

	private void init(Context context) {
		DP20 = Utils.dp2px(context, 20);
	}
}
