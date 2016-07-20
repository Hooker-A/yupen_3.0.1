package com.huaop2p.yqs.widget;

import com.huaop2p.yqs.module.logon.activity.WheelViewT;

/**
 * Created by zgq on 2016/5/26.
 * 作者:  zhu  guo qing
 * 时间:  2016/5/26 17:47
 * 功能:
 */
public interface OnWheelScroll {
    void onScrollingStarted(WheelViewT wheel);

    /**
     * Callback method to be invoked when scrolling ended.
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingFinished(WheelViewT wheel);
}
