package com.mundane.androidtechniqueapply.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.view.widgets.HorizontalScrollViewEx2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewInViewGroupActivity extends AppCompatActivity {


	@BindView(R.id.lv1)
	ListView mLv1;
	@BindView(R.id.lv2)
	ListView mLv2;
	@BindView(R.id.lv3)
	ListView mLv3;
	@BindView(R.id.horizontal_scrollview)
	HorizontalScrollViewEx2 mHorizontalScrollview;
	@BindView(R.id.activity_list_view_in_view_group)
	RelativeLayout mActivityListViewInViewGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_in_view_group);
		ButterKnife.bind(this);
		initActionBar();
		String[] arr = new String[100];
		for (int i = 0; i < 100; i++) {
			arr[i] = "测试数据" + i;
		}
		mLv1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr));
		mLv2.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr));
		mLv3.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr));
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("外部拦截法解决滑动冲突示例");
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
