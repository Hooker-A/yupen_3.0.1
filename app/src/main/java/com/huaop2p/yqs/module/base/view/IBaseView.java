package com.huaop2p.yqs.module.base.view;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface IBaseView<T> {
    public void startLoadData();

    public void loadDataOver();

    /**
     * 显示数据
     **/
    public void showSuccess(T t);

    /**
     * 显示错误
     **/
    public void showError(int errorCode,String str);
}
