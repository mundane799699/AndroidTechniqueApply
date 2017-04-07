package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.ui.fragment.SimpleTabFragment;
import com.mundane.androidtechniqueapply.view.adapter.SimpleTabAdapter;
import com.mundane.androidtechniqueapply.view.widget.GradientImageWithTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LikeWeChatMainActivity extends AppCompatActivity {

	@BindView(R.id.view_pager)
	ViewPager mViewPager;
	@BindView(R.id.tab_wechat)
	GradientImageWithTextView mTabWechat;
	@BindView(R.id.tab_contact)
	GradientImageWithTextView mTabContact;
	@BindView(R.id.tab_discover)
	GradientImageWithTextView mTabDiscover;
	@BindView(R.id.tab_me)
	GradientImageWithTextView mTabMe;

	private List<Fragment> mTabs = new ArrayList<>();
	private List<GradientImageWithTextView> mGradientTabs = new ArrayList<>();
	private SimpleTabAdapter mSimpleTabAdapter;

	private final String TAG = "LikeWeChatMainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_like_we_chat_main);
		ButterKnife.bind(this);
		mGradientTabs.add(mTabWechat);
		mGradientTabs.add(mTabContact);
		mGradientTabs.add(mTabDiscover);
		mGradientTabs.add(mTabMe);
		initFragments();
		mSimpleTabAdapter = new SimpleTabAdapter(getSupportFragmentManager(), mTabs);
		mViewPager.setAdapter(mSimpleTabAdapter);
		mTabWechat.setAllAlpha(1);
		for (int i = 0; i < mGradientTabs.size(); i++) {
			final int finalIndex = i;
			mGradientTabs.get(i).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					resetAllTabs();
					mViewPager.setCurrentItem(finalIndex, false);
					mGradientTabs.get(finalIndex).setAllAlpha(1);
				}
			});
		}
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				Log.d(TAG, "position = " + position);
				Log.d(TAG, "positionOffset = " + positionOffset);
				mGradientTabs.get(position).setAllAlpha(1 - positionOffset);
				if (position + 1 < mGradientTabs.size()) {
					mGradientTabs.get(position + 1).setAllAlpha(positionOffset);
				}
			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	private void resetAllTabs() {
		for (GradientImageWithTextView gradientTab : mGradientTabs) {
			gradientTab.setAllAlpha(0);
		}
	}

	private void initFragments() {
		mTabs.add(SimpleTabFragment.newInstance("微信"));
		mTabs.add(SimpleTabFragment.newInstance("通讯录"));
		mTabs.add(SimpleTabFragment.newInstance("发现"));
		mTabs.add(SimpleTabFragment.newInstance("我"));
	}
}
