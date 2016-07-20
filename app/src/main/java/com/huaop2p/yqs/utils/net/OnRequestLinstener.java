package com.huaop2p.yqs.utils.net;

/**
 * Created by Administrator on 2016/5/12.
 * 作者：任洋
 * 功能：请求返回结果监听
 */
public interface OnRequestLinstener<T> {
    public void  success(T t);
    public void error(int code,String error);
}
