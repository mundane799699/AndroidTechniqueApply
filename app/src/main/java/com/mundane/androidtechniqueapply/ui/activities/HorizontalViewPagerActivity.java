package com.mundane.androidtechniqueapply.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.base.BaseActionBarActivity;
import com.mundane.androidtechniqueapply.ui.fragments.OneLevelFragment;
import com.mundane.androidtechniqueapply.view.adapter.ContentAdapter;
import com.mundane.androidtechniqueapply.view.transforms.ZoomOutPageTransformer;
import com.mundane.androidtechniqueapply.view.widgets.HorizontalViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalViewPagerActivity extends BaseActionBarActivity {

	@BindView(R.id.horizontal_viewpager)
	HorizontalViewPager mHorizontalViewpager;
	@BindView(R.id.activity_horizontal_view_pager)
	RelativeLayout mActivityHorizontalViewPager;

	private List<Fragment> mFragmentList;
	private ContentAdapter mContentAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horizontal_view_pager);
		ButterKnife.bind(this);
		mFragmentList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			mFragmentList.add(OneLevelFragment.newInstance(i));
		}
		mContentAdapter = new ContentAdapter(getSupportFragmentManager(), mFragmentList);
		mHorizontalViewpager.setAdapter(mContentAdapter);
		mHorizontalViewpager.setPageTransformer(false, new ZoomOutPageTransformer());
	}

}
