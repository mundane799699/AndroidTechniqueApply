package com.mundane.androidtechniqueapply.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mundane.androidtechniqueapply.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestTouchConflictFragment extends Fragment {

    @BindView(R.id.listview)
    ListView mListview;


    public TestTouchConflictFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance() {
        TestTouchConflictFragment fragment = new TestTouchConflictFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragments
        View view = inflater.inflate(R.layout.fragment_test_touch_conflict, container, false);
        ButterKnife.bind(this, view);
        String[] arr = new String[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = "测试数据" + i;
        }
        mListview.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arr));
        return view;
    }

}
