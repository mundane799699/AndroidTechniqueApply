package com.mundane.androidtechniqueapply.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.view.adapter.MyViewAdapter;
import com.mundane.androidtechniqueapply.view.widget.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneLevelFragment extends Fragment {


	@BindView(R.id.vertical_viewpager)
	VerticalViewPager mVerticalViewpager;
	@BindView(R.id.tv)
	TextView mTv;
	private MyViewAdapter mAdapter;
	private int mIndex;

	public OneLevelFragment() {
		// Required empty public constructor
	}

	public static final String BUNDLE_KEY = "bundlekey";

	public static Fragment newInstance(int index) {
		OneLevelFragment fragment = new OneLevelFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(BUNDLE_KEY, index);
		fragment.setArguments(bundle);
		return fragment;
	}

	private List<View> mViewList;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mIndex = bundle.getInt(BUNDLE_KEY);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_one_level, container, false);
		ButterKnife.bind(this, view);
		View view1 = inflater.inflate(R.layout.layout1, null);
		View view2 = inflater.inflate(R.layout.layout2, null);
		View view3 = inflater.inflate(R.layout.layout3, null);
		mViewList = new ArrayList<>();
		mTv.setText("" + mIndex);
		if (mIndex == 0) {
			mViewList.add(view1);
			mViewList.add(view2);
			mViewList.add(view3);
		} else if (mIndex == 1) {
			mViewList.add(view2);
			mViewList.add(view1);
			mViewList.add(view3);
		} else if (mIndex == 2) {
			mViewList.add(view3);
			mViewList.add(view1);
			mViewList.add(view2);
		}
		mAdapter = new MyViewAdapter(mViewList, getContext());
		mVerticalViewpager.setAdapter(mAdapter);
		return view;
	}

}
