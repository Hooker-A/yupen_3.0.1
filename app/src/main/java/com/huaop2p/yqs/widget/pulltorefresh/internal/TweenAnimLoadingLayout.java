package com.huaop2p.yqs.widget.pulltorefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.module.two_wealth.anim.AnimList;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;

public class TweenAnimLoadingLayout extends LoadingLayout {

    private AnimationDrawable frameAnim1, frameAnim2;

    public TweenAnimLoadingLayout(Context context, PullToRefreshBase.Mode mode,
                                  PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        // 初始化

    }

    // 默认图片  
    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.dropdown_load_1;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {
        frameAnim1 = addFrame("dropdown_load_", 5, 100);
        frameAnim2 = addFrame("dropdown_loading_", 58, 50);
        // NO-OP
    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        // NO-OP
        mHeaderImage.setBackgroundDrawable(frameAnim1);
    }

    // 下拉以刷新  
    @Override
    protected void pullToRefreshImpl() {
        // NO-OP
        if (frameAnim1.isRunning()) {
            frameAnim1.setVisible(true, true);
            frameAnim1.stop();
        }
    }

    // 正在刷新时回调  
    @Override
    protected void refreshingImpl() {
        // 播放帧动画
        mHeaderImage.setBackgroundDrawable(frameAnim2);
        frameAnim2.start();
    }

    // 释放以刷新  
    @Override
    protected void releaseToRefreshImpl() {
        frameAnim1.start();
        // NO-OP  
    }

    // 重新设置  
    @Override
    protected void resetImpl() {
        if (frameAnim2 != null) {
            frameAnim2.stop();
            if (frameAnim1!=null)
                frameAnim1.setVisible(true,true);
            mHeaderImage.setBackgroundDrawable(frameAnim1);
        }
    }

    public AnimationDrawable addFrame(String name, int count, int time) {
        mHeaderImage.clearAnimation();
        mHeaderImage.setBackgroundDrawable(null);
        AnimationDrawable frameAnim= AnimList.addFrame(name,count,time,getContext());
        frameAnim.setOneShot(false);
        return frameAnim;
    }
}