package com.huaop2p.yqs.module.one_home.interface_;

import com.huaop2p.yqs.module.one_home.Module.view.RiseNumberTextView;

/**
 * Created by maoxiaofei on 2016/5/25.
 */
public interface RiseNumberBase {
    public void start();

    public RiseNumberTextView withNumber(float number);

    public RiseNumberTextView withNumber(float number, boolean flag);

    public RiseNumberTextView withNumber(int number);

    public RiseNumberTextView setDuration(long duration);

    public void setOnEnd(RiseNumberTextView.EndListener callback);
}
