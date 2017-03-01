package com.mundane.androidtechniqueapply.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.base.BaseActionBarActivity;
import com.mundane.androidtechniqueapply.ui.fragments.CustomPullToRefreshFragment;
import com.mundane.androidtechniqueapply.ui.fragments.SwipeRefreshLayoutFragment;
import com.mundane.androidtechniqueapply.view.adapter.ContentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomPullToRefreshActivity extends BaseActionBarActivity {


	@BindView(R.id.view_pager)
	ViewPager mViewPager;
	@BindView(R.id.activity_custom_pull_to_refresh)
	RelativeLayout mActivityCustomPullToRefresh;
	private List<Fragment> mFragmentList;
	private ContentAdapter mContentAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_pull_to_refresh);
		ButterKnife.bind(this);
		mFragmentList = new ArrayList<>();
		mFragmentList.add(SwipeRefreshLayoutFragment.newInstance());
		mFragmentList.add(CustomPullToRefreshFragment.newInstance());
		mContentAdapter = new ContentAdapter(getSupportFragmentManager(), mFragmentList);
		mViewPager.setAdapter(mContentAdapter);
	}


}
