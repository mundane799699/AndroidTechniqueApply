package com.mundane.androidtechniqueapply.ui.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.api.bean.Subject;
import com.mundane.androidtechniqueapply.http.HttpMethods;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class DouBanMovieFragment extends Fragment {


	@BindView(R.id.tv_result)
	TextView mTvResult;
	@BindView(R.id.btn_click_me)
	Button mBtnClickMe;

	private Observer mObserver;

	private Activity mActivity;

	private ProgressDialog mProgressDialog;


	public DouBanMovieFragment() {
		// Required empty public constructor
	}

	public static Fragment newInstance() {
		DouBanMovieFragment fragment = new DouBanMovieFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
		mProgressDialog = new ProgressDialog(mActivity);
	}

	private ProgressDialog createProgressDialog() {
		return new ProgressDialog(mActivity);
	}

	private void showProgressDialog() {
		if (mProgressDialog == null) {//判断null的用单独的一个if, 写在最上面
			mProgressDialog = createProgressDialog();
		}
		if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	private void dismissProgressDialog() {
		if (mProgressDialog == null) {//判断null的用单独的一个if, 写在最上面
			return;
		}
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_doui_ban_movie, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@OnClick(R.id.btn_click_me)
	public void onClick() {
		getMovie();
	}

	private void getMovie() {
		//onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。
		//RxJava 规定，当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。
		//onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。
		//在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，并且是事件序列中的最后一个。
		//需要注意的是，onCompleted() 和 onError() 二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个
//		mObserver = new Observer<List<Subject>>(){
//			@Override
//			public void onSubscribe(Disposable d) {
//
//			}
//
//			@Override
//			public void onNext(List<Subject> value) {
//				mTvResult.setText(value.get(0).original_title);
//			}
//
//			@Override
//			public void onError(Throwable e) {
//				mTvResult.setText(e.getMessage());
//			}
//
//			@Override
//			public void onComplete() {
//				Toast.makeText(getContext(), "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
//			}
//
//		};
		mObserver = new DisposableObserver<List<Subject>>(){


			@Override
			protected void onStart() {
				showProgressDialog();
			}

			@Override
			public void onNext(List<Subject> subjects) {
				mTvResult.setText(subjects.toString());
			}

			@Override
			public void onError(Throwable e) {
				mTvResult.setText(e.getMessage());
				if (e instanceof SocketTimeoutException) {
					Toast.makeText(getContext(), "连接超时，请检查您的网络状态", Toast.LENGTH_SHORT).show();
				} else if (e instanceof ConnectException) {
					Toast.makeText(getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				dismissProgressDialog();
			}

			@Override
			public void onComplete() {
				dismissProgressDialog();
				Toast.makeText(getContext(), "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
			}
		};
		HttpMethods.getInstance().getTopMovie(mObserver, 0, 10);
	}
}
