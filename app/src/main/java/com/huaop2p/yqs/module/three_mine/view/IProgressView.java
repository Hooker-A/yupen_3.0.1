package com.huaop2p.yqs.module.three_mine.view;

/**
 * Created by Administrator on 2016/5/6.
 * 作者：任洋
 * 功能：进度条接口
 */
public interface IProgressView {
    public void setProgress(float[] progress);

    public void setColors(int[] colors);

    public void startLoad();

    public void setMargin(int margin);

    public void clearProgerss();

}
