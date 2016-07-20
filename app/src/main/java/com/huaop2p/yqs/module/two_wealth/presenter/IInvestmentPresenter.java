package com.huaop2p.yqs.module.two_wealth.presenter;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/31.
 * 作者：任洋
 * 功能：
 */
public interface IInvestmentPresenter {
    public void payYuXin(Map<String, Object> map);
    public void payHouseCar(Map<String, Object> map);
    public void payTransfer(Map<String, Object> map);
    public void getBalance() ;
    public void getYuXinAgreement();
    public void getYuFangAgreement();
    public void getYuCheAgreement();
}
