package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.base.BaseActionBarActivity;
import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.view.widgets.MyScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewInScrollViewActivity extends BaseActionBarActivity {

	@BindView(R.id.lv)
	ListView mLv;
	@BindView(R.id.activity_list_view_in_scroll_view)
	RelativeLayout mActivityListViewInScrollView;
	@BindView(R.id.my_scrollview)
	MyScrollView mMyScrollview;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_in_scroll_view);
		ButterKnife.bind(this);
		mMyScrollview.setListView(mLv);

		String[] arr = new String[100];
		for (int i = 0; i < 100; i++) {
			arr[i] = "测试数据" + i;
		}
		mLv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr));
	}
}
