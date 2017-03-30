package com.mundane.androidtechniqueapply.ui.activitity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.view.widget.CircleLayerImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoundAvatarActivity extends AppCompatActivity {

	@BindView(R.id.iv)
	ImageView mIv;
	@BindView(R.id.iv_circle_layer_image_view)
	CircleLayerImageView mIvCircleLayerImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round_avatar);
		ButterKnife.bind(this);

		Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a4);
		mIv.setImageBitmap(
				roundBitmapByXfermode(
						srcBitmap,
						(int) getResources().getDimension(R.dimen.round_bitmap_width),
						(int) getResources().getDimension(R.dimen.round_bitmap_height),
						(int) getResources().getDimension(R.dimen.round_bitmap_radius)
				)
		);
	}

	/**
	 * 利用Xfermode绘制圆角图片
	 *
	 * @param srcBitmap 待处理图片
	 * @param outWidth  结果图片宽度，一般为控件的宽度
	 * @param outHeight 结果图片高度，一般为控件的高度
	 * @param radius    圆角半径大小
	 * @return 结果图片
	 */
	private Bitmap roundBitmapByXfermode(Bitmap srcBitmap, int outWidth, int outHeight, int radius) {
		if (srcBitmap == null) {
			throw new NullPointerException("Bitmap can't be null");
		}

		// 等比例缩放拉伸
		float widthScale = outWidth * 1.0f / srcBitmap.getWidth();
		float heightScale = outHeight * 1.0f / srcBitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.setScale(widthScale, heightScale);
		Bitmap transformedSrcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);

		// 初始化目标bitmap
		Bitmap dstBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(dstBitmap);
		canvas.drawARGB(0, 0, 0, 0);

		Paint paint = new Paint();
		paint.setAntiAlias(true);

		RectF rectF = new RectF(0f, 0f, outWidth, outHeight);

		// 在画布上绘制圆角图
//		canvas.drawRoundRect(rectF, radius, radius, paint);
		canvas.drawCircle(outWidth / 2, outHeight / 2, outWidth / 2, paint);//src 圆形

		// 设置叠加模式
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

		// 在画布上绘制原图片
		Rect ret = new Rect(0, 0, outWidth, outHeight);
		canvas.drawBitmap(transformedSrcBitmap, ret, ret, paint);//dst 原图

		return dstBitmap;
	}
}
