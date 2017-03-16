package com.mundane.androidtechniqueapply.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by fangyuan.zhou on 2017/3/16 11:17
 */

public class AnimationFragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragmentList;
	public AnimationFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
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
				return "java代码";
			case 1:
				return "XML";
			default:
				break;
		}
		return super.getPageTitle(position);
	}
}
