package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fangyuan.zhou on 2017/3/21 11:11
 */

public class CustomFlowLayout extends ViewGroup {

	public CustomFlowLayout(Context context) {
		this(context, null);
	}

	public CustomFlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	//	储存所有行的高度
	private List<Integer> allHeights = new ArrayList<>();
	//	储存所有行的View的集合
	private List<List<View>> allViews = new ArrayList<>();

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int width = this.getWidth();
		//	设置每行的行高和行宽
		int lineWidth = 0;
		int lineHeight = 0;
		//	创建每一行的子视图的集合, 用来存放每一行的所有子视图
		List<View> lineList = new ArrayList<>();

		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);

			int childWidth = childView.getMeasuredWidth();
			int childHeight = childView.getMeasuredHeight();

			MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();

			if (lineWidth + childWidth + mp.rightMargin + mp.leftMargin <= width) {
				//	不换行, 则把每行的子View加到行的集合中
				lineList.add(childView);
				lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
				//	每一行中取最高的那个
				lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
			} else {//	换行
				//	把"每行的子View的集合"加到所有的集合中, 把行高加到行高的集合中, allViews:这里面的每一个元素都表示每一行中的所有子View
				allViews.add(lineList);
				allHeights.add(lineHeight);

				//换行以后需要执行的操作
				lineList = new ArrayList<>();
//				lineList.clear();
				//	把新的一行的子View加到行的集合中
				lineList.add(childView);
				lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
				lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
			}

			if (i == childCount - 1) {
				allViews.add(lineList);
				allHeights.add(lineHeight);
			}
		}
		//	都表示行数, 所以两者应该相等
		Log.d(TAG, "allViews.size() = " + allViews.size() + ", allHeights.size() = " + allHeights.size());

		//	遍历集合元素, 调用每一个view的layout方法
		int x = 0;//	用来记录每一行中每个子View的left
		int y = 0;//	用来记录每一行子View的top
		//	allViews:这里面的每一个元素都表示每一行中的所有子View
		for (int i = 0; i < allViews.size(); i++) {
			List<View> lineViews = allViews.get(i);
			for (int j = 0; j < lineViews.size(); j++) {
				View childView = lineViews.get(j);
				MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();
				//	得到l, t, r, b
				int left = x + mp.leftMargin;
				int top = y + mp.topMargin;
				int right = left + childView.getMeasuredWidth();
				int bottom = top + childView.getMeasuredHeight();
				childView.layout(left, top, right, bottom);
				x += childView.getMeasuredWidth() + mp.leftMargin + mp.rightMargin;
			}

			//	换行了
			x = 0;
			y += allHeights.get(i);
		}
	}

	private final String TAG = getClass().getSimpleName();

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//	获取宽度和高度
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		//	获取宽和高各自的设置模式
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		//布局的宽和高
		int width = 0;
		int height = 0;

		//行宽和行高
		int lineWidth = 0;
		int lineHeight = 0;

		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			measureChild(childView, widthMeasureSpec, heightMeasureSpec);
			int childWidth = childView.getMeasuredWidth();
			int childHeight = childView.getMeasuredHeight();
			MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();

			//	lineWidth表示每一行之前累加的子View的总宽度, 每次换行都要重新赋值
			if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin <= widthSize) {//	不需要换行, 行宽要增加
				lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
				//	行高是最高的那一行的高度
				lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
			} else {//	需要换行了
				//	取所有历史行宽中的最大值作为布局宽度
				width = Math.max(lineWidth, width);
				height += lineHeight;

				//重新赋值
				lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
				lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
			}
			//	因为每次换行的时候, height加的是上一行的高度, 所以到最后一行的时候, 要加上最后一行的高度
			//	比如说, 只有一行的时候, 没有换行, 于是就不会走else里面的逻辑, 所以height等于0, 所以要加上最后一行的高度
			if (i == childCount - 1) {
				width = Math.max(lineWidth, width);
				height += lineHeight;
			}
		}

		Log.d(TAG, "width = " + width + ", height = " + height);
		Log.d(TAG, "widthSize = " + widthSize + ", heightSize = " + heightSize);
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		MarginLayoutParams mp = new MarginLayoutParams(getContext(), attrs);
		return mp;
	}

}
