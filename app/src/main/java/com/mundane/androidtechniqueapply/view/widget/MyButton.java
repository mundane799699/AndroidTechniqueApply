package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by mundane on 2017/2/25 13:46
 */

public class MyButton extends Button {
	public MyButton(Context context) {
		super(context);
		init(context);
	}

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);//当把这行改为this(context, 0)的时候, xml里的button将会失去默认的style
		//super(context, attrs)相当于在xml里设置style="@android:style/Widget.Button"
		//详细的可以点进super(context, attrs)看源码
		init(context);
	}

	public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private Context mContext;

	private void init(Context context) {
		mContext = context;
	}

	private final String TAG = getClass().getSimpleName();

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Toast.makeText(mContext, "Button", Toast.LENGTH_SHORT).show();
		return false;
	}
}
