package com.huaop2p.yqs.module.three_mine.view;

import com.huaop2p.yqs.module.three_mine.model.entity.Balance;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface IMyWealthView  {

    public void signSuccess(String str);
    public void signError(String str);

    public void openShade();
    public void closeShade();

    public void clearUiData();

    public void setBalance(Balance balance);

    public  void updateSignButton(String string);

    public void setTotalMoney(String string);
}
