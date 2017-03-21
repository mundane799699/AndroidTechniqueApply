package com.mundane.androidtechniqueapply.ui.activitity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.base.BaseActionBarActivity;
import com.mundane.androidtechniqueapply.utils.Utils;
import com.mundane.androidtechniqueapply.view.widget.CustomFlowLayout;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlowLayoutActivity extends BaseActionBarActivity {


	@BindView(R.id.flow_layout)
	CustomFlowLayout mFlowLayout;
	private String[] datas = new String[]{
			"新手计划", "乐享活系列90天计划", "钱包",
			"30天理财计划(加息2%)",
			"林业局投资商业经营与大捞一笔",
			"中学老师购买车辆", "屌丝下海经商计划",
			"新西游影视拍", "Java培训老师自己周转",
			"HelloWorld", "C++-C-ObjectC-java",
			"Android vs ios", "算法与数据结构",
			"JNI与NDK", "team working"};

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flow_layout);
		ButterKnife.bind(this);

		for (int i = 0; i < datas.length; i++) {
			final TextView tv = new TextView(this);
			tv.setText(datas[i]);

//            相当于布局文件中的：
//            android:layout_width="wrap_content"
//            android:layout_height="wrap_content"
//            必须要写
			ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(layoutParams);
			tv.setLayoutParams(mp);
			//  -->太挤了，且字体太小

			//设置边距
			mp.leftMargin = Utils.dp2px(this, 5);
			mp.rightMargin = Utils.dp2px(this, 5);
			mp.topMargin = Utils.dp2px(this, 5);
			mp.bottomMargin = Utils.dp2px(this, 5);

			tv.setTextSize(Utils.dp2px(this, 5));

			Random random = new Random();
			//设置背景颜色
			int red = random.nextInt(180);
			int green = random.nextInt(180);
			int blue = random.nextInt(180);
//方式一：使用GradentDrawable类，相当于 <=> shape 中的<gradent> 标签
//            //指定颜色和圆角半径
//            tv.setBackground(JMDrawableUtils.getDrawable(Color.rgb(red,green,blue),JMUiUtils.dp2px(5)));

//方式二：使用StateListDrawable类，相当于 <=> 我们经常使用的selector 标签
			//给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
			Drawable normalDrawable = Utils.getDrawable(this, Color.rgb(red, green, blue), Utils.dp2px(this, 5));
			Drawable pressDrawable = Utils.getDrawable(this, Color.WHITE, Utils.dp2px(this, 5));
			tv.setBackground(Utils.getSelector(normalDrawable, pressDrawable));

			//使用方式二背景颜色可以改变，但是发现点击时，背景没有变为白色
			//这是因为 TextView 默认是不可点的
			//解决 TextView默认不可点的方法有如下两种：
			//①设置为可点击的
//            tv.setClickable(true);

			//②当设置点击事件时，textview就默认为可点的啦
			tv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(FlowLayoutActivity.this, tv.getText(), Toast.LENGTH_SHORT).show();
				}
			});


			//背景框把字体包的太紧了
			//提供内边距
			int padding = Utils.dp2px(this, 5);
			tv.setPadding(padding, padding, padding, padding);

			//添加视图到我们的自定义JMFlowLayout中
			mFlowLayout.addView(tv);


		}
	}
}
