package com.mundane.androidtechniqueapply.view.widgets;

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


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Toast.makeText(mContext, "MyTouchView", Toast.LENGTH_SHORT).show();
		return super.onTouchEvent(event);
	}
}
