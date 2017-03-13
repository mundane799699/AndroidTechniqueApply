package com.mundane.androidtechniqueapply.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.api.bean.Item;
import com.mundane.androidtechniqueapply.api.bean.ZhuangBiEntity;
import com.mundane.androidtechniqueapply.api.function.GankBeautyResultToItemsMapper;
import com.mundane.androidtechniqueapply.http.NetWork;
import com.mundane.androidtechniqueapply.view.adapter.ItemListAdapter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class RxjavaZipFragment extends Fragment {

    @BindView(R.id.btn_load)
    Button             mBtnLoad;
    @BindView(R.id.rv)
    RecyclerView       mRv;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ItemListAdapter mItemListAdapter;


    public RxjavaZipFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance() {
        RxjavaZipFragment fragment = new RxjavaZipFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxjava_zip, container, false);
        ButterKnife.bind(this, view);

        mRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mItemListAdapter = new ItemListAdapter();
        mRv.setAdapter(mItemListAdapter);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        mSwipeRefreshLayout.setEnabled(false);

        return view;
    }


    @OnClick(R.id.btn_load)
    public void onClick() {
        mSwipeRefreshLayout.setRefreshing(true);
        Observable
            .zip(
                NetWork.getGankService().getBeauties(200, 1).map(GankBeautyResultToItemsMapper.getInstance()),
                NetWork.getZhuangBiService().search("装逼"),
                new BiFunction<List<Item>, List<ZhuangBiEntity>, List<Item>>() {
                @Override
                public List<Item> apply(List<Item> gankItems, List<ZhuangBiEntity> zhuangBiItems) throws Exception {
                    List<Item> items = new ArrayList<>();
                    for (int i = 0; i < (gankItems.size()-1)/2 && i < zhuangBiItems.size(); i++) {
                        items.add(gankItems.get(i * 2));
                        items.add(gankItems.get(i * 2 + 1));
                        Item item = new Item();
                        ZhuangBiEntity zhuangBiItem = zhuangBiItems.get(i);
                        item.imageUrl = zhuangBiItem.image_url;
                        item.description = zhuangBiItem.description;
                        items.add(item);
                    }
                    return items;
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<Item>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }


                @Override
                public void onNext(List<Item> items) {
                    mItemListAdapter.setImages(items);
                }


                @Override
                public void onError(Throwable e) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
                }


                @Override
                public void onComplete() {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (!mSwipeRefreshLayout.isEnabled()) {
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
            });
    }
}
