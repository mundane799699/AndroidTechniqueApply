package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.ui.fragment.FrameAnimationFragment;
import com.mundane.androidtechniqueapply.ui.fragment.ObjectAnimateFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimationActivity extends AppCompatActivity {

	@BindView(R.id.fl_content)
	FrameLayout mFlContent;
	@BindView(R.id.rg)
	RadioGroup mRg;

	private int mFragmentIndex;
	private List<Fragment> mFragmentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation);
		ButterKnife.bind(this);
		initEvent();
	}

	private void initEvent() {
		mFragmentList = new ArrayList<>();
		mFragmentList.add(FrameAnimationFragment.newInstance());
		mFragmentList.add(ObjectAnimateFragment.newInstance());
		mFragmentList.add(FrameAnimationFragment.newInstance());
		mFragmentList.add(FrameAnimationFragment.newInstance());
		turnToFragment();
		mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
				switch (checkedId) {
					case R.id.rb_frame:
						mFragmentIndex = 0;
						break;
					case R.id.rb_object:
						mFragmentIndex = 1;
						break;
					case R.id.rb_tween:
						mFragmentIndex = 2;
						break;
					case R.id.rb_view_compat:
						mFragmentIndex = 3;
						break;
					default:
						break;
				}
				turnToFragment();
			}
		});
	}

	private Fragment mCurrentFragment;

	private void turnToFragment() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment toFragment = mFragmentList.get(mFragmentIndex);
		if (mCurrentFragment != null) {
			ft.hide(mCurrentFragment);
		}
		if (toFragment.isAdded()) {
			ft.show(toFragment);
		} else {
			ft.add(R.id.fl_content, toFragment);
		}
		int commmit = ft.commitAllowingStateLoss();
		mCurrentFragment = toFragment;


	}

}
