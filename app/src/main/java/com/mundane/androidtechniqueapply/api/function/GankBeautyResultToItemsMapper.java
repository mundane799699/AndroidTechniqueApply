package com.mundane.androidtechniqueapply.api.function;

import com.mundane.androidtechniqueapply.api.bean.GankBeauty;
import com.mundane.androidtechniqueapply.api.bean.GankBeautyResult;
import com.mundane.androidtechniqueapply.api.bean.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * Created by mundane on 2017/3/11 9:19
 */

public class GankBeautyResultToItemsMapper  implements Function<GankBeautyResult, List<Item>> {

	@Override
	public List<Item> apply(GankBeautyResult gankBeautyResult) throws Exception {
		List<GankBeauty> gankBeauties = gankBeautyResult.results;
		List<Item> items = new ArrayList<>(gankBeauties.size());
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		for (GankBeauty gankBeauty : gankBeauties) {
			Item item = new Item();
			try {
				Date date = inputFormat.parse(gankBeauty.createdAt);
				item.description = outputFormat.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
				item.description = "unknown date";
			}
			item.imageUrl = gankBeauty.url;
			items.add(item);

		}
		return items;
	}

	private GankBeautyResultToItemsMapper() {
	}

	public static GankBeautyResultToItemsMapper getInstance() {
		return INSTANCE;
	}

	private static GankBeautyResultToItemsMapper INSTANCE = new GankBeautyResultToItemsMapper();
}
