package com.aiaa.anualdiner.phonevote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class RectView extends View {

	private float left;
	private float top;
	private float width;
	private float bottom;
	private Paint pic_paint;

	public RectView(Context context) {
		super(context);	
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawRect(left, top, left + width, bottom, pic_paint);			
		
	}
	
	private void init() {
		
		pic_paint = new Paint();
		pic_paint.setARGB(255, 255, 255, 128);

		invalidate();

	}
}
