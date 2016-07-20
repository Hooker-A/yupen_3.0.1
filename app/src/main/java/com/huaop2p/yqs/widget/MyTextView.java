package com.huaop2p.yqs.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView  extends TextView{
	private Paint  paint;
	private int backgroundColor=Color.TRANSPARENT;
	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint=new  Paint();
	}
	public MyTextView(Context context) {
		super(context);
		paint=new  Paint();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		paint.setAntiAlias(true);
		paint.setColor(backgroundColor);
		paint.setStyle(Paint.Style.FILL);
		RectF oval4 = new RectF(0, 0f, getWidth(), getHeight());
		canvas.drawRoundRect(oval4, 4, 4, paint);
		super.onDraw(canvas);
	}
	public void setBackgroundColor(int color){
		this.backgroundColor=color;
	}

}
