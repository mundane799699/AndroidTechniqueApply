package com.mundane.androidtechniqueapply.ui.fragment;


import android.graphics.drawable.AnimationDrawable;
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

public class FrameAnimationSubXMLFragment extends Fragment {

	@BindView(R.id.btn_start)
	Button mBtnStart;
	@BindView(R.id.view)
	View mView;
	@BindView(R.id.btn_stop)
	Button mBtnStop;
	@BindView(R.id.iv)
	ImageView mIv;

	public FrameAnimationSubXMLFragment() {
		// Required empty public constructor
	}

	public static FrameAnimationSubXMLFragment newInstance() {
		FrameAnimationSubXMLFragment fragment = new FrameAnimationSubXMLFragment();
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
		View view = inflater.inflate(R.layout.fragment_frame_animation_sub_xml, container, false);
		ButterKnife.bind(this, view);
		initView();
		return view;
	}

	private void initView() {

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
