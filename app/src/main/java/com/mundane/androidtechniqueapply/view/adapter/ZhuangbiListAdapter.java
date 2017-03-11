package com.mundane.androidtechniqueapply.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.api.bean.ZhuangBiEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mundane on 2017/3/10 15:15
 */

public class ZhuangbiListAdapter extends RecyclerView.Adapter {

	private List<ZhuangBiEntity> mList;

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
		return new DebounceViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		DebounceViewHolder debounceViewHolder = (DebounceViewHolder) holder;
		ZhuangBiEntity zhuangBiEntity = mList.get(position);
		Glide.with(holder.itemView.getContext()).load(zhuangBiEntity.image_url).into(debounceViewHolder.mImageIv);
		debounceViewHolder.mDescriptionTv.setText(zhuangBiEntity.description);
	}

	@Override
	public int getItemCount() {
		return mList == null ? 0 : mList.size();
	}

	public void setImages(List<ZhuangBiEntity> list) {
		mList = list;
		notifyDataSetChanged();
	}

	static class DebounceViewHolder extends RecyclerView.ViewHolder{
		@BindView(R.id.imageIv)
		ImageView mImageIv;
		@BindView(R.id.descriptionTv)
		TextView mDescriptionTv;

		DebounceViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
