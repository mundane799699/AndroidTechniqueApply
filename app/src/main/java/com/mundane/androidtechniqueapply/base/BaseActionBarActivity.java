package com.mundane.androidtechniqueapply.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.mundane.androidtechniqueapply.constant.Constant;

/**
 * Created by mundane on 2017/2/28 16:19
 */

public class BaseActionBarActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		String title = getIntent().getStringExtra(Constant.KEY_INTENT);
		if (TextUtils.isEmpty(title)) {
			title = "";
		}
		actionBar.setTitle(title);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
