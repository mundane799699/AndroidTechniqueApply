package com.mundane.androidtechniqueapply.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.base.BaseActionBarActivity;
import com.mundane.androidtechniqueapply.ui.fragments.TestTouchConflictFragment;
import com.mundane.androidtechniqueapply.view.adapter.TouchConflictAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewInViewPagerActivity extends BaseActionBarActivity {

    @BindView(R.id.viewpager)
    ViewPager      mViewpager;
    @BindView(R.id.activity_test_touch_conflict)
    RelativeLayout mActivityTestTouchConflict;
    private TouchConflictAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_touch_conflict);
        ButterKnife.bind(this);
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragments.add(TestTouchConflictFragment.newInstance());
        }
        mAdapter = new TouchConflictAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(mAdapter);
    }

}
