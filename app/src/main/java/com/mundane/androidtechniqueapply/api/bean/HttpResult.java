package com.mundane.androidtechniqueapply.api.bean;

/**
 * Created by mundane on 2017/3/7 11:50
 */

public class HttpResult<T> {
	public int count;
	public int start;
	public int total;
	public String title;
	public T subjects;
}
