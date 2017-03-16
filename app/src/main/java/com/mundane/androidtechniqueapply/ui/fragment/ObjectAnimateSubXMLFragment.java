package com.mundane.androidtechniqueapply.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mundane.androidtechniqueapply.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObjectAnimateSubXMLFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectAnimateSubXMLFragment extends Fragment {


	public ObjectAnimateSubXMLFragment() {
		// Required empty public constructor
	}

	public static ObjectAnimateSubXMLFragment newInstance() {
		ObjectAnimateSubXMLFragment fragment = new ObjectAnimateSubXMLFragment();
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
		return inflater.inflate(R.layout.fragment_object_animate_sub_xml, container, false);
	}

}
