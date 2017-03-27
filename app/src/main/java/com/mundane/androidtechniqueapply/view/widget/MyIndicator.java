package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by fangyuan.zhou on 2017/3/22 9:49
 */

public class MyIndicator extends LinearLayout {

	private static final float DEFAULT_INDICATOR_WIDTH = 10;
	private static final int DEFAULT_INDICATOR_COLOR = Color.BLUE;
	private static final int DEFAULT_TAB_PADDING = 5;
	private Context mContext;
	private Paint mPaint;
	private int mWidth;
	private int mHeight;
	private int mTabWidth;
	private float mTranslationX;

	public MyIndicator(Context context) {
		this(context, null);
	}

	public MyIndicator(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(DEFAULT_INDICATOR_WIDTH);
		mPaint.setColor(DEFAULT_INDICATOR_COLOR);
		setOrientation(HORIZONTAL);
	}

	private String[] mTabs;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		mHeight = h;
		mTabWidth = mWidth / mTabs.length;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		canvas.save();
		canvas.translate(mTranslationX, getHeight()-2);
		canvas.drawLine(0, 0, mTabWidth, 0, mPaint);
		canvas.restore();
	}

	public void scroll(int position, float offset) {
		mTranslationX = (mWidth / mTabs.length * (position + offset));
		invalidate();
	}

	private OnTitleClickListener mTitleClickListener;

	public interface OnTitleClickListener {
		void onTitleClick(View view, int index);
	}

	public void setOnTitleClickListener(OnTitleClickListener listener) {
		this.mTitleClickListener = listener;
	}

	public void setTitles(String[] tabs) {
		mTabs = tabs;
		generateTabText();
	}

	private void generateTabText() {
		if (getChildCount() > 0) {
			removeAllViews();
		}

		int count = mTabs.length;
		setWeightSum(count);

		for (int i = 0; i < count; i++) {
			TextView textView = new TextView(mContext);
			LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
			params.weight = 1;
			textView.setText(mTabs[i]);
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			textView.setLayoutParams(params);
			textView.setTag(i);
			textView.setPadding(DEFAULT_TAB_PADDING, DEFAULT_TAB_PADDING, DEFAULT_TAB_PADDING, DEFAULT_TAB_PADDING);
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mTitleClickListener != null) {
						int index = (int) v.getTag();
						mTitleClickListener.onTitleClick(v, index);
					}
				}
			});
			addView(textView);
		}
	}
}
