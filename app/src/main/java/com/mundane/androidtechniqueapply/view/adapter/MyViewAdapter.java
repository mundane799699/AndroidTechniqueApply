package com.mundane.androidtechniqueapply.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by mundane on 2017/2/25 11:09
 */

public class MyViewAdapter extends PagerAdapter {

	private List<View> mViewList;
	private Context mContext;

	public MyViewAdapter(List<View> viewList, Context context) {
		mViewList = viewList;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mViewList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViewList.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View child = mViewList.get(position);
		container.addView(child);
		return child;
	}
}
