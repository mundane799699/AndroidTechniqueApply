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

public class ObjectAnimateFragment extends Fragment {


	@BindView(R.id.tabs)
	TabLayout mTabs;
	@BindView(R.id.viewpager)
	ViewPager mViewpager;

	public ObjectAnimateFragment() {
		// Required empty public constructor
	}

	public static ObjectAnimateFragment newInstance() {
		ObjectAnimateFragment fragment = new ObjectAnimateFragment();
		return fragment;
	}

	private List<Fragment> mFragmentList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFragmentList = new ArrayList<>();
		if (getArguments() != null) {
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_object_animate, container, false);
		ButterKnife.bind(this, view);
		initView();
		return view;
	}

	private void initView() {
		mFragmentList.add(ObjectAnimateSubJavaFragment.newInstance());
		mFragmentList.add(ObjectAnimateSubXMLFragment.newInstance());
		AnimationFragmentAdapter adapter = new AnimationFragmentAdapter(getChildFragmentManager(), mFragmentList);
		mViewpager.setAdapter(adapter);
		mTabs.setupWithViewPager(mViewpager);
	}

}
