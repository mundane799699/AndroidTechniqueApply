package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.ui.fragment.DouBanMovieFragment;
import com.mundane.androidtechniqueapply.ui.fragment.RxjavaBasicFragment;
import com.mundane.androidtechniqueapply.ui.fragment.RxjavaMapFragment;

import java.util.ArrayList;
import java.util.List;

public class RxjavaAndRetrofitActivity extends AppCompatActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private List<Fragment> mFragmentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rxjava_and_retrofit);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mFragmentList = new ArrayList<>();

		mFragmentList.add(DouBanMovieFragment.newInstance());
		mFragmentList.add(RxjavaBasicFragment.newInstance());
		mFragmentList.add(RxjavaMapFragment.newInstance());

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mFragmentList);
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_rxjava_and_retrofit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> mFragmentList;

		public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			mFragmentList = fragmentList;
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "获取豆瓣电影数据";
				case 1:
					return "基本";
				case 2:
					return "转换(MAP)";
			}
			return null;
		}
	}
}
