package com.mundane.androidtechniqueapply.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by fangyuan.zhou on 2017/4/7 16:57
 */

public class SimpleTabAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragmentList;
	public SimpleTabAdapter(FragmentManager fm, List<Fragment> fragmentList) {
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
}
