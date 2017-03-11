package com.mundane.androidtechniqueapply.api.service;

import com.mundane.androidtechniqueapply.api.bean.GankBeauty;
import com.mundane.androidtechniqueapply.api.bean.GankBeautyResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mundane on 2017/3/10 20:14
 */

public interface GankService {
	//	分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
	//	http://gank.io/api/data/福利/10/1
	@GET("data/福利/{number}/{page}")
	Observable<GankBeautyResult<List<GankBeauty>>> getBeauties(@Path("number") int number, @Path("page") int page);
}
