package com.mundane.androidtechniqueapply.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.view.adapter.AnimationFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrameAnimationFragment extends Fragment {


	@BindView(R.id.tabs)
	TabLayout mTabs;
	@BindView(R.id.viewpager)
	ViewPager mViewpager;

	public FrameAnimationFragment() {
		// Required empty public constructor
	}

	public static Fragment newInstance() {
		FrameAnimationFragment fragment = new FrameAnimationFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFragmentList = new ArrayList<>();
		if (getArguments() != null) {

		}
	}

	private List<Fragment> mFragmentList;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_frame_animation, container, false);
		ButterKnife.bind(this, view);
		initView();
		return view;
	}

	private void initView() {
		mFragmentList.add(FrameAnimationSubJavaFragment.newInstance());
		mFragmentList.add(FrameAnimationSubXMLFragment.newInstance());

		AnimationFragmentAdapter adapter = new AnimationFragmentAdapter(getChildFragmentManager(), mFragmentList);
		mViewpager.setAdapter(adapter);
		mTabs.setupWithViewPager(mViewpager, true);
	}

}
