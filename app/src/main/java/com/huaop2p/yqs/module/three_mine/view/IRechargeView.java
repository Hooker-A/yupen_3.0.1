package com.huaop2p.yqs.module.three_mine.view;

import android.view.View;

import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.model.entity.QuotaBank;

/**
 * Created by Administrator on 2016/6/14.
 * 作者：任洋
 * 功能：
 */
public interface IRechargeView {
    public void setSign(String sign);

    public void updateNumByValue(double value);

    public void recharge(View view);

    public void setBalance(Balance balance);

    public void cash(View view);

    public void  setBank(QuotaBank banks);
}
