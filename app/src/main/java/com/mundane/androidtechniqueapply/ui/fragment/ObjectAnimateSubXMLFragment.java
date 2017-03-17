package com.mundane.androidtechniqueapply.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.mundane.androidtechniqueapply.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObjectAnimateSubXMLFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectAnimateSubXMLFragment extends Fragment {


	@BindView(R.id.btn_alpha)
	Button mBtnAlpha;
	@BindView(R.id.btn_translate)
	Button mBtnTranslate;
	@BindView(R.id.btn_color)
	Button mBtnColor;
	@BindView(R.id.btn_rotate)
	Button mBtnRotate;
	@BindView(R.id.btn_set)
	Button mBtnSet;
	@BindView(R.id.iv)
	ImageView mIv;

	public ObjectAnimateSubXMLFragment() {
		// Required empty public constructor
	}

	public static ObjectAnimateSubXMLFragment newInstance() {
		ObjectAnimateSubXMLFragment fragment = new ObjectAnimateSubXMLFragment();
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
		View view = inflater.inflate(R.layout.fragment_object_animate_sub_xml, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@OnClick({R.id.btn_alpha, R.id.btn_translate, R.id.btn_color, R.id.btn_rotate, R.id.btn_set})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_alpha:
				alpha();
				break;
			case R.id.btn_translate:
				//	使用View的anim方法实现, 不过需要SDK11
				//	此后在SDK12，SDK16又分别添加了withStartAction和withEndAction用于在动画前，和动画后执行一些操作。当然也可以.setListener(listener)等操作
				translate();
				break;
			case R.id.btn_color:
				color();
				break;
			case R.id.btn_rotate:
				break;
			case R.id.btn_set:
				scale();
				break;
		}
	}

	private void color() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			//	前两位ff表示透明度, ff表示完全不透明
			ObjectAnimator color = ObjectAnimator.ofArgb(mIv, "backgroundColor", 0xff000000, 0xffffffff).setDuration(2000);
// 	第二个参数主要是看第一个参数target有没有set第二个参数的方法, 比如mIv.setBackgroundColor(), 参数类型也是int的, 而并没有mIv.setBackground()方法
//			ObjectAnimator color = ObjectAnimator.ofArgb(mIv, "background", 0xff000000, 0xffffffff).setDuration(2000);
			color.start();
		} else {
			
		}
	}

	private void translate() {
//		mIv.animate().y(mIv.getY()+mIv.getHeight()/2).setDuration(1000).setInterpolator(new DecelerateInterpolator(2)).start();
		//	这个y指的是它在父布局中的y坐标
		mIv.animate().y(0).setDuration(1000).setInterpolator(new DecelerateInterpolator(2)).start();
	}

	private void scale() {
		Animator animator = AnimatorInflater.loadAnimator(getContext(), R.animator.anim_scale);
		animator.setTarget(mIv);
		animator.start();
	}

	private void alpha() {
		Animator animator = AnimatorInflater.loadAnimator(getContext(), R.animator.anim_alpha);
		animator.setTarget(mIv);
		animator.start();
	}
}
