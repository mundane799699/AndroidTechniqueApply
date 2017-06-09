package com.mundane.androidtechniqueapply.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.model.SportItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : mundane
 * @time : 2017/4/11 10:03
 * @description :
 * @file : CustomPopupWindowRvAdapter.java
 */

public class CustomPopupWindowRvAdapter extends RecyclerView.Adapter<CustomPopupWindowRvAdapter.CustomPopupWindowRvViewHolder> {

	private List<SportItem> mList;

	public CustomPopupWindowRvAdapter(List<SportItem> list) {
		mList = list;
	}

	@Override
	public CustomPopupWindowRvAdapter.CustomPopupWindowRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custompopupwindow_rv, parent, false);
		return new CustomPopupWindowRvViewHolder(view);
	}

	@Override
	public void onBindViewHolder(CustomPopupWindowRvAdapter.CustomPopupWindowRvViewHolder holder, int position) {
		SportItem sportItem = mList.get(position);
		holder.tv.setText(sportItem.name);
		holder.iv.setImageResource(sportItem.imgRes);
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	static class CustomPopupWindowRvViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.iv)
		ImageView iv;
		@BindView(R.id.tv)
		TextView tv;
		public CustomPopupWindowRvViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

}
