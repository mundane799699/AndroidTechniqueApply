package com.mundane.androidtechniqueapply.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mundane.androidtechniqueapply.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimpleTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleTabFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	@BindView(R.id.tv)
	TextView mTv;

	// TODO: Rename and change types of parameters
	private String mParam1;


	public SimpleTabFragment() {
		// Required empty public constructor
	}

	public static SimpleTabFragment newInstance(String param1) {
		SimpleTabFragment fragment = new SimpleTabFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_simple_tab, container, false);
		ButterKnife.bind(this, view);
		if (!TextUtils.isEmpty(mParam1)) {
			mTv.setText(mParam1);
		}
		return view;
	}

}
