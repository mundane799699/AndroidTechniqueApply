package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * @author : mundane
 * @time : 2017/4/11 9:03
 * @description :
 * @file : CustomPopupWindow.java
 */

public class CustomPopupWindow extends PopupWindow {


	public CustomPopupWindow(Context context, View view) {
		super(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

}
