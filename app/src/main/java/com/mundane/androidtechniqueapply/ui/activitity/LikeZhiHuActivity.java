package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.view.adapter.ListRecyclerAdapter;
import com.mundane.androidtechniqueapply.view.behavior.ScaleDownShowBehavior;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LikeZhiHuActivity extends AppCompatActivity {

	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.recycle_view)
	RecyclerView mRecycleView;
	@BindView(R.id.tab_layout)
	LinearLayout mTabLayout;
	@BindView(R.id.fab)
	FloatingActionButton mFab;
	private BottomSheetBehavior<LinearLayout> mBottomSheetBehavior;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_like_zhi_hu);
		ButterKnife.bind(this);

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mRecycleView.setHasFixedSize(true);
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			list.add("我是第" + i + "个");
		}
		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		layoutManager.setSmoothScrollbarEnabled(true);
		mRecycleView.setLayoutManager(layoutManager);
		ListRecyclerAdapter adapter = new ListRecyclerAdapter(list);
		mRecycleView.setAdapter(adapter);

		mFab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Snackbar.make(mFab, "点击了floatingactionbutton", Snackbar.LENGTH_SHORT).show();
			}
		});

		ScaleDownShowBehavior scaleDownShowBehavior = ScaleDownShowBehavior.from(mFab);
		
		scaleDownShowBehavior.setOnStateChangedListener(mOnStateChangedListener);


		mBottomSheetBehavior = BottomSheetBehavior.from(mTabLayout);
	}

	private boolean initialize = false;

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (!initialize) {
			initialize = true;
			mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
		}
	}

	private ScaleDownShowBehavior.OnStateChangeListener mOnStateChangedListener = new ScaleDownShowBehavior.OnStateChangeListener() {
		@Override
		public void onChanged(boolean isShow) {
			mBottomSheetBehavior.setState(isShow? BottomSheetBehavior.STATE_EXPANDED:BottomSheetBehavior.STATE_COLLAPSED);
		}
	};

}
