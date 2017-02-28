package com.mundane.androidtechniqueapply.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
		mContext = context;
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

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		/**
		 *    这个position是当前页面的下一个页面的缓存，若是想做滑动，position=当前item-1反之则加一
		 *    举个例子这个viewpager总共包含了imagesize+2=7个页面
		 *    positio=5时 position % ImageSize=0，切换到了第一个position
		 */
		position = position % mArrDrawables.length;
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
		/**
		 *  第五这里获得当前的positon然后对其setCurrentItem进行变换
		 *  这里设置当position=0时把position设置为图片列表的最大值
		 *  是为了position=0时左滑显示最后一张，我举个例子这里ImageSize是5
		 *  当position==0时设置为5，左滑就是position=4，也就是第五张图片，
		 *
		 *  if (position == (ImageSize+2) - 1)
		 *  这个判断 (ImageSize+2)这个是给viewpager设置的页面数，这里是7
		 *  当position==7-1=6时，这时viewpager就滑到头了，所以把currentItem设置为1
		 *  这里设置为1还是为了能够左滑，这时左滑position=0又执行了第一个判断又设置为5，
		 *  这样就实现了无限轮播的效果
		 *  setCurrentItem(position,false);
		 *  这里第二个参数false是消除viewpager设置item时的滑动动画，不理解的去掉它运行下就知道啥意思了
		 *
		 */
		if (position == 0) {
			position = mArrDrawables.length;
			mBanner.setCurrentItem(position, false);
		} else if (position == (mArrDrawables.length + 2) - 1) {
			position = 1;
			mBanner.setCurrentItem(position, false);
		}
	}
}
