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
	private ZhuangbiListAdapter mZhuangbiListAdapter;

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
		mZhuangbiListAdapter = new ZhuangbiListAdapter();
		mGridRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		mGridRv.setAdapter(mZhuangbiListAdapter);
		mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
		mSwipeRefreshLayout.setEnabled(false);
		mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
		return view;
	}

	@OnCheckedChanged({R.id.searchRb1, R.id.searchRb2, R.id.searchRb3, R.id.searchRb4})
	public void onCheck(RadioButton searchRb, boolean checked) {
		if (checked) {
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
				mSwipeRefreshLayout.setEnabled(true);
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
