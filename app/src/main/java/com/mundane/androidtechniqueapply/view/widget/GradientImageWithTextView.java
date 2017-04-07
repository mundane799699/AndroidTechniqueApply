package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mundane.androidtechniqueapply.R;

/**
 * Created by fangyuan.zhou on 2017/4/7 14:46
 */

public class GradientImageWithTextView extends LinearLayout {

	private GradientImageView mGradientImageView;
	private GradientTextView mGradientTextView;
	private float mAlpha = 0f;


	public GradientImageWithTextView(Context context) {
		this(context, null);
	}


	public GradientImageWithTextView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GradientImageWithTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GradientImageWithTextView);
		int indexCount = ta.getIndexCount();
		for (int i = 0; i < indexCount; i++) {
			int attr = ta.getIndex(i);
			switch (attr) {
				case R.styleable.GradientImageWithTextView_bottom_imageview:
					Drawable bottomDrawable = ta.getDrawable(attr);
					if (bottomDrawable != null) {
						mGradientImageView.setBottomImageViewDrawable(bottomDrawable);
					}
					break;
				case R.styleable.GradientImageWithTextView_top_imageview:
					Drawable topDrawable = ta.getDrawable(attr);
					if (topDrawable != null) {
						mGradientImageView.setTopImageViewDrawable(topDrawable);
					}
					break;
				case R.styleable.GradientImageWithTextView_bottom_color:
					int bottomTextColor = ta.getColor(attr, getResources().getColor(R.color.tab_text_gray));
					mGradientTextView.setBottomTextColor(bottomTextColor);
					break;
				case R.styleable.GradientImageWithTextView_top_color:
					int topTextColor = ta.getColor(attr, getResources().getColor(R.color.tab_text_green));
					mGradientTextView.setTopTextColor(topTextColor);
					break;
				case R.styleable.GradientImageWithTextView_all_text:
					String text = ta.getString(attr);
					mGradientTextView.setText(text);
					break;
			}
		}

	}

	public void setAllAlpha(float alpha) {
		mAlpha = alpha;
		mGradientTextView.setTextViewAlpha(mAlpha);
		mGradientImageView.setImageViewAlpha(mAlpha);
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

	private void init(Context context) {
		setOrientation(VERTICAL);//相当于xml中的android:gravity="center"
		setGravity(Gravity.CENTER);
		mGradientImageView = new GradientImageView(context);
		mGradientTextView = new GradientTextView(context);
		LayoutParams imageViewParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		LayoutParams textViewParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		imageViewParams.gravity = Gravity.CENTER_HORIZONTAL;//相当于xml中的android:layout_gravity="center_horizontal"
//		textViewParams.gravity = Gravity.CENTER_HORIZONTAL;
		addView(mGradientImageView, imageViewParams);
		addView(mGradientTextView, textViewParams);
	}
}
