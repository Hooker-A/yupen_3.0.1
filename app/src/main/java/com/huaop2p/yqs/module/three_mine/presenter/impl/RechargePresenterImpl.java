package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.logon.AppApplication;
import com.huaop2p.yqs.module.three_mine.activity.BaseRechargeCashActivity;
import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.model.entity.QuotaBank;
import com.huaop2p.yqs.module.three_mine.model.impl.MyWealthModelImpl;
import com.huaop2p.yqs.module.three_mine.presenter.IRechargePresenter;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/14.
 * 作者：任洋
 * 功能：
 */
public class RechargePresenterImpl  implements IRechargePresenter {
    private BaseRechargeCashActivity activity;
    private MyWealthModelImpl myWealthModel;

    public RechargePresenterImpl(BaseRechargeCashActivity activity) {
        super();
        this.activity = activity;
        myWealthModel = new MyWealthModelImpl();

    }

    /***
     * 获取签名
     */
    @Override
    public void getSign(Map<String, Object> map) {
        if (!AppApplication.isLogin)
            return;
        activity.startLoadData();
        myWealthModel.getSign(new OnRequestLinstener<BaseResponseEntity<String>>() {
            @Override
            public void success(BaseResponseEntity<String> entity) {
                activity.loadDataOver();
                activity.setSign(entity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
            }
        }, map);
    }

    /***
     * 获取余额
     **/
    @Override
    public void getBalance() {
        myWealthModel.getBalance(new OnRequestLinstener<BaseResponseEntity<List<Balance>>>() {
            @Override
            public void success(BaseResponseEntity<List<Balance>> o) {
                if (o == null || o.ReturnMessage == null || o.ReturnMessage.get(0) == null) {
                } else {
                    activity.setBalance(o.ReturnMessage.get(0));
                }
            }

            @Override
            public void error(int code, String error) {

            }
        });
    }

    @Override
    public void getBankCard() {
        myWealthModel.getBank(new OnRequestLinstener<BaseResponseEntity<Object>>() {

            @Override
            public void success(BaseResponseEntity<Object> bankCardListBeanBaseResponseEntity) {
                if (bankCardListBeanBaseResponseEntity.ReturnMessage instanceof  QuotaBank)
                activity.setBank((QuotaBank) bankCardListBeanBaseResponseEntity.ReturnMessage);

            }

            @Override
            public void error(int code, String error) {

            }
        });
    }
}
