package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mundane.androidtechniqueapply.R;

/**
 * Created by fangyuan.zhou on 2017/4/7 14:26
 */

public class GradientImageView extends FrameLayout {

	private ImageView mBottomImageView;
	private ImageView mTopImageView;
	private float mAlpha = 0f;
	private static final String INSTANCE_STATE = "instance_state";
	private static final String STATE_ALPHA = "state_alpha";

	public GradientImageView(@NonNull Context context) {
		this(context, null);
	}

	public GradientImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public void setTopImageViewDrawable(Drawable drawable) {
		mTopImageView.setImageDrawable(drawable);
	}

	public void setBottomImageViewDrawable(Drawable drawable) {
		mBottomImageView.setImageDrawable(drawable);
	}

	public GradientImageView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GradientImageView);

		Drawable bottomDrawable = ta.getDrawable(R.styleable.GradientImageView_bottom_image);
		if (bottomDrawable != null) {
			setBottomImageViewDrawable(bottomDrawable);//灰色
		}

		Drawable topDrawable = ta.getDrawable(R.styleable.GradientImageView_top_image);
		if (topDrawable != null) {
			setTopImageViewDrawable(topDrawable);//绿色
		}
		ta.recycle();
		setImageViewAlpha(mAlpha);

	}

	public void setImageViewAlpha(float alpha) {
		mAlpha = alpha;
		mTopImageView.setAlpha(alpha);
		mBottomImageView.setAlpha(1 - alpha);
	}

	private void init(Context context) {
		mBottomImageView = new ImageView(context);
		mTopImageView = new ImageView(context);
		LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		addView(mBottomImageView, layoutParams);
		addView(mTopImageView, layoutParams);
	}


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
