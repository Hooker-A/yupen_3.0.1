package com.huaop2p.yqs.module.one_home.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;


import com.huaop2p.yqs.R;

import java.util.ArrayList;

/**
 * Created by maoxiaofei on 2016/2/2.
 */
public class FlakeView extends View {
    Bitmap droid;       // 所有片使用的位图
    int numFlakes = 0;  // 当前数量的片
    ArrayList<Flake> flakes = new ArrayList<Flake>(); // List of current flakes当前片列表

    // Animator used to drive all separate flake animations. Rather than have potentially
    // hundreds of separate animators, we just use one and then update all flakes for each
    // frame of that single animation.　动画师用于驱动所有单独的片状动画。而不是可能　　
    // / /数百个独立的动画师,我们只使用一个然后更新所有雪花　　/ /帧的动画。
    public ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
    long startTime, prevTime; // 用于追踪时间为动画和fps
    int frames = 0;     // Used to track frames per second用来跟踪帧每秒
    Paint textPaint;    // Used for rendering fps text用于呈现fps文本
    float fps = 0;      // frames per second帧频
    Matrix m = new Matrix(); // Matrix used to translate/rotate each flake during rendering矩阵用于翻译/旋转期间每片呈现
    String fpsString = "";
    String numFlakesString = "";
    /**
     * Constructor. Create objects used throughout the life of the View: the Paint and
     * the animator　构造函数。创建对象的整个生命周期中使用视图:油漆和　　*的动画师
     */
    public FlakeView(Context context) {
        super(context);
        droid = BitmapFactory.decodeResource(getResources(), R.drawable.moneey);//金币图片
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);

        // This listener is where the action is for the flak animations. Every frame of the
        // animation, we calculate the elapsed time and update every flake's position and rotation
        // according to its speed.　此侦听器是防弹的行动是动画。每一帧的　　
        // / /动画,我们计算运行时间和更新每一片的位置和旋转　　/ /根据它的速度
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                long nowTime = System.currentTimeMillis();
                float secs = (float) (nowTime - prevTime) / 100f;
                prevTime = nowTime;
                for (int i = 0; i < numFlakes; ++i) {
                    Flake flake = flakes.get(i);
                    flake.y += (flake.speed * secs);
                    if (flake.y > getHeight()) {
                        // If a flake falls off the bottom, send it back to the top如果一个片状脱落底部,将其发送回顶部
//                        flake.y = 0 - flake.height;
                    }
                    flake.rotation = flake.rotation + (flake.rotationSpeed * secs);
                }
                // Force a redraw to see the flakes in their new positions and orientations力重绘看雪花在他们的新位置和方向
                invalidate();//调用系统方法进行重置
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);//重复数量
        animator.setDuration(3000);//持续时间
    }

    int getNumFlakes() {
        return numFlakes;
    }

    private void setNumFlakes(int quantity) {
        numFlakes = quantity;
        numFlakesString = "numFlakes: " + numFlakes;
    }

    /**
     * Add the specified number of droidflakes.添加指定的droidflakes的数量。
     */
    public void addFlakes(int quantity) {
        for (int i = 0; i < quantity; ++i) {
            flakes.add(Flake.createFlake(getWidth(), droid,getContext()));
        }
        setNumFlakes(numFlakes + quantity);
    }

    /**
     * Subtract the specified number of droidflakes. We just take them off the end of the
     * list, leaving the others unchanged.　减去droidflakes的指定数量。我们只是把他们的结束　　*列表,其他不变
     */
    void subtractFlakes(int quantity) {
        for (int i = 0; i < quantity; ++i) {
            int index = numFlakes - i - 1;
            flakes.remove(index);
        }
        setNumFlakes(numFlakes - quantity);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Reset list of droidflakes, then restart it with 8 flakes重置droidflakes列表,然后用8片重新启动它
        flakes.clear();
        numFlakes = 0;
        addFlakes(16);
        // Cancel animator in case it was already running取消动画师,以防它已经运行
        animator.cancel();
        // Set up fps tracking and start the animation设置帧跟踪和启动动画
        startTime = System.currentTimeMillis();
        prevTime = startTime;
        frames = 0;
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // For each flake: back-translate by half its size (this allows it to rotate around its center),
        // rotate by its current rotation, translate by its location, then draw its bitmap每片:back-translate一半大小(这使它旋转中心), 　
        // 　/ /当前旋转旋转,翻译的位置,然后画出它的位图
        for (int i = 0; i < numFlakes; ++i) {
            Flake flake = flakes.get(i);
            m.setTranslate(-flake.width / 2, -flake.height / 2);
            m.postRotate(flake.rotation);
            m.postTranslate(flake.width / 2 + flake.x, flake.height / 2 + flake.y);
            canvas.drawBitmap(flake.bitmap, m, null);
        }
        // fps counter: count how many frames we draw and once a second calculate the
        // frames per second　fps计数器:数我们画多少帧和第二计算一次　　/ /帧每秒
        ++frames;
        long nowTime = System.currentTimeMillis();
        long deltaTime = nowTime - startTime;
        if (deltaTime > 1000) {
            float secs = (float) deltaTime / 1000f;
            fps = (float) frames / secs;
//            fpsString = "fps: " + fps;
            startTime = nowTime;
            frames = 0;
        }
//        canvas.drawText(numFlakesString, getWidth() - 200, getHeight() - 50, textPaint);
//        canvas.drawText(fpsString, getWidth() - 200, getHeight() - 80, textPaint);画布。drawText(numFlakesString getWidth()- 200,获得()- 50,textPaint); 　　
// / /画布。drawText(fpsString getWidth()- 200,获得()——80年,textPaint);
    }

    public void pause() {
        // Make sure the animator's not spinning in the background when the activity is paused.
        // 确保动画不是旋转活动时在后台暂停。
        animator.cancel();
    }

    public void resume() {
        animator.start();
    }
}
