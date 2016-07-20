package com.huaop2p.yqs.module.two_wealth.view;

import com.huaop2p.yqs.module.three_mine.model.entity.Balance;

/**
 * Created by Administrator on 2016/5/31.
 * 作者：任洋
 * 功能：
 */
public interface IInvestmentView {
    public  void updateNumByValue(long value);
    public void setBalance(Balance balance);
    public  void startWebActivity(String url);
}
