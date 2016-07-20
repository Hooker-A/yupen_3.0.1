package com.huaop2p.yqs.module.three_mine.view;

import com.huaop2p.yqs.module.three_mine.model.entity.Balance;

/**
 * Created by Administrator on 2016/6/22.
 * 作者：任洋
 * 功能：
 */
public interface ICashView {

    public void updateNumByValue(double value);
    public void setBalance(Balance balance);
}
