package com.mundane.androidtechniqueapply.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.api.bean.Item;
import com.mundane.androidtechniqueapply.api.function.GankBeautyResultToItemsMapper;
import com.mundane.androidtechniqueapply.http.NetWork;
import com.mundane.androidtechniqueapply.view.adapter.ItemListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxjavaMapFragment extends Fragment {

	@BindView(R.id.pageTv)
	TextView mPageTv;
	@BindView(R.id.previousPageBt)
	AppCompatButton mPreviousPageBt;
	@BindView(R.id.nextPageBt)
	AppCompatButton mNextPageBt;
	@BindView(R.id.rv)
	RecyclerView mRv;
	@BindView(R.id.swipe_refresh_layout)
	SwipeRefreshLayout mSwipeRefreshLayout;
	private ItemListAdapter mItemListAdapter;
	private Disposable mDisposable;
	private Observer<List<Item>> Observer = new Observer<List<Item>>() {
		@Override
		public void onSubscribe(Disposable d) {
			mDisposable = d;
		}

		@Override
		public void onNext(List<Item> images) {
			mPageTv.setText(String.format("第%d页", page));
			mItemListAdapter.setImages(images);
		}

		@Override
		public void onError(Throwable e) {
			mSwipeRefreshLayout.setRefreshing(false);
			Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete() {
			mSwipeRefreshLayout.setRefreshing(false);
			if (!mSwipeRefreshLayout.isEnabled()) {
				mSwipeRefreshLayout.setEnabled(true);
			}
		}
	};
	private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
		@Override
		public void onRefresh() {
			loadPage(page);
		}
	};

	public RxjavaMapFragment() {
		// Required empty public constructor
	}

	public static Fragment newInstance() {
		RxjavaMapFragment fragment = new RxjavaMapFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
		}
	}

	// todo 我发现切到第一个选项卡再切回来的时候, 虽然视图被销毁了, 但是想page这种全局变量还是保存着的
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_rxjava_map, container, false);
		ButterKnife.bind(this, view);

		mRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		mItemListAdapter = new ItemListAdapter();
		mRv.setAdapter(mItemListAdapter);
		mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
		mSwipeRefreshLayout.setEnabled(false);

		mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
		return view;
	}

	//	表示当前是第几页
	private int page = 0;

	@OnClick({R.id.previousPageBt, R.id.nextPageBt})
	public void onClick(View view) {
		mSwipeRefreshLayout.setRefreshing(true);
		switch (view.getId()) {
			case R.id.previousPageBt:
				loadPage(--page);
				if (page <= 1) {//如果当前页面处于第一页, 上一页的按钮变灰
					mPreviousPageBt.setEnabled(false);
				}
				break;
			case R.id.nextPageBt:
				loadPage(++page);
				if (page >= 2) {//如果当前页面处于第二页了(点击上一页可以回到第一页了), 上一页的按钮变成可点击
					mPreviousPageBt.setEnabled(true);
				}
				break;
		}
	}

	private void loadPage(int page) {

		NetWork.getGankService()
				.getBeauties(10, page)
				.map(GankBeautyResultToItemsMapper.getInstance())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(Observer);
	}
}
