package com.mundane.androidtechniqueapply.ui.activitity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.base.BaseActionBarActivity;
import com.mundane.androidtechniqueapply.view.adapter.BannerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotIntegerMaxValueBannerActivity extends BaseActionBarActivity {

	@BindView(R.id.view_pager)
	ViewPager mViewPager;
	@BindView(R.id.activity_not_integer_max_value_banner)
	RelativeLayout mActivityNotIntegerMaxValueBanner;

	private BannerAdapter mBannerAdapter;
	private SensorManager mSensorManager;
	private Sensor mSensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_not_integer_max_value_banner);
		ButterKnife.bind(this);
		int[] drawables = {
			R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6};
		mBannerAdapter = new BannerAdapter(drawables, this, mViewPager);
		mViewPager.setAdapter(mBannerAdapter);

//		AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	}

	@Override
	protected void onResume() {
		mSensorManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mSensorEventListener);
		super.onPause();
	}

	private final String TAG = getClass().getSimpleName();

	private SensorEventListener mSensorEventListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			Log.d(TAG, "mSensor = " + mSensor.toString());
			float range = event.values[0];
			Log.d(TAG, "range = " + range);
			if (range == mSensor.getMaximumRange()) {
//				Toast.makeText(NotIntegerMaxValueBannerActivity.this, "正常模式", Toast.LENGTH_LONG).show();
				Log.d(TAG, "正常模式");
			} else {
//				Toast.makeText(NotIntegerMaxValueBannerActivity.this, "听筒模式", Toast.LENGTH_LONG).show();
				Log.d(TAG, "听筒模式");
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};


}
