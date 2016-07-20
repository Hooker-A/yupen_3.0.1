package com.huaop2p.yqs.widget;

import java.math.BigDecimal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.huaop2p.yqs.R;

public class CostomButton extends View {
    private Paint paint;
    private float progress = 0;
    private int backgroundColor = getResources().getColor(R.color.red_real);
    private Runnable run;

    public CostomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);// 设置画笔的锯齿
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        RectF oval3 = new RectF(0, getHeight() - (getHeight() / 100f)
                * progress, getWidth(), getHeight());
        canvas.drawRoundRect(oval3, 5, 5, paint);
    }

    public void setProgress(final float progress) {
        this.progress = 0;
        if (run != null) {
            this.removeCallbacks(run);
        }
        run = new Runnable() {

            @Override
            public void run() {
                if (CostomButton.this.progress == progress) {
                    invalidate();
                    CostomButton.this.removeCallbacks(this);
                    return;
                } else {
                    invalidate();
                    CostomButton.this.postDelayed(this, 5);
                }
                CostomButton.this.progress = add(CostomButton.this.progress,
                        1f);

            }
        };
        this.postDelayed(run, 10);
    }

    public void setFull() {
        if (run != null) {
            this.removeCallbacks(run);
        }
        run = new Runnable() {

            @Override
            public void run() {

                if (CostomButton.this.progress >= 100) {
                    CostomButton.this.removeCallbacks(this);
                    return;
                } else {
                    invalidate();
                    CostomButton.this.postDelayed(this, 5);
                }
                CostomButton.this.progress = add(CostomButton.this.progress,
                        2f);

            }
        };
        this.postDelayed(run, 5);
    }

    public void setColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        progress = 100;
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    public float add(float value1, float value2) {
        BigDecimal b1 = new BigDecimal(Float.toString(value1));
        BigDecimal b2 = new BigDecimal(Float.toString(value2));
        return b1.add(b2).floatValue();
    }
}
