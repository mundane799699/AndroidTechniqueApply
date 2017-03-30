package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mundane.androidtechniqueapply.R;

/**
 * Created by fangyuan.zhou on 2017/3/30 15:19
 */

public class SwitchButton extends View {

	private int switchViewBgCloseColor;
	private int switchViewBgOpenColor;
	private int switchViewStrockWidth;
	private int switchViewStrockColor;
	private int switchViewBallColor;
	private Paint mBgPaint;
	private Paint mBallPaint;
	private Paint mStokePaint;
	private int mViewHeight;
	private int mViewWidth;
	private int mStrokeRadius;
	private int mSolidRadius;
	private RectF mBgStrokeRectF;
	private RectF mBgRectF;

	public SwitchButton(Context context) {
		this(context, null);
	}

	public SwitchButton(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SwitchView, defStyleAttr, R.style.def_switch_view);
		int indexCount = typedArray.getIndexCount();
		for (int i = 0; i < indexCount; i++) {
			int attr = typedArray.getIndex(i);
			switch (attr) {
				case R.styleable.SwitchView_switch_bg_close_color:
					switchViewBgCloseColor = typedArray.getColor(attr, Color.BLACK);
					break;
				case R.styleable.SwitchView_switch_bg_open_color:
					switchViewBgOpenColor = typedArray.getColor(attr, Color.BLACK);
					break;
				case R.styleable.SwitchView_switch_bg_strock_width:
					switchViewStrockWidth = typedArray.getDimensionPixelOffset(attr, 0);
					break;
				case R.styleable.SwitchView_switch_strock_color:
					switchViewStrockColor = typedArray.getColor(attr, Color.BLACK);
					break;
				case R.styleable.SwitchView_switch_ball_color:
					switchViewBallColor = typedArray.getColor(attr, Color.BLACK);
					break;
			}
		}
		typedArray.recycle();
		initData();
	}

	private void initData() {
		mBgPaint = createPaint(switchViewBgCloseColor, 0, Paint.Style.FILL, 0);
		mBallPaint = createPaint(switchViewBallColor, 0, Paint.Style.FILL, 0);
		mStokePaint = createPaint(switchViewStrockColor, 0, Paint.Style.FILL, 0);
	}

	private float mSwitchBallx;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mViewHeight = h;
		mViewWidth = w;

		mStrokeRadius = mViewHeight / 2;
		mSolidRadius = (mViewHeight - 2 * switchViewStrockWidth) / 2;

		mSwitchBallx = mSolidRadius;
		mBgStrokeRectF = new RectF(0, 0, mViewWidth, mViewHeight);
		mBgRectF = new RectF(switchViewStrockWidth, switchViewStrockWidth, mViewWidth - switchViewStrockWidth, mViewHeight - switchViewStrockWidth);

	}

	private static final int DEF_H = 60;
	private static final int DEF_W = 120;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		int measureWidth;
		int measureHeight;

		switch (widthMode) {
			case MeasureSpec.UNSPECIFIED:
			case MeasureSpec.AT_MOST://wrap_content
				measureWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_W, getResources().getDisplayMetrics());
				widthMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY);
				break;
			case MeasureSpec.EXACTLY:
				break;
		}

		switch (heightMode) {
			case MeasureSpec.UNSPECIFIED:
			case MeasureSpec.AT_MOST://wrap_content
				measureHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_H, getResources().getDisplayMetrics());
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY);
				break;
			case MeasureSpec.EXACTLY:
				break;

		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawSwitchBg(canvas);
		drawSwitchBallByFlag(canvas);
	}

	private void drawSwitchBallByFlag(Canvas canvas) {
		canvas.drawCircle(mSwitchBallx, mStrokeRadius, mSolidRadius, mBallPaint);
	}

	private void drawSwitchBg(Canvas canvas) {
		canvas.drawRoundRect(mBgStrokeRectF, mStrokeRadius, mStrokeRadius, mStokePaint);
		canvas.drawRoundRect(mBgRectF, mSolidRadius, mSolidRadius, mBgPaint);
	}

	private Paint createPaint(int paintColor, int textSize, Paint.Style style, int lineWidth) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(paintColor);
		paint.setStrokeWidth(lineWidth);
		paint.setDither(true);//设置防抖动
		paint.setTextSize(textSize);
		paint.setStyle(style);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
		return paint;
	}
}
