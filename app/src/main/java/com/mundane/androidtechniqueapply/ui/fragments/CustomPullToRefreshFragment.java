package com.mundane.androidtechniqueapply.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.view.widgets.RefreshLayout;
import com.mundane.androidtechniqueapply.view.widgets.ShopView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomPullToRefreshFragment extends Fragment {


	@BindView(R.id.lv)
	ListView mLv;
	@BindView(R.id.refresh_layout)
	RefreshLayout mRefreshLayout;
	private String[] mArr;
	private ArrayAdapter mAdapter;

	public CustomPullToRefreshFragment() {
		// Required empty public constructor
	}

	public static Fragment newInstance() {
		CustomPullToRefreshFragment fragment = new CustomPullToRefreshFragment();
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_custom_pull_to_refresh, container, false);
		ButterKnife.bind(this, view);
		mArr = new String[100];
		for (int i = 0; i < 100; i++) {
			mArr[i] = "测试数据" + i;
		}
		mAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mArr);
		mLv.setAdapter(mAdapter);
		mRefreshLayout.setRefreshListener(mOnRefreshListener);
		ShopView shopView = new ShopView(getContext());
		mRefreshLayout.setRefreshHeader(shopView);
//		mRefreshLayout.autoRefresh();
		return view;
	}

	private RefreshLayout.OnRefreshListener mOnRefreshListener = new RefreshLayout.OnRefreshListener() {
		@Override
		public void onRefresh() {
			mRefreshLayout.postDelayed(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < mArr.length; i++) {
						mArr[i] = "改变后的测试数据" + i;
					}
					mAdapter.notifyDataSetChanged();
					mRefreshLayout.refreshComplete();
				}
			}, 3000);
		}
	};

}
