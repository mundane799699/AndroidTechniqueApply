package com.mundane.androidtechniqueapply.api.service;

import com.mundane.androidtechniqueapply.api.bean.HttpResult;
import com.mundane.androidtechniqueapply.api.bean.Subject;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mundane on 2017/3/7 9:40
 */

public interface MovieService {
	@GET("top250")
	Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
	//Observable: 可观察者，即被观察者
}
