package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fangyuan.zhou on 2017/3/22 10:42
 */

public class CanvasTestView extends View {

	private Paint mPaint;

	public CanvasTestView(Context context) {
		this(context, null);
	}

	public CanvasTestView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLACK);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//如果还没有进行save就进行restore操作就会报出一个Underflow in restore - more restores than saves的错误
		canvas.save();  //保存当前canvas的状态
		canvas.translate(100, 100);
//		canvas.save();	//如果把这行注释解开, 那么下面canvas.restore()的时候就会恢复到canvas.translate(100, 100)后的状态
		canvas.drawCircle(50, 50, 50, mPaint);

		canvas.restore();  //恢复保存的Canvas的状态, 也就是恢复到执行save那行代码之前的状态
		mPaint.setColor(Color.RED);
		canvas.drawCircle(50, 50, 50, mPaint);

		drawText(canvas);
	}

	private void drawText(Canvas canvas) {

	}
}
