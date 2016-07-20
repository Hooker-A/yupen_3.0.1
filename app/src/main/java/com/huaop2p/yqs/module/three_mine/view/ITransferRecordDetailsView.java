package com.huaop2p.yqs.module.three_mine.view;

/**
 * Created by Administrator on 2016/5/25.
 * 作者：任洋
 * 功能：
 */
public interface ITransferRecordDetailsView<T> {
    public void loadDetails(T t);

    public void cancelTransfer(String string);
}
