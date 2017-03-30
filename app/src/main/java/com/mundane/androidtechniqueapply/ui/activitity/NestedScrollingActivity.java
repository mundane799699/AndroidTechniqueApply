package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.ui.fragment.TabFragment;
import com.mundane.androidtechniqueapply.view.widget.SimpleViewPagerIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NestedScrollingActivity extends AppCompatActivity {

	@BindView(R.id.top_view)
	RelativeLayout mTopView;
	@BindView(R.id.indicator)
	SimpleViewPagerIndicator mIndicator;
	@BindView(R.id.view_pager)
	ViewPager mViewPager;

	private String[] mTitles = new String[]{"简介", "评价", "相关"};
	private TabFragment[] mFragments = new TabFragment[mTitles.length];
	private FragmentPagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nested_scrolling);
		ButterKnife.bind(this);
		initData();
		initEvents();

	}

	private void initEvents() {
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				mIndicator.scroll(position, positionOffset);
			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		mIndicator.setOnTitleClickListener(new SimpleViewPagerIndicator.OnTitleClickListener() {
			@Override
			public void onTitleClick(View view, int index) {
				mViewPager.setCurrentItem(index, true);
			}
		});
	}

	private void initData() {
		mIndicator.setTitles(mTitles);
		for (int i = 0; i < mTitles.length; i++) {
			mFragments[i] = TabFragment.newInstance(mTitles[i]);
		}

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mTitles.length;
			}

			@Override
			public Fragment getItem(int position) {
				return mFragments[position];
			}

		};

		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
	}
}
