package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleViewPagerIndicator extends LinearLayout {

	private static final int COLOR_TEXT_NORMAL = 0xFF000000;
	private static final int COLOR_INDICATOR_COLOR = Color.GREEN;

	private String[] mTitles;
	private int mTabCount;
	private int mIndicatorColor = COLOR_INDICATOR_COLOR;
	private float mTranslationX;
	private Paint mPaint = new Paint();
	private int mTabWidth;

	public SimpleViewPagerIndicator(Context context) {
		this(context, null);
	}

	public SimpleViewPagerIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint.setColor(mIndicatorColor);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(9);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTabWidth = w / mTabCount;
	}

	public void setTitles(String[] titles) {
		mTitles = titles;
		mTabCount = titles.length;
		generateTitleView();

	}

	public void setIndicatorColor(int indicatorColor) {
		this.mIndicatorColor = indicatorColor;
	}

	private OnTitleClickListener mTitleClickListener;

	public interface OnTitleClickListener {
		//	接口中的变量必须都是公有的，静态的常量，即可以用 public static final 这些关键字修饰搜索。
		//	在接口中你可以不用这些关键字修饰，只要是在接口中声明变量，默认就是（即你加不加关键字修饰，系统都认为是公有的静态的常量）
		//	所以有的时候你想用一些public static final的constant可以用interface里面的数据, 比如将常用的工具类Constant定义为一个接口
		void onTitleClick(View view, int index);
	}

	public void setOnTitleClickListener(OnTitleClickListener listener) {
		this.mTitleClickListener = listener;
	}

	private final String TAG = getClass().getSimpleName();

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		canvas.save();
		canvas.translate(mTranslationX, getHeight() - 2);
		canvas.drawLine(0, 0, mTabWidth, 0, mPaint);
		canvas.restore();
	}

	public void scroll(int position, float offset) {
		/**
		 * <pre>
		 *  0-1:position=0 ;1-0:postion=0;
		 * </pre>
		 */
		Log.d(TAG, "position = " + position);
		Log.d(TAG, "offset = " + offset);
		//	getWidth() / mTabCount表示每一个tab的宽度
		mTranslationX = (getWidth() / mTabCount) * (position + offset);
		invalidate();
	}

	private void generateTitleView() {
		if (getChildCount() > 0) {
			this.removeAllViews();
		}
		setWeightSum(mTabCount);
		for (int i = 0; i < mTabCount; i++) {
			TextView tv = new TextView(getContext());
			LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT);
			lp.weight = 1;
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(COLOR_TEXT_NORMAL);
			tv.setText(mTitles[i]);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			tv.setLayoutParams(lp);
			tv.setTag(i);
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mTitleClickListener != null) {
						int index = (int) v.getTag();
						mTitleClickListener.onTitleClick(v, index);
					}
				}
			});
			addView(tv);
		}
	}

}
