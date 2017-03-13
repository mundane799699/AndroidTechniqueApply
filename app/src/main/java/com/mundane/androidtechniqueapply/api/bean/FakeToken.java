package com.mundane.androidtechniqueapply.api.bean;

/**
 * Created by fangyuan.zhou on 2017/3/13 14:03
 */

public class FakeToken {
	public String token;
	public boolean expired;

	public FakeToken() {
	}

	public FakeToken(boolean expired) {
		this.expired = expired;
	}
}
