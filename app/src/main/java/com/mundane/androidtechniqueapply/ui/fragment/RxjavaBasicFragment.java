package com.mundane.androidtechniqueapply.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.api.bean.ZhuangBiEntity;
import com.mundane.androidtechniqueapply.http.NetWork;
import com.mundane.androidtechniqueapply.view.adapter.ZhuangbiListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RxjavaBasicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RxjavaBasicFragment extends Fragment {

	@BindView(R.id.searchRb1)
	AppCompatRadioButton mSearchRb1;
	@BindView(R.id.searchRb2)
	AppCompatRadioButton mSearchRb2;
	@BindView(R.id.searchRb3)
	AppCompatRadioButton mSearchRb3;
	@BindView(R.id.searchRb4)
	AppCompatRadioButton mSearchRb4;
	@BindView(R.id.gridRv)
	RecyclerView mGridRv;
	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;
	@BindView(R.id.rg)
	RadioGroup mRg;
	//	todo 这么写的好处是每当切换到别的选项卡导致视图销毁再切换回来的时候, adapter不是再次new出来的而是上次的, 并且adapter中保存了上次的数据
	private ZhuangbiListAdapter mZhuangbiListAdapter = new ZhuangbiListAdapter();
	private boolean isSwipeRefreshEnabled = false;


	// FIXME: 2017/3/16  bug描述:切换到这一页, 不点击radiobutton, 马上再切换到比如第4页(反正是不相邻的)再点可爱是没有反应的
	public RxjavaBasicFragment() {
		// Required empty public constructor
	}

	public static Fragment newInstance() {
		RxjavaBasicFragment fragment = new RxjavaBasicFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_rxjava_basic, container, false);
		ButterKnife.bind(this, view);
		mGridRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		mGridRv.setAdapter(mZhuangbiListAdapter);
		mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
		mSwipeRefreshLayout.setEnabled(isSwipeRefreshEnabled);
		mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
		isFirstCheck = true;
		return view;
	}

	@Override
	public void onDestroyView() {
		isFirst = false;
		super.onDestroyView();
	}

	private boolean isFirst = true;
	private boolean isFirstCheck = true;

	@OnCheckedChanged({R.id.searchRb1, R.id.searchRb2, R.id.searchRb3, R.id.searchRb4})
	public void onCheck(RadioButton searchRb, boolean checked) {
		if (checked) {
			//	如果是第一次创建该页面, 则什么都不做, 将isFirst设置为false
			//	如果不是第一次创建该页面并且是第一次调用onCheck方法, 则不调用, 将isFirstCheck标记设置为false, 表示以后的onCheck方法都不是第一调用了
			if (!isFirst && isFirstCheck) {
				isFirstCheck = false;
				return;
			}
			unsubscribe();//解除订阅
			mZhuangbiListAdapter.setImages(null);
			mSwipeRefreshLayout.setRefreshing(true);
			search(searchRb.getText().toString());
		}
	}

	/**
	 * 解除订阅
	 */
	private void unsubscribe() {
		if (mDisposable == null) {
			return;
		}
		if (!mDisposable.isDisposed()) {
			mDisposable.dispose();
		}
	}

	private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
		@Override
		public void onRefresh() {
			int checkedRadioButtonId = mRg.getCheckedRadioButtonId();
			RadioButton checkedRadioButton = (RadioButton) RxjavaBasicFragment.this.getView().findViewById(checkedRadioButtonId);
			search(checkedRadioButton.getText().toString());
		}
	};

	private Disposable mDisposable;

	Observer<List<ZhuangBiEntity>> observer = new Observer<List<ZhuangBiEntity>>() {
		@Override
		public void onSubscribe(Disposable d) {
			mDisposable = d;
		}

		@Override
		public void onNext(List<ZhuangBiEntity> images) {
			mZhuangbiListAdapter.setImages(images);
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
				isSwipeRefreshEnabled = true;
				mSwipeRefreshLayout.setEnabled(isSwipeRefreshEnabled);
			}
		}
	};

	private void search(String value) {
		NetWork.getZhuangBiService()
				.search(value)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(observer);
	}


}
