package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mundane.androidtechniqueapply.R;

/**
 * Created by fangyuan.zhou on 2017/4/7 11:54
 */

public class GradientTextView extends FrameLayout {
	private final float DEFAULT_TEXT_SIZE = 12;
	private TextView mBottomTextView;
	private TextView mTopTextView;
	private float mAlpha = 0f;

	public GradientTextView(@NonNull Context context) {
		this(context, null);
	}

	public GradientTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GradientTextView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);

		float textSize = ta.getFloat(R.styleable.GradientTextView_text_size, DEFAULT_TEXT_SIZE);
		mBottomTextView.setTextSize(textSize);
		mTopTextView.setTextSize(textSize);

		int topTextColor = ta.getColor(R.styleable.GradientTextView_top_text_color, getResources().getColor(R.color.tab_text_green));
		setTopTextColor(topTextColor);

		int bottomTextColor = ta.getColor(R.styleable.GradientTextView_bottom_text_color, getResources().getColor(R.color.tab_text_gray));
		setBottomTextColor(bottomTextColor);

		String text = ta.getString(R.styleable.GradientTextView_text);
		setText(text);
		ta.recycle();

		setTextViewAlpha(mAlpha);
	}

	public void setText(String text) {
		mBottomTextView.setText(text);
		mTopTextView.setText(text);
	}

	public void setBottomTextColor(int color) {
		mBottomTextView.setTextColor(color);
	}

	public void setTopTextColor(int color) {
		mTopTextView.setTextColor(color);
	}

	public void setTextViewAlpha(float alpha) {
		mAlpha = alpha;
		mTopTextView.setAlpha(alpha);
		mBottomTextView.setAlpha(1 - alpha);
	}

	private void init(Context context) {
		mBottomTextView = new TextView(context);
		mTopTextView = new TextView(context);
		LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		addView(mBottomTextView, layoutParams);
		addView(mTopTextView, layoutParams);
	}

	private static final String INSTANCE_STATE = "instance_state";
	private static final String STATE_ALPHA = "state_alpha";

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
		bundle.putFloat(STATE_ALPHA, mAlpha);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			mAlpha = bundle.getFloat(STATE_ALPHA);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
		} else {
			super.onRestoreInstanceState(state);
		}

	}
}
