package com.huaop2p.yqs.widget.bank;

/**
 * Created by zgq on 2016/5/26.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/26 15:21
 * 功能:
 */
public interface OnWheelScrollListener {
    void onScrollingStarted(WheelView wheel);

    /**
     * Callback method to be invoked when scrolling ended.
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingFinished(WheelView wheel);
}
