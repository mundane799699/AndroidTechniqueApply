package com.mundane.androidtechniqueapply.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mundane.androidtechniqueapply.api.bean.HttpResult;
import com.mundane.androidtechniqueapply.api.bean.Subject;
import com.mundane.androidtechniqueapply.api.exception.ApiException;
import com.mundane.androidtechniqueapply.api.service.MovieService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mundane on 2017/3/7 11:12
 */

public class HttpMethods {
	private static class SingletonHolder {
		private static final HttpMethods INSTANCE = new HttpMethods();
	}

	public static HttpMethods getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static final int DEFAULT_TIMEOUT = 5;

	public static final String BASE_URL = "https://api.douban.com/v2/movie/";

	private Retrofit retrofit;

	private MovieService movieService;

	private HttpMethods() {
		OkHttpClient okHttpClient = new OkHttpClient
				.Builder()
				.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
				.build();
		retrofit = new Retrofit.Builder()
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.baseUrl(BASE_URL)
				.build();
		movieService = retrofit.create(MovieService.class);

	}

	public void getTopMovie(Observer<List<Subject>> observer, int start, int count) {
		movieService.getTopMovie(start, count)
				.map(new HttpResultFunc<List<Subject>>())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(observer);


	}

	private class HttpResultFunc<T> implements Function<HttpResult<T>, T> {

		@Override
		public T apply(HttpResult<T> httpResult) throws Exception {
			if (httpResult.count != 10) {
				throw new ApiException(100);
			}
			return httpResult.subjects;
		}
	}


}
