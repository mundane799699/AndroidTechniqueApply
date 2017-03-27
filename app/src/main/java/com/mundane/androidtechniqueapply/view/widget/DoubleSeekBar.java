package com.mundane.androidtechniqueapply.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.mundane.androidtechniqueapply.R;

import java.util.List;

/**
 * Created by fangyuan.zhou on 2017/3/22 17:33
 */

public class DoubleSeekBar extends View {

	private Paint seekTextPaint;
	private Paint seekBgPaint;
	private Paint seekBallStartPaint;
	private Paint seekPbPaint;
	private Paint seekBallEndPaint;
	private Paint seekBallStrokePaint;
	private int mViewHeight;
	private int mViewWidth;
	private int seekBallRadio;
	private int seekBallY;
	//	左边那个圆的圆心的x坐标
	private int leftSeekBallX;
	//	右边那个圆的圆心的x坐标
	private int rightSeekBallX;
	private RectF seekBgRectF;//	绿色条底下的灰白色的那一段
	private RectF seekPbRectF;//	中间的绿色的那一段
	private int downX;
	private BallType currentMovingType;

	public DoubleSeekBar(Context context) {
		this(context, null);
	}

	public DoubleSeekBar(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DoubleSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private int seekBgColor;
	private int seekPbColor;
	private int seekBallSolidColor;
	private int seekBallStrokeColor;
	private int seekTextColor;
	private int seekTextSize;

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
		TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DoubleSeekBar, defStyleAttr, R.style.def_doubleseekbar);
		int indexCount = ta.getIndexCount();
		for (int i = 0; i < indexCount; i++) {
			int attr = ta.getIndex(i);
			switch (attr) {
				case R.styleable.DoubleSeekBar_seek_bg_color:
					seekBgColor = ta.getColor(attr, 0);
					break;
				case R.styleable.DoubleSeekBar_seek_pb_color:
					seekPbColor = ta.getColor(attr, 0);
					break;
				case R.styleable.DoubleSeekBar_seek_ball_solid_color:
					seekBallSolidColor = ta.getColor(attr, 0);
					break;
				case R.styleable.DoubleSeekBar_seek_ball_stroke_color:
					seekBallStrokeColor = ta.getColor(attr, 0);
					break;
				case R.styleable.DoubleSeekBar_seek_text_color:
					seekTextColor = ta.getColor(attr, Color.BLACK);
					break;
				case R.styleable.DoubleSeekBar_seek_text_size:
					seekTextSize = ta.getDimensionPixelSize(attr, 10);
					break;
				default:
					break;
			}
		}
		ta.recycle();

