package com.mundane.androidtechniqueapply.ui.fragment;


import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mundane.androidtechniqueapply.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrameAnimationSubJavaFragment extends Fragment {

	@BindView(R.id.btn_start)
	Button mBtnStart;
	@BindView(R.id.view)
	View mView;
	@BindView(R.id.btn_stop)
	Button mBtnStop;
	@BindView(R.id.iv)
	ImageView mIv;

	public FrameAnimationSubJavaFragment() {
		// Required empty public constructor
	}

	public static FrameAnimationSubJavaFragment newInstance() {
		FrameAnimationSubJavaFragment fragment = new FrameAnimationSubJavaFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_frame_animation_sub_java, container, false);
		ButterKnife.bind(this, view);
		AnimationDrawable anim = new AnimationDrawable();
		for (int i = 1; i <= 6; i++) {
			int redId = getResources().getIdentifier("icon" + i, "drawable", getContext().getPackageName());
			Drawable drawable = getResources().getDrawable(redId);
			anim.addFrame(drawable, 200);
		}
		anim.setOneShot(false);
		mIv.setImageDrawable(anim);
		return view;
	}

	@OnClick({R.id.btn_start, R.id.btn_stop})
	public void onClick(View view) {
		AnimationDrawable drawable = null;
		switch (view.getId()) {
			case R.id.btn_start:
				drawable = (AnimationDrawable) mIv.getDrawable();
				if (!drawable.isRunning()) {
					drawable.start();
				}
				break;
			case R.id.btn_stop:
				drawable = (AnimationDrawable) mIv.getDrawable();
				if (drawable.isRunning()) {
					drawable.stop();
				}
				break;
		}
	}
}
