package com.huaop2p.yqs.module.three_mine.model;

import com.huaop2p.yqs.utils.net.OnRequestLinstener;

import java.util.Map;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface IMyWealthModel {

    public void  sign(OnRequestLinstener basePresenter);

    public void  getBalance(OnRequestLinstener basePresenter);

    public void selectIsSign(OnRequestLinstener basePresenter);

    public  void getSign(OnRequestLinstener basePresenter,Map<String,Object> map);

    public  void getBank(OnRequestLinstener basePresenter);

    public  void getAgreement(OnRequestLinstener basePresenter,String url);
}