		currentMovingType = BallType.LEFT;
		seekTextPaint = createPaint(seekTextColor, seekTextSize, Paint.Style.FILL, 0);
		seekBgPaint = createPaint(seekBgColor, 0, Paint.Style.FILL, 0);
		seekBallStartPaint = createPaint(seekBallSolidColor, 0, Paint.Style.FILL, 0);
		seekPbPaint = createPaint(seekPbColor, 0, Paint.Style.FILL, 0);
		seekBallEndPaint = seekPbPaint;
		seekBallStrokePaint = createPaint(seekBallStrokeColor, 0, Paint.Style.FILL, 0);
		seekBallStrokePaint.setShadowLayer(5, 2, 2, seekBallStrokeColor);
	}

	private static final float SEEK_BG_SCALE = 1.1F / 2;
	private static final float SEEK_TEXT_SCALE = 1.F / 3.5F;
	private static final int DEF_HEIGHT = 125;
	private static final int DEF_PADDING = 50;
	private static final int BG_HEIGHT = 5;
	private static final int SEEK_STROKE_SIZE = 1;
	private int seekTextY;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mViewHeight = h;
		mViewWidth = w;

		seekBallRadio = 30;
		seekBallY = (int) (mViewHeight * SEEK_BG_SCALE + BG_HEIGHT / 2);
		seekTextY = (int) (mViewHeight * SEEK_TEXT_SCALE);
		leftSeekBallX = DEF_PADDING + seekBallRadio;
		rightSeekBallX = mViewWidth - DEF_PADDING - seekBallRadio;

		//	绿色条底下的灰白色的那一段, 只是做背景, 不会动
		seekBgRectF = new RectF(seekBallRadio + DEF_PADDING, mViewHeight * SEEK_BG_SCALE, mViewWidth - seekBallRadio - DEF_PADDING, mViewHeight * SEEK_BG_SCALE + BG_HEIGHT);
		//	中间的绿色的那一段, 会随着手动拖动而该改变长度
		seekPbRectF = new RectF(leftSeekBallX, mViewHeight * SEEK_BG_SCALE, rightSeekBallX, mViewHeight * SEEK_BG_SCALE + BG_HEIGHT);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int measureHeight;
		if (heightMode == MeasureSpec.AT_MOST || heightMeasureSpec == MeasureSpec.UNSPECIFIED) {
			measureHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_HEIGHT, getContext().getResources().getDisplayMetrics());
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		drawText(canvas);
		drawSeekBg(canvas);
		drawseekPb(canvas);
		drawLeftCircle(canvas);
		drawRightCircle(canvas);
	}

	private final String TAG = getClass().getSimpleName();

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = (int) event.getX();//获取点击事件距离控件左边的距离, 即视图坐标
				currentMovingType = getMovingLeftOrRight(downX);//现在是接近左边那个圆还是接近右边那个圆
				if (currentMovingType == BallType.LEFT) {
					leftSeekBallX = downX;
				} else if (currentMovingType == BallType.RIGHT) {
					rightSeekBallX = downX;
				}
				seekPbRectF.left = leftSeekBallX;
				seekPbRectF.right = rightSeekBallX;
				break;
			case MotionEvent.ACTION_MOVE:
				int moveX = (int) event.getX();
				// TODO: 2017/3/23
				if (currentMovingType == BallType.LEFT) {
					if (moveX >= rightSeekBallX) {
						moveX = rightSeekBallX;
					}
					leftSeekBallX = moveX;
				} else if (currentMovingType == BallType.RIGHT) {
					if (moveX <= leftSeekBallX) {
						moveX = leftSeekBallX;
					}
					rightSeekBallX = moveX;
				}
				seekPbRectF.left = leftSeekBallX;
				seekPbRectF.right = rightSeekBallX;
				break;
			case MotionEvent.ACTION_UP:
				int upX = (int) event.getX();
				if (currentMovingType == BallType.LEFT) {
					leftSeekBallX = leftSeekBallX - rightSeekBallX >= 0 ? rightSeekBallX : upX;
				} else if (currentMovingType == BallType.RIGHT) {
					rightSeekBallX = rightSeekBallX - leftSeekBallX <= 0 ? leftSeekBallX : upX;
				}
				break;
			default:
				break;
		}

		//	边界限制
		if (BallType.LEFT == currentMovingType) {
			if (leftSeekBallX < seekBallRadio + DEF_PADDING) {
				leftSeekBallX = seekBallRadio + DEF_PADDING;
			}
			if (leftSeekBallX > mViewWidth - seekBallRadio - DEF_PADDING) {
				leftSeekBallX = mViewWidth - seekBallRadio - DEF_PADDING;
			}
		} else if (BallType.RIGHT == currentMovingType) {
			if (rightSeekBallX < seekBallRadio + DEF_PADDING) {
				rightSeekBallX = seekBallRadio + DEF_PADDING;
			}
			if (rightSeekBallX > mViewWidth - seekBallRadio - DEF_PADDING) {
				rightSeekBallX = mViewWidth - seekBallRadio - DEF_PADDING;
			}
		}
		seekPbRectF.left = leftSeekBallX;
		seekPbRectF.right = rightSeekBallX;
		invalidate();
		return true;
	}

	private enum BallType {
		LEFT, RIGHT
	}

	private BallType getMovingLeftOrRight(int downX) {
		if (leftSeekBallX == rightSeekBallX) {
			if (downX >= rightSeekBallX) {
				return BallType.RIGHT;
			} else if (downX <= leftSeekBallX) {
				return BallType.LEFT;
			}
		}
		return Math.abs(leftSeekBallX - downX) - Math.abs(rightSeekBallX - downX) > 0 ? BallType.RIGHT : BallType.LEFT;
	}

	private void drawLeftCircle(Canvas canvas) {
		setLayerType(LAYER_TYPE_SOFTWARE, null);
		canvas.drawCircle(leftSeekBallX, seekBallY, seekBallRadio, seekBallStrokePaint);
		canvas.drawCircle(leftSeekBallX, seekBallY, seekBallRadio - SEEK_STROKE_SIZE, seekBallStartPaint);
	}

	private void drawRightCircle(Canvas canvas) {
		setLayerType(LAYER_TYPE_SOFTWARE, null);
		canvas.drawCircle(rightSeekBallX, seekBallY, seekBallRadio, seekBallStrokePaint);
		canvas.drawCircle(rightSeekBallX, seekBallY, seekBallRadio - SEEK_STROKE_SIZE, seekBallEndPaint);
	}

	private void drawseekPb(Canvas canvas) {
		canvas.drawRect(seekPbRectF, seekPbPaint);
	}

	private void drawSeekBg(Canvas canvas) {
		canvas.drawRect(seekBgRectF, seekBgPaint);
	}

	private List<String> data;

	public void setData(List<String> data) {
		this.data = data;
		invalidate();
	}



	private void drawText(Canvas canvas) {
		if (data == null) {
			return;
		}
		int size = data.size();
		int unitWidth = getUnitWidth(size - 1);//	两个文字之间的宽度
		for (int i = 0; i < size; i++) {
			String text = data.get(i);
			float measureTextWidth = seekPbPaint.measureText(text);
			canvas.drawText(text, DEF_PADDING + unitWidth * i - measureTextWidth / 2, seekTextY, seekTextPaint);
		}
	}


	// 计算两个文字之间的宽度, view宽度-内容的左右边距以及圆球的直径, 再除以文字的总数量 - 1
	private int getUnitWidth(int count) {
		return (mViewWidth - 2 * DEF_PADDING - 2 * seekBallRadio) / count;
	}

	private Paint createPaint(int paintColor, int textSize, Paint.Style style, int lineWidth) {
		Paint paint = new Paint();
		paint.setColor(paintColor);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(lineWidth);
		paint.setDither(true);
		paint.setTextSize(textSize);
		paint.setStyle(style);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
		return paint;
	}


}
