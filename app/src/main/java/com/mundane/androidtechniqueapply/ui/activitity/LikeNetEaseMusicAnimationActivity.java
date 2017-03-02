package com.mundane.androidtechniqueapply.ui.activitity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mundane.androidtechniqueapply.R;

public class LikeNetEaseMusicAnimationActivity extends AppCompatActivity {

	private Handler mHandler = new Handler();
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_like_net_ease_music_animation);
		mContext = this;
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(mContext, LikeNetEaseMusiMainActivity.class);
				mContext.startActivity(intent);
				finish();
			}
		}, 2000);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
	}

	@Override
	protected void onDestroy() {
		if (mHandler != null) {
			mHandler.removeCallbacksAndMessages(null);
			mHandler = null;
		}
		super.onDestroy();
	}

}
