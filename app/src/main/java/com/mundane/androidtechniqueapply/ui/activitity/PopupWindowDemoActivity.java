package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.model.SportItem;
import com.mundane.androidtechniqueapply.view.adapter.CustomPopupWindowRvAdapter;
import com.mundane.androidtechniqueapply.view.widget.CustomPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupWindowDemoActivity extends AppCompatActivity {

	@BindView(R.id.tv_pop)
	TextView mTvPop;
	@BindView(R.id.linearLayoutBg)
	LinearLayout mLinearLayoutBg;
	private RecyclerView mRv;

	private List<SportItem> mList = new ArrayList<>();
	private CustomPopupWindowRvAdapter mAdapter;
	private CustomPopupWindow mCustomPopupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup_window_demo);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.tv_pop)
	public void onViewClicked() {
		if (mCustomPopupWindow == null) {
			mCustomPopupWindow = createPopupWindow();
		}
		if (mCustomPopupWindow.isShowing()) {
			mCustomPopupWindow.dismiss();
		} else {
			mCustomPopupWindow.showAsDropDown(mTvPop);
			mLinearLayoutBg.setVisibility(View.VISIBLE);
		}

	}

	private CustomPopupWindow createPopupWindow() {
		View view = View.inflate(this, R.layout.popupwindow_custom, null);
		initView(view);
		CustomPopupWindow customPopupWindow = new CustomPopupWindow(this, view);
		customPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				mLinearLayoutBg.setVisibility(View.GONE);
			}
		});
		return customPopupWindow;
	}

	private void initView(View view) {
		mRv = (RecyclerView) view.findViewById(R.id.rv);
		mRv.setLayoutManager(new GridLayoutManager(this, 3));
		if (mList.isEmpty()) {
			mList.add(new SportItem("健走", R.drawable.ic_history_walk_normal));
			mList.add(new SportItem("跑步", R.drawable.ic_history_run_normal));
			mList.add(new SportItem("骑行", R.drawable.ic_history_riding_normal));
		}
		mAdapter = new CustomPopupWindowRvAdapter(mList);
		mRv.setAdapter(mAdapter);
	}
}
