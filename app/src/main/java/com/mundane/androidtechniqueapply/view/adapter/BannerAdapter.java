package com.mundane.androidtechniqueapply.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mundane.androidtechniqueapply.R;

/**
 * Created by mundane on 2017/2/28 19:23
 */

public class BannerAdapter extends PagerAdapter {

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private int[] mArrDrawables;

	private ViewPager mBanner;

	public BannerAdapter(int[] drawables, Context context, ViewPager banner) {
		mArrDrawables = drawables;
		mLayoutInflater = LayoutInflater.from(context);
		mBanner = banner;
	}

	@Override
	public int getCount() {
		return mArrDrawables.length + 2;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}


	private final String TAG = "BannerAdapter";
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		position = position % mArrDrawables.length;
		Log.d(TAG, "instantiateItem: " + position);
		View view = mLayoutInflater.inflate(R.layout.layout_banner, container, false);
		ImageView iv = (ImageView) view.findViewById(R.id.iv);
		iv.setImageResource(mArrDrawables[position]);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		int position = mBanner.getCurrentItem();
		if (position == 0) {
			position = mArrDrawables.length;
			mBanner.setCurrentItem(position, false);
		} else if (position == (mArrDrawables.length + 2) - 1) {
			position = 1;
			mBanner.setCurrentItem(position, false);
		}
	}
}
