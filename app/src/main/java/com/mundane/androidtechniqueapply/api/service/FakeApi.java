package com.mundane.androidtechniqueapply.api.service;

import android.support.annotation.NonNull;

import com.mundane.androidtechniqueapply.api.bean.FakeThing;
import com.mundane.androidtechniqueapply.api.bean.FakeToken;

import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by fangyuan.zhou on 2017/3/13 14:02
 */

public class FakeApi {
	Random random = new Random();

	public Observable<FakeToken> getFakeToken(@NonNull String fakeAuth) {
		return Observable
				.just(fakeAuth)
				.map(new Function<String, FakeToken>() {
					@Override
					public FakeToken apply(String s) throws Exception {
						int fakeNetWorkTimeCost = random.nextInt(500) + 500;
						try {
							Thread.sleep(fakeNetWorkTimeCost);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						FakeToken fakeToken = new FakeToken();
						fakeToken.token = createToken();
						return fakeToken;
					}
				});
	}

	public Observable<FakeThing> getFakeData(FakeToken fakeToken) {
		return Observable
				.just(fakeToken)
				.map(new Function<FakeToken, FakeThing>() {
					@Override
					public FakeThing apply(FakeToken fakeToken) throws Exception {
						int fakeNetWorkTimeCost = random.nextInt(500) + 500;
						try {
							Thread.sleep(fakeNetWorkTimeCost);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (fakeToken.expired) {
							throw new IllegalArgumentException("Token expired!");
						}
						FakeThing fakeData = new FakeThing();
						fakeData.id = (int) (System.currentTimeMillis() % 10000);
						fakeData.name = "FAKE_USER_" + fakeData.id;
						return fakeData;
					}
				});
	}

	private String createToken() {

		return "fake_token_" + System.currentTimeMillis() % 10000;
	}
}
