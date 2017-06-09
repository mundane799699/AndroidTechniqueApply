package com.mundane.androidtechniqueapply.model;

import android.support.annotation.DrawableRes;

/**
 * @author : mundane
 * @time : 2017/4/11 10:18
 * @description :
 * @file : SportItem.java
 */

public class SportItem {
	@DrawableRes
	public int imgRes;
	public String name;

	public SportItem(String name, @DrawableRes int imgRes) {
		this.name = name;
		this.imgRes = imgRes;
	}
}
