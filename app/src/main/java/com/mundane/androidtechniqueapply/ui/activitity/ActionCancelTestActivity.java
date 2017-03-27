package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.mundane.androidtechniqueapply.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActionCancelTestActivity extends AppCompatActivity {

	@BindView(R.id.btn)
	Button mBtn;

	private final String TAG = getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_cancel_test);
		ButterKnife.bind(this);
		mBtn.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						Log.d(TAG, "ACTION_DOWN");
						break;
					case MotionEvent.ACTION_UP:
						Log.d(TAG, "ACTION_UP");
						break;
					case MotionEvent.ACTION_MOVE:
//						Log.d(TAG, "ACTION_MOVE");
						break;
					case MotionEvent.ACTION_CANCEL:
						Log.d(TAG, "ACTION_CANCEL");
						break;
				}
				return true;
			}
		});
	}
}
