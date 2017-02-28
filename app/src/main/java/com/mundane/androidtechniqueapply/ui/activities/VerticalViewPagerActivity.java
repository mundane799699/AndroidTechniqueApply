package com.mundane.androidtechniqueapply.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.base.BaseActionBarActivity;
import com.mundane.androidtechniqueapply.ui.fragments.ContentFragment;
import com.mundane.androidtechniqueapply.view.adapter.ContentAdapter;
import com.mundane.androidtechniqueapply.view.widgets.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerticalViewPagerActivity extends BaseActionBarActivity {

	@BindView(R.id.verticalpager)
	VerticalViewPager mVerticalpager;
	@BindView(R.id.activity_main)
	RelativeLayout mActivityMain;
	private ContentAdapter mContentAdapter;
	private List<Fragment> mFragmentList;
	private Handler mHandler = new Handler();
	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			int currentItem = mVerticalpager.getCurrentItem();
			currentItem++;
			if (currentItem < mFragmentList.size()) {
				mVerticalpager.setCurrentItem(currentItem, true);
				mHandler.postDelayed(mRunnable, 1000);
			} else {
				mHandler.removeCallbacksAndMessages(null);
				mHandler = null;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vertical_viewpager);
		ButterKnife.bind(this);
		mFragmentList = new ArrayList<>();
		for (int i = 5; i >= 0; i--) {
			mFragmentList.add(ContentFragment.newInstance(i + ""));
		}
		mContentAdapter = new ContentAdapter(getSupportFragmentManager(), mFragmentList);
		mVerticalpager.setAdapter(mContentAdapter);
//		mHandler.postDelayed(mRunnable, 1000);
	}


	@Override
	protected void onDestroy() {
		if (mHandler != null) {
			mHandler.removeCallbacksAndMessages(null);
			mHandler = null;
		}
		super.onDestroy();
	}
}
