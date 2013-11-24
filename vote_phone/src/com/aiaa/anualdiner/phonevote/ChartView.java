package com.aiaa.anualdiner.phonevote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class ChartView extends View {
	private Paint pic_paint;
	private Paint num_Paint;
	private Paint name_Paint;

	private int numDisplayOffset = 9;

	// 起始高度为 最大高度减去 80 【注意这里的高度是反的，也就是说，y轴是逐渐减少的】
	private float startPaintHeight = Result.START_POINT;
	private float endPaintHeight = startPaintHeight;

	// 柱状图的宽度
	private int width = Result.PAINT_WIDTH;
	private int distance = Result.PAINT_DISTANCE;

	private int value;
	private int indexSize;

	private String name = "";

	private boolean isNumberOne;
	private double paintWeight = Result.PAINT_WEIGHT;

	private static int TEXT_SIZE = 30;

	/**
	 * 
	 * @param context
	 * @param maxSize
	 *            需要显示的数值
	 * @param displayMode
	 *            显示的类型
	 */
	public ChartView(Context context, int value, String name) {
		super(context);
		this.value = value;
		this.name = name;
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isNumberOne) {
			num_Paint.setTextSize(TEXT_SIZE*2);	
			
			if (indexSize > 20) {
				pic_paint.setARGB(255, 255, 255, 0);
			}
		}
		else {
			num_Paint.setTextSize(TEXT_SIZE);
			pic_paint.setARGB(255, 255, 255, 128);
		}
		
		
		canvas.drawRect(distance, endPaintHeight, distance + width, startPaintHeight, pic_paint);			
		canvas.drawText("" + indexSize, numDisplayOffset, endPaintHeight - distance, num_Paint);
		canvas.drawText(name, 0, startPaintHeight + 30, name_Paint);
	}

	// 初始化
	private void init() {
		// 数值初始化
		pic_paint = new Paint();
		pic_paint.setARGB(255, 255, 255, 128);

		num_Paint = new Paint();
		num_Paint.setARGB(255, 255, 255, 0);
		num_Paint.setTextSize(TEXT_SIZE);

		name_Paint = new Paint();
		name_Paint.setARGB(255, 255, 255, 255);
		name_Paint.setTextSize(TEXT_SIZE);

		numDisplayOffset = 2;
		if (value < 10) {
			numDisplayOffset = 12;
		} else if (value < 100) {
			numDisplayOffset = -2;
		}
		indexSize = value;
		endPaintHeight = startPaintHeight - (float) (value * paintWeight);
		invalidate();

	}

	public void setHight(int hight, boolean isNumberOne) {
		indexSize = hight;
		endPaintHeight = startPaintHeight - (float) (hight * paintWeight);
		this.isNumberOne = isNumberOne;
		invalidate();
	}

}