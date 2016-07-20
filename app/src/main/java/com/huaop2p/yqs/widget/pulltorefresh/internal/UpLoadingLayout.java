package com.huaop2p.yqs.widget.pulltorefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.huaop2p.yqs.R;
import com.huaop2p.yqs.utils.auto.AutoUtils;
import com.huaop2p.yqs.widget.pulltorefresh.PullToRefreshBase;

public class UpLoadingLayout extends LoadingLayout {

    private AnimationDrawable frameAnim;
    private String packageName;
    private TextView pull_to_refresh_sub_text;
    public UpLoadingLayout(Context context, PullToRefreshBase.Mode mode,
                           PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        AutoUtils.initLayoutSize(this,context);
        pull_to_refresh_sub_text= (TextView) findViewById(R.id.pull_to_refresh_sub_text);
        // 初始化
    }

    // 默认图片  
    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.dropdown_load_1;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {
        packageName = getContext().getPackageName();
        frameAnim = addFrame("load", 12, 150);
        // NO-OP
    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        // NO-OP
        mHeaderImage.setBackgroundDrawable(frameAnim);
    }

    // 下拉以刷新  
    @Override
    protected void pullToRefreshImpl() {
        // NO-OP
        if (frameAnim.isRunning()) {
            frameAnim.setVisible(true, true);
            frameAnim.stop();
        }
        pull_to_refresh_sub_text.setText("上拉加载");
    }

    // 正在刷新时回调  
    @Override
    protected void refreshingImpl() {
        // 播放帧动画
        frameAnim.start();
        pull_to_refresh_sub_text.setText("正在刷新...");
    }

    // 释放以刷新  
    @Override
    protected void releaseToRefreshImpl() {
        frameAnim.start();
        pull_to_refresh_sub_text.setText("释放刷新");
        // NO-OP  
    }

    // 重新设置  
    @Override
    protected void resetImpl() {
        if (frameAnim != null) {
            frameAnim.stop();
            mHeaderImage.setBackgroundDrawable(frameAnim);
        }
    }

    public AnimationDrawable addFrame(String name, int count, int time) {
        mHeaderImage.clearAnimation();
        mHeaderImage.setBackgroundDrawable(null);
        AnimationDrawable frameAnim = new AnimationDrawable();
        for (int i = 1; i <= count; i++) {
            int imageResId = getResources().getIdentifier(name + i, "drawable", packageName);
            frameAnim.addFrame(getResources().getDrawable(imageResId), time);
        }
        frameAnim.setOneShot(false);
        return frameAnim;
    }
}