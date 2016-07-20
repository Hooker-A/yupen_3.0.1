package com.huaop2p.yqs.module.base.linstener;

/**
 * Created by Administrator on 2016/5/17.
 * 作者：任洋
 * 功能：
 */
public interface EventBusLinstener<T> {
    public void onEventMainThread(T t);
}
