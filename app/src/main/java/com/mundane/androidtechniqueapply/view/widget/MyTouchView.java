package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by mundane on 2017/2/25 13:43
 */

public class MyTouchView extends FrameLayout {
	public MyTouchView(Context context) {
		this(context, null);
	}

	public MyTouchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private Context mContext;

	private void init(Context context) {
		mContext = context;
	}


	//如果一个View的onTouchEvent返回了false,那么它父容器的onTouchEvent方法将会被调用
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Toast.makeText(mContext, "MyTouchView", Toast.LENGTH_SHORT).show();
		return super.onTouchEvent(event);
	}
}
