package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by fangyuan.zhou on 2017/3/27 10:13
 */

public class CircleLayerImageView extends ImageView {

	private Paint mPaint;

	public CircleLayerImageView(Context context) {
		super(context);
		init(context);
	}



	public CircleLayerImageView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CircleLayerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.WHITE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Drawable drawable = getDrawable();
		int intrinsicWidth = drawable.getIntrinsicWidth();
		int intrinsicHeight = drawable.getIntrinsicHeight();

		//	获取宽度和高度
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		//	获取宽和高各自的设置模式
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);


	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();

		//创建跟imageview相同宽高的遮罩层
		int min = Math.min(width, height);
		Bitmap maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas maskCanvas = new Canvas(maskBitmap);

		//遮罩层颜色
		int maskColor;
		Drawable background = getBackground();
		if (background != null && background instanceof ColorDrawable) {
			maskColor = ((ColorDrawable) background).getColor();
		} else {
			maskColor = Color.RED;
		}
		maskCanvas.drawColor(maskColor);
		canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);
		canvas.save();
		canvas.drawBitmap(maskBitmap, 0, 0, mPaint);//先画一层红色的dst
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));//取src中交集以外的部分
		// width / 1^2的意思
		canvas.drawCircle(width >> 1, height >> 1, min >> 1, mPaint);//圆,  src
		// 这句代码一定要加, 否则切换到别的应用再切换回来就会变黑
		mPaint.setXfermode(null);
		canvas.restore();
	}
}
