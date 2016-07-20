package com.huaop2p.yqs.widget;

import java.util.Timer;
import java.util.TimerTask;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.two_wealth.assist.BaseCalculator;
import com.huaop2p.yqs.utils.auto.AutoUtils;

public class ProgressView extends View {
    private Paint paint;
    private int lineColor;
    private int contentColor;
    private int jianGe = AutoUtils.getPercentHeightSize(20); // 进度条的宽度
    private int textSize = 20;
    private int textColor = Color.RED;
    private String content = "";
    private float[] pros = new float[]{0};  //每个颜色的百分比
    private int[] colors = new int[]{Color.BLUE}; //颜色
    private int count = 1;      //数量
    private Timer timer = new Timer();
    private Rect bounds = new Rect();
    private RectF oval = new RectF();
    private int margin;
    public float time = 3;
    private boolean isAnimation = true;
    private ObjectAnimator objectAnimator;
    private BaseCalculator calculator;
    private Handler handler = new Handler() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ProgressView.this.invalidate();
                    break;
                default:
                    objectAnimator.cancel();
                    break;
            }
        }
    };

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ProgressView);
        lineColor = mTypedArray.getColor(R.styleable.ProgressView_lineColor,
                getResources().getColor(R.color.qian_grey));
        contentColor = mTypedArray.getColor(R.styleable.ProgressView_contentColor,
                Color.RED);
        paint = new Paint();
        calculator = BaseCalculator.getClaculator();
        mTypedArray.recycle();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);   //关闭硬件加速  防止webview造成的影响
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(lineColor);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);// 此函数是用来防止边缘的锯齿
//        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
//        paint.setTypeface(font);
        int banJIn;
        if (getHeight() > getWidth()) {
            banJIn = getWidth() / 2;
        } else {
            banJIn = getHeight() / 2;
        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, banJIn - jianGe / 2 + margin,
                paint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, banJIn - jianGe
                - jianGe / 2 - margin, paint);
        paint.setColor(contentColor);
        paint.setStrokeWidth(jianGe);
        float oldIndex = 270;
        for (int i = 0; i < pros.length; i++) {
            paint.setColor(colors[i]);
            if (getHeight() >= getWidth()) { // 高度大于等于宽度
                oval.left = jianGe; // 左边 进度条左边两个圆圈中间位置
                oval.top = (getHeight() - getWidth()) / 2 + jianGe; // 上边
                oval.right = getWidth() - jianGe; // 右边
                oval.bottom = getHeight() - oval.top;
            } else {
                oval.left = jianGe + (getWidth() - getHeight()) / 2; // 左边
                oval.top = jianGe; // 上边
                oval.right = getWidth() - oval.left; // 右边
                oval.bottom = getHeight() - jianGe;

            }

            float result = 3.6f * pros[i] / time;
            canvas.drawArc(oval, oldIndex, result, false, paint);

            oldIndex += 3.6f * pros[i];
        }
        paint.reset();
        //  paint.setTypeface(font);
        paint.setTextAlign(Align.LEFT);
        paint.setColor(textColor);
        paint.setTextSize(textSize);

        paint.getTextBounds(content, 0, content.length(), bounds);
        FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top)
                / 2 - fontMetrics.top;

        canvas.drawText(content, getMeasuredWidth() / 2 - bounds.width() / 2,
                baseline, paint);

    }

    public void clearPro() {
        pros = new float[0];
        this.invalidate();
    }

    public void setPro(float[] progress, int[] colors) {
        if (isAnimation) {
            startAnimation();
        }
        pros = progress;
        this.colors = colors;
        count = progress.length;
        time = 5f;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (time <= 1.0f) {
                    if (isAnimation) {
                        handler.sendEmptyMessage(2);

                    }
                    this.cancel();
                    return;
                }
                time = calculator.reduce(time, 0.1f);
                handler.sendEmptyMessage(1);
            }
        }, 0, 25);

    }

    private void startAnimation() {
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("rotation", 0.0F, 360.0F);
        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, pvhZ);
        objectAnimator.setDuration(5000).start();
    }

//    public float sub(float value1, float value2) {
//        BigDecimal b1 = new BigDecimal(Float.toString(value1));
//        BigDecimal b2 = new BigDecimal(Float.toString(value2));
//        return b1.subtract(b2).floatValue();
//    }

    public void setProWidth(int jianGe) {
        this.jianGe = AutoUtils.getPercentHeightSize(jianGe);
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setContentColor(int contentColor) {
        this.contentColor = contentColor;
    }

    public void setBoldColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public void seTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void seTextContent(String content) {
        this.content = content;
    }

    public void seMargin(int margin) {
        this.margin = AutoUtils.getPercentHeightSize(margin);
    }
    public float[] getPro(){
        return pros;
    }
}
