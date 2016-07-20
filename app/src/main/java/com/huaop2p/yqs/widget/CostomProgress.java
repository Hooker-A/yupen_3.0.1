package com.huaop2p.yqs.widget;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.huaop2p.yqs.R;

public class CostomProgress extends View {
    private Paint paint;
    private float progress = -1;
    private int backgroundColor1 = getResources().getColor(R.color.red);   //进度值
    private int backgroundColor2 = getResources().getColor(R.color.qian_grey);   //背景
    private TimerTask run;
    private Rect bounds = new Rect();
    private String content;
    private int textColor = Color.WHITE;   //字体颜色
    private Timer timer = new Timer();
    private StringBuffer sb = new StringBuffer();
    private int style = STYLE1;
    private int textSize = 40;
    public final static int STYLE1 = 1;
    public final static int STYLE0 = 0;
    public final static int STYLE2 = 2;

    public CostomProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (style == STYLE0) {
            setOne(canvas);
        } else if (style == STYLE2) {
            paint.setAntiAlias(true);// 设置画笔的锯齿
            paint.setStyle(Paint.Style.FILL);// / 设置填满
            paint.setColor(backgroundColor1);
            RectF oval3 = new RectF(0, 0, (getWidth() / 100f)
                    * progress, getHeight()); // 设置个新的长方形
            canvas.drawRoundRect(oval3, 0, 0, paint); // 画一个进度背景
        } else {
            setTwo(canvas);
        }
    }

    private void setOne(Canvas canvas) {
        sb.delete(0, sb.length());
        paint.setAntiAlias(true);// 设置画笔的锯齿
        paint.setColor(backgroundColor2);
        paint.setStyle(Paint.Style.FILL);// / 设置填满
        sb.append(progress).append("%");
        content = sb.toString();

        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.investment_progress_bg);
        float width = ((getWidth()-bm.getWidth()) / 100f) * progress ;
        if (width < 0) {
            width = 0;
        }
        canvas.drawBitmap(bm, width,
                0, paint);// 画文字背景图

        RectF oval4 = new RectF(bm.getWidth()/2, bm.getHeight(), getWidth()-bm.getWidth()/2, getHeight());
        canvas.drawRoundRect(oval4, 15, 15, paint); // 画一个默认背景

        paint.setColor(backgroundColor1);
        RectF oval3 = new RectF(bm.getWidth()/2, bm.getHeight(), ((getWidth()-bm.getWidth()) / 100f)
                * progress+bm.getWidth()/2, getHeight()); // 设置个新的长方形
        canvas.drawRoundRect(oval3, 15, 15, paint); // 画一个进度背景

        paint.reset();
        paint.setTextAlign(Align.LEFT);
        paint.setColor(textColor);
        paint.setTextSize(textSize);

        paint.getTextBounds(content, 0, content.length(), bounds);
        FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = bm.getHeight() / 2 + bounds.height() / 2;

        canvas.drawText(content, width + bm.getWidth() / 2 - bounds.width() / 2, baseline,
                paint);
    }

    private void setTwo(Canvas canvas) {
        sb.delete(0, sb.length());
        paint.setAntiAlias(true);// 设置画笔的锯齿
        paint.setColor(backgroundColor2);
        paint.setStyle(Paint.Style.FILL);// / 设置填满
        sb.append( progress).append("%");
        content = sb.toString();

        RectF oval4 = new RectF(0, 0f, getWidth(), getHeight()); // 设置个新的长方形
        canvas.drawRoundRect(oval4, 15, 15, paint);
        paint.setColor(backgroundColor1);
        RectF oval3 = new RectF(0, 0f, (getWidth() / 100f) * progress,
                getHeight()); // 设置个新的长方形
        canvas.drawRoundRect(oval3, 15, 15, paint);

        paint.reset();
        paint.setTextAlign(Align.LEFT);
        paint.setColor(textColor);
        paint.setTextSize(30);

        paint.getTextBounds(content, 0, content.length(), bounds);
        FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top)
                / 2 - fontMetrics.top;
        float width = (getWidth() / 100f) * progress - bounds.right;
        if (width < 0) {
            width = 0;
        }
        canvas.drawText(content, width,
                baseline, paint);
    }

    public void setProgress(final float progress) {
        this.progress = 0;
        if (run != null) {
            run.cancel();
        }
        run = new TimerTask() {
            @Override
            public void run() {
                if (progress <= 0) {
                    CostomProgress.this.postInvalidate();
                    this.cancel();
                    return;
                }
                if (CostomProgress.this.progress >= progress) {
                    this.cancel();
                } else {
                    CostomProgress.this.progress = add(CostomProgress.this.progress,
                            0.5f);
                    CostomProgress.this.postInvalidate();
                }
            }
        };
        timer.schedule(run, 0, 5);
    }

    public void setProgress(float occupy, float total) {
        if (total == 0) {
            setProgress(0);
            return;
        }
        float pro = occupy * 100 / total ;
        if (pro>100)
            pro=100;
        else if (pro<0)
            pro=0;
        setProgress(pro);
    }

    public int getProgress() {
        return (int) progress;
    }

    public void setFull() {
        if (run != null) {
            run.cancel();
        }
        run = new TimerTask() {
            @Override
            public void run() {
                if (CostomProgress.this.progress >= 100) {
                    this.cancel();
                } else {
                    CostomProgress.this.progress = add(CostomProgress.this.progress,
                            0.1f);
                    CostomProgress.this.postInvalidate();
                }

            }
        };
        timer.schedule(run, 0, 5);
    }

    public float add(float value1, float value2) {
        BigDecimal b1 = new BigDecimal(Float.toString(value1));
        BigDecimal b2 = new BigDecimal(Float.toString(value2));
        return b1.add(b2).floatValue();
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBackgroundColorSelect(int color) {
        this.backgroundColor1 = color;
    }

    public void setBackgroundColorDefault(int color) {
        this.backgroundColor2 = color;
    }
}
