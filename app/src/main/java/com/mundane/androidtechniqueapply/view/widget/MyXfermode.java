package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mundane.androidtechniqueapply.R;

/**
 * Created by fangyuan.zhou on 2017/3/27 11:27
 */

public class MyXfermode extends View {
	private int width;
	private int height;
	private Paint mPaint;
	private Bitmap mbitmap;
	private Bitmap moutbitmap;
	public MyXfermode(Context context) {
		super(context);

	}
	public MyXfermode(Context context, AttributeSet attrs) {
		super(context, attrs);

		initBitmap();
	}
	public void initBitmap(){
		//禁用硬件加速器，因为有些硬件加速器不支持
		setLayerType(LAYER_TYPE_SOFTWARE, null);
		//设置抗锯齿
		mPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.YELLOW);
		mbitmap= BitmapFactory.decodeResource(getResources(), R.drawable.a1);
		moutbitmap= Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Log.d("图片信息", "宽: " + mbitmap.getWidth()+ ", 高: "+mbitmap.getHeight());
		//以后的绘制都将显示在moutbitmap上面
		Canvas canvas = new Canvas(moutbitmap);

		//Dst
		canvas.drawCircle(mbitmap.getWidth() / 2, mbitmap.getHeight() / 2, mbitmap.getWidth() / 2, mPaint);
		PorterDuffXfermode mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
		mPaint.setXfermode(mode);
		//Src
		canvas.drawBitmap(mbitmap, 0, 0, mPaint);

	}
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(moutbitmap, 0, 0, null);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
	}
}
