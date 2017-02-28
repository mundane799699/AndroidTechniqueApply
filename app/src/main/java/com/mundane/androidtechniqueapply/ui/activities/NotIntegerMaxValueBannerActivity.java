package com.mundane.androidtechniqueapply.ui.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.base.BaseActionBarActivity;
import com.mundane.androidtechniqueapply.view.adapter.BannerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotIntegerMaxValueBannerActivity extends BaseActionBarActivity {

	@BindView(R.id.view_pager)
	ViewPager mViewPager;
	@BindView(R.id.activity_not_integer_max_value_banner)
	RelativeLayout mActivityNotIntegerMaxValueBanner;

	private BannerAdapter mBannerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_not_integer_max_value_banner);
		ButterKnife.bind(this);
		int[] drawables = {
			R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6};
		mBannerAdapter = new BannerAdapter(drawables, this, mViewPager);
		mViewPager.setAdapter(mBannerAdapter);
	}
}
