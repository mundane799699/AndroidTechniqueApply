package com.mundane.androidtechniqueapply.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.view.adapter.MyRecyclerAdapter;
import com.mundane.androidtechniqueapply.model.ActivityModel;
import com.mundane.androidtechniqueapply.view.recyclerviewdecoration.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.rv)
	RecyclerView mRv;
	@BindView(R.id.activity_main)
	RelativeLayout mActivityMain;
	private MyRecyclerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
//		ActionBar actionBar = getSupportActionBar();
//		actionBar.setTitle("自定义View合集");
//		actionBar.setDisplayHomeAsUpEnabled(true);
		mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		ActivityModel[] arr = {new ActivityModel("竖直的ViewPager", "VerticalViewPagerActivity"),
				new ActivityModel("横向ViewPager嵌套竖直ViewPager", "HorizontalViewPagerActivity"),
				new ActivityModel("测试ontouchevent", "TestOntouchEventActivity"),
				new ActivityModel("ViewPager中嵌套ListView", "ListViewInViewPagerActivity"),
				new ActivityModel("外部拦截法解决ViewGroup嵌套ListView滑动冲突", "ListViewInViewGroupActivity"),
				new ActivityModel("内部拦截法解决ViewGroup嵌套ListView滑动冲突", "ListViewInViewGroupActivity2")
		};
		mAdapter = new MyRecyclerAdapter(arr, this);
		mRv.setAdapter(mAdapter);
	}
}
