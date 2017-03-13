package com.mundane.androidtechniqueapply.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mundane.androidtechniqueapply.api.service.FakeApi;
import com.mundane.androidtechniqueapply.api.service.GankService;
import com.mundane.androidtechniqueapply.api.service.ZhuangBiService;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mundane on 2017/3/10 14:52
 */

public class NetWork {

	private static ZhuangBiService sZhuangBiService;
	private static GankService sGankService;
	private static OkHttpClient okHttpClient = new OkHttpClient();
	private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
	private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();


//	https://www.zhuangbi.info/search?q=可爱
	public static ZhuangBiService getZhuangBiService() {
		if (sZhuangBiService == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.client(okHttpClient)
					.baseUrl("http://www.zhuangbi.info/")
					.addConverterFactory(gsonConverterFactory)
					.addCallAdapterFactory(rxJavaCallAdapterFactory)
					.build();
			sZhuangBiService = retrofit.create(ZhuangBiService.class);
		}
		return sZhuangBiService;
	}

//	分类数据: http://gank.io/api/data/数据类型/请求个数/第几页

//	数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
//	请求个数： 数字，大于0
//	第几页：数字，大于0

	//	http://gank.io/api/data/福利/10/1
	public static GankService getGankService() {
		if (sGankService == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.client(okHttpClient)
					.baseUrl("http://gank.io/api/")
					.addConverterFactory(gsonConverterFactory)
					.addCallAdapterFactory(rxJavaCallAdapterFactory)
					.build();
			sGankService = retrofit.create(GankService.class);
		}
		return sGankService;
	}

	private static FakeApi fakeApi;

	public static FakeApi getFakeApi() {
		if (fakeApi == null) {
			fakeApi = new FakeApi();
		}
		return fakeApi;
	}

}
