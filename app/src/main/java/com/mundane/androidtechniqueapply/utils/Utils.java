package com.mundane.androidtechniqueapply.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Author:    Diamond_Lin
 * Version    V1.0
 * Date:      16/10/17 上午11:28
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/10/17      Diamond_Lin            1.0                    1.0
 * Why & What is modified:
 */
public class Utils {
    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

	//提供一个指定颜色和圆角半径的Drawable对象
	public static GradientDrawable getDrawable(Context context, int rgb, float radius) {
		GradientDrawable gradientDrawable = new GradientDrawable();
		gradientDrawable.setShape(GradientDrawable.RECTANGLE);
		gradientDrawable.setColor(rgb);  //填充颜色
//		gradientDrawable.setGradientType(GradientDrawable.RECTANGLE); //shape矩形
		gradientDrawable.setCornerRadius(radius);  //四周圆角半径
		gradientDrawable.setStroke(Utils.dp2px(context, 1), rgb); //边框厚度与颜色
		return gradientDrawable;
	}

	public static StateListDrawable getSelector(Drawable normalDrawable, Drawable pressDrawable) {
		StateListDrawable stateListDrawable = new StateListDrawable();
		//给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
		stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressDrawable);
		stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
		//设置默认状态
		stateListDrawable.addState(new int[]{}, normalDrawable);
		return stateListDrawable;
	}



}
