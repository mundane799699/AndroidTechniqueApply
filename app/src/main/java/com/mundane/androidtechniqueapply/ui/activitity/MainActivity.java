package com.mundane.androidtechniqueapply.ui.activitity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.model.ActivityModel;
import com.mundane.androidtechniqueapply.view.adapter.MyRecyclerAdapter;
import com.mundane.androidtechniqueapply.view.recyclerviewdecoration.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.rv)
	RecyclerView mRv;
	@BindView(R.id.activity_main)
	RelativeLayout mActivityMain;
	private MyRecyclerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		// TODO: 2017/2/28 MainAcitivity以后要在上面加搜索和搜索出来的高亮
//		ActionBar actionBar = getSupportActionBar();
//		actionBar.setTitle("自定义View合集");
//		actionBar.setDisplayHomeAsUpEnabled(true);
		mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRv.setHasFixedSize(true);
		mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		ActivityModel[] arr = {
				new ActivityModel("仿新浪微博发微博时候可以拖动图片排序", null),
				new ActivityModel("多点触控技术", null),
				new ActivityModel("竖直的ViewPager", "VerticalViewPagerActivity"),
				new ActivityModel("横向ViewPager嵌套竖直ViewPager", "HorizontalViewPagerActivity"),
				new ActivityModel("测试ontouchevent", "TestOntouchEventActivity"),
				new ActivityModel("ViewPager中嵌套ListView", "ListViewInViewPagerActivity"),
				new ActivityModel("外部拦截法解决ViewGroup嵌套ListView滑动冲突", "ListViewInViewGroupActivity"),
				new ActivityModel("内部拦截法解决ViewGroup嵌套ListView滑动冲突", "ListViewInViewGroupActivity2"),
				new ActivityModel("外部拦截法解决ScrollView嵌套ListView滑动冲突", "ListViewInScrollViewActivity"),
				new ActivityModel("仿网易云音乐LoaddingActivity到MainActivity过渡动画", "LikeNetEaseMusicAnimationActivity"),
				new ActivityModel("卫星菜单", "ArcMenuActivity"),
				//ScrollView嵌套ListView滑动冲突解决:判断触摸的是否是ListView, 如果是, 由ListView来消费这个事件, 如果不是, 给ScrollView
				new ActivityModel("内部拦截法解决ScrollView嵌套ListView滑动冲突", null),
				new ActivityModel("仿京东以及小鹿美美详情页下拉查看图文详情", null),
				new ActivityModel("贝塞尔曲线做的开场动画", null),
				new ActivityModel("仿酷安的各种共享动画", null),
				new ActivityModel("动画练习合集", "AnimationActivity"),
				new ActivityModel("service、IntentService相关 + 音乐播放器", null),
				new ActivityModel("视频播放相关", null),
				new ActivityModel("双向拖动的seekbar", "DoubleSeekBarActivity"),
				new ActivityModel("自定义ViewPagerIndicator", null),
				new ActivityModel("饿了么购物页面", null),
				new ActivityModel("canvas的demo", "CanvasTestActivity"),
				//把里面分为帧动画, 属性动画, 值动画, 补间动画, Viewcompat动画,
				//	还有mAppBar.animate().translationY(mIsHidden ? 0 : -mAppBar.getHeight()).setInterpolator(new DecelerateInterpolator(2)).start(); 这种
				//	上面那段代码抄自meizhi
				// 并且每个分为代码实现和xml实现
				new ActivityModel("仿QQ空间以及小鹿美美顶部tab随滚动透明度变化", null),
				new ActivityModel("演示MotionEvent.ACTION_CANCEL", "ActionCancelTestActivity"),
				new ActivityModel("仿微信朋友圈下拉刷新", null),
				new ActivityModel("仿知乎主页", "LikeZhiHuActivity"),
				new ActivityModel("retrofit和rxjava结合sample", "RxjavaAndRetrofitActivity"),
				new ActivityModel("仿微信的switchButton", "LikeWeiXinStateButtonActivity"),
				new ActivityModel("流式布局", "FlowLayoutActivity"),
				new ActivityModel("简易的PhotoView", null),
				new ActivityModel("简易的ImageLoader", null),
				new ActivityModel("类似小鹿美美的嵌套滚动机制", "NestedScrollingActivity"),
				new ActivityModel("仿美团吸顶", null),
				new ActivityModel("可拖拽的GridLayout", null),
				new ActivityModel("不使用Integer.MAX_VALUE的banner", "NotIntegerMaxValueBannerActivity"),
				new ActivityModel("自定义下拉刷新", "CustomPullToRefreshActivity"),
				new ActivityModel("又一个解决滑动冲突的demo", null),//可以做成解决滑动冲突合集
				new ActivityModel("多重ViewPager嵌套解决滑动冲突", null),
				new ActivityModel("仿微信底部滑动变色的tab", "LikeWeChatMainActivity"),
				new ActivityModel("圆形头像的多种实现方式", "RoundAvatarActivity"),
				new ActivityModel("类似微信中ViewPager的fragment懒加载", null),
				new ActivityModel("调用系统相机拍照 + 6.0权限示例", "PermissionActivity"),
				new ActivityModel("检查更新点击下载通知栏进度", null),
				new ActivityModel("仿饿了么购物车动画", null),
				new ActivityModel("搜索, 历史搜索和高亮搜索", null),
				new ActivityModel("仿微博和闲鱼底部弹出动画", null),
				new ActivityModel("仿QQ弹出的popupWindow", null),
				new ActivityModel("自定义ScrollView", null),
				new ActivityModel("仿QQ右上角弹出框", null),
				new ActivityModel("9patch图片", null),
				new ActivityModel("notification", null)
		};
		mAdapter = new MyRecyclerAdapter(arr, this);
		mRv.setAdapter(mAdapter);
	}
}
