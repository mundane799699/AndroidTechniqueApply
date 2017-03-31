package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.view.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LikeWeiXinStateButtonActivity extends AppCompatActivity {

	@BindView(R.id.switch_button)
	SwitchButton mSwitchButton;
	@BindView(R.id.tv)
	TextView mTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_like_wei_xin_state_button);
		ButterKnife.bind(this);
		mSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {
//				Toast.makeText(LikeWeiXinStateButtonActivity.this, , Toast.LENGTH_SHORT).show();
				mTv.setText(isChecked ? "开" : "关");
			}
		});
	}
}
