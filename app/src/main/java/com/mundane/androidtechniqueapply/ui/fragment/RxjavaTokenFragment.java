package com.mundane.androidtechniqueapply.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.api.bean.FakeThing;
import com.mundane.androidtechniqueapply.api.bean.FakeToken;
import com.mundane.androidtechniqueapply.api.service.FakeApi;
import com.mundane.androidtechniqueapply.http.NetWork;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxjavaTokenFragment extends Fragment {


	@BindView(R.id.swipe_refresh_layout)
	SwipeRefreshLayout mSwipeRefreshLayout;
	@BindView(R.id.btn_load)
	Button mBtnLoad;
	@BindView(R.id.tv)
	TextView mTv;

	public RxjavaTokenFragment() {
		// Required empty public constructor
	}

	public static RxjavaTokenFragment newInstance() {
		RxjavaTokenFragment fragment = new RxjavaTokenFragment();
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
		View view = inflater.inflate(R.layout.fragment_rxjava_token, container, false);
		ButterKnife.bind(this, view);
		mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
		mSwipeRefreshLayout.setEnabled(false);
		return view;
	}

	@OnClick(R.id.btn_load)
	public void onClick() {
		mSwipeRefreshLayout.setRefreshing(true);
		final FakeApi fakeApi = NetWork.getFakeApi();
		fakeApi
				.getFakeToken("fake_auth_code")
				.flatMap(new Function<FakeToken, Observable<FakeThing>>() {
					@Override
					public Observable<FakeThing> apply(FakeToken fakeToken) throws Exception {
						return fakeApi.getFakeData(fakeToken);
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<FakeThing>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(FakeThing fakeData) {
						mSwipeRefreshLayout.setRefreshing(false);
						//String.format("我叫%2$s,她叫%1$s", "小明","小方"); // 我叫小方,她叫小明
						mTv.setText(String.format("获取到的数据：\nID：%2$d\n用户名：%1$s", fakeData.name, fakeData.id));
					}

					@Override
					public void onError(Throwable e) {
						mSwipeRefreshLayout.setRefreshing(false);
						Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete() {

					}
				});
	}
}
