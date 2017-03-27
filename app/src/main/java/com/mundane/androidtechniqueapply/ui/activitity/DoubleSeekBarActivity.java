package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.view.widget.DoubleSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoubleSeekBarActivity extends AppCompatActivity {

	@BindView(R.id.double_seek_bar)
	DoubleSeekBar mDoubleSeekBar;

	private final String TAG = getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_double_seek_bar);
		ButterKnife.bind(this);
		List<String> data = new ArrayList<>();
		data.add("0元");
		data.add("400元");
		data.add("800元");
		data.add("1600元");
		data.add("无限");

		mDoubleSeekBar.setData(data);

	}
}
