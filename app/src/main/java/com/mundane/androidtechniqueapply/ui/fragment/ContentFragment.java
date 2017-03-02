package com.mundane.androidtechniqueapply.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mundane.androidtechniqueapply.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {


	@BindView(R.id.tv)
	TextView mTv;
	private String mContent;

	public ContentFragment() {
		// Required empty public constructor
	}

	public static final String BUNDLE_KEY = "bundlekey";

	public static Fragment newInstance(String content) {
		ContentFragment fragment = new ContentFragment();
		Bundle bundle = new Bundle();
		bundle.putString(BUNDLE_KEY, content);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mContent = bundle.getString(BUNDLE_KEY);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_content, container, false);
		ButterKnife.bind(this, view);
		mTv.setText(mContent);
		return view;
	}

}
