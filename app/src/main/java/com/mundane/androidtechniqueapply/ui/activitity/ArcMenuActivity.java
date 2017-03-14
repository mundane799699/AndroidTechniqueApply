package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.base.BaseActionBarActivity;
import com.mundane.androidtechniqueapply.view.widget.ArcMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArcMenuActivity extends BaseActionBarActivity {

	@BindView(R.id.list_view)
	ListView mListView;
	@BindView(R.id.arc_menu)
	ArcMenu mArcMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arc_menu);
		ButterKnife.bind(this);
		initData();
		mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDataList));
		initEvent();
	}

	private void initEvent() {
		mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (mArcMenu.isOpen()) {
					mArcMenu.toggleMenu();
				}
			}
		});

		mArcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
			@Override
			public void onClick(View view, int pos) {
				Toast.makeText(ArcMenuActivity.this, pos + ":" + view.getTag(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private List<String> mDataList;

	private void initData() {
		mDataList = new ArrayList<>();
		for (int i = 'A'; i <= 'Z'; i++) {
			mDataList.add(String.valueOf((char)i));
		}
	}
}
