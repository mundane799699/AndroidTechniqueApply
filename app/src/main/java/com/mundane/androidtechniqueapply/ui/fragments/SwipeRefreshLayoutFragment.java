package com.mundane.androidtechniqueapply.ui.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mundane.androidtechniqueapply.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SwipeRefreshLayoutFragment extends Fragment {


	@BindView(R.id.lv)
	ListView mLv;
	@BindView(R.id.swipe_refresh_layout)
	SwipeRefreshLayout mSwipeRefreshLayout;

	private Handler mHandler = new Handler();

	private String[] mArr;
	private ArrayAdapter<String> mAdapter;

	private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
		@Override
		public void onRefresh() {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < mArr.length; i++) {
						mArr[i] = "改变后的测试数据" + i;
					}
					mAdapter.notifyDataSetChanged();
					mSwipeRefreshLayout.setRefreshing(false);
				}
			}, 4000);
		}

	};

	public SwipeRefreshLayoutFragment() {
		// Required empty public constructor
	}

	public static Fragment newInstance() {
		SwipeRefreshLayoutFragment fragment = new SwipeRefreshLayoutFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_swipe_refresh_layout, container, false);
		ButterKnife.bind(this, view);
		mArr = new String[100];
		for (int i = 0; i < 100; i++) {
			mArr[i] = "测试数据" + i;
		}
		mAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mArr);
		mLv.setAdapter(mAdapter);
		mSwipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		//设置下拉刷新监听
		mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
		return view;
	}

}
