package com.mundane.androidtechniqueapply.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by mundane on 2017/2/24 15:19
 */

public class ContentAdapter extends FragmentPagerAdapter {

	private List<Fragment> mFragmentList;

	public ContentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
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
