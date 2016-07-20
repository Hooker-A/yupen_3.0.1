package com.huaop2p.yqs.module.one_home.interface_;

/**
 * Created by maoxiaofei on 2016/4/13.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
