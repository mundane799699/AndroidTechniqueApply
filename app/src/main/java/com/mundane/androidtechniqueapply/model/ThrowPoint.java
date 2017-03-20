package com.mundane.androidtechniqueapply.model;

/**
 * Created by fangyuan.zhou on 2017/3/20 14:16
 */

public class ThrowPoint {
	private float x;
	private float y;
	//	y = a*X*X;	经过(0, 0)点和(960, 1800)这两个点
	private final float a = (float) (1800f / (Math.pow(960, 2)));

	public ThrowPoint(float x) {
		this.x = x;
		this.y = a * ((float) Math.pow(x, 2));
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		this.y = a * ((float) Math.pow(x, 2));
	}

	public float getY() {
		return y;
	}

}
