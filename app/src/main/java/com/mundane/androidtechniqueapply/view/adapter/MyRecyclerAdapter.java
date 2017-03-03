package com.mundane.androidtechniqueapply.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mundane.androidtechniqueapply.constant.Constant;
import com.mundane.androidtechniqueapply.model.ActivityModel;

/**
 * Created by mundane on 2017/2/25 9:17
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

	private ActivityModel[] mArr;
	private Context mContext;
	private int mTextViewHeight;

	public MyRecyclerAdapter(ActivityModel[] arr, Context context) {
		mArr = arr;
		mContext = context;
		mTextViewHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
	}

	@Override
	public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		TextView textView = new TextView(mContext);
		textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTextViewHeight));
		textView.setGravity(Gravity.CENTER);
		return new MyViewHolder(textView);
	}


	@Override
	public void onBindViewHolder(final MyRecyclerAdapter.MyViewHolder holder, int position) {
		final ActivityModel activityModel = mArr[position];
		final String text = activityModel.text;
		final String action = activityModel.action;
		holder.tv.setText(text);
		if (action != null) {
			holder.tv.setBackgroundResource(android.R.color.holo_green_dark);
			holder.tv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(action);
					intent.putExtra(Constant.KEY_INTENT, text);
					mContext.startActivity(intent);
				}
			});
		} else {
			holder.tv.setBackgroundResource(android.R.color.transparent);
			holder.tv.setOnClickListener(null);
		}

	}

	@Override
	public int getItemCount() {
		return mArr.length;
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView tv;

		public MyViewHolder(TextView itemView) {
			super(itemView);
			tv = itemView;
		}
	}
}
