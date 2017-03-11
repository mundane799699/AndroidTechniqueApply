package com.mundane.androidtechniqueapply.api.service;

import com.mundane.androidtechniqueapply.api.bean.ZhuangBiEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mundane on 2017/3/10 14:58
 */



public interface ZhuangBiService {
	@GET("search")
	Observable<List<ZhuangBiEntity>> search(@Query("q") String value);//https://www.zhuangbi.info/search?q=可爱
}
