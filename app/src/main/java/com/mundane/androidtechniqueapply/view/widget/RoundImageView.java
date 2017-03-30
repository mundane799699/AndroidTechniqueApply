package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by fangyuan.zhou on 2017/3/27 11:55
 */

public class RoundImageView extends ImageView {

	private Paint paint;

	public RoundImageView(Context context) {
		super(context);
		init(context);
	}

	public RoundImageView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (null != drawable) {
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			//核心代码
			Bitmap b = getCircleBitmap(bitmap);
			paint.reset();
			canvas.drawBitmap(b, 0, 0, paint);
		} else {
			super.onDraw(canvas);
		}
	}

	private Bitmap getCircleBitmap(Bitmap bitmap){
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
		float radius = Math.min(bitmap.getHeight() / 2, bitmap.getWidth() / 2);
		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, radius, paint);//dst
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);//将图片画出来, src
		return output;
	}
}
