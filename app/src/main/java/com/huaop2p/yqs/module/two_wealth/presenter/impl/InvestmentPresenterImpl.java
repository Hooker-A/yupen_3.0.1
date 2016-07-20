package com.huaop2p.yqs.module.two_wealth.presenter.impl;

import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.model.impl.MyWealthModelImpl;
import com.huaop2p.yqs.module.two_wealth.activity.InvestmentActivity;
import com.huaop2p.yqs.module.two_wealth.model.entity.Order;
import com.huaop2p.yqs.module.two_wealth.model.impl.WealthCenterModelImpl;
import com.huaop2p.yqs.module.two_wealth.presenter.IInvestmentPresenter;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/31.
 * 作者：任洋
 * 功能：
 */
public class InvestmentPresenterImpl extends BasePresenter implements IInvestmentPresenter {
    private InvestmentActivity activity;
    private WealthCenterModelImpl model;
    private MyWealthModelImpl myWealthModel;

    public InvestmentPresenterImpl(InvestmentActivity activity) {
        this.activity = activity;
        model = new WealthCenterModelImpl();
        myWealthModel = new MyWealthModelImpl();
    }

    @Override
    public void payYuXin(Map<String, Object> map) {
        activity.startLoadData();
        model.pay(map, HttpUrl.PAY_XIN, new OnRequestLinstener<BaseResponseEntity<Order>>() {
            @Override
            public void success(BaseResponseEntity<Order> entity) {
                activity.loadDataOver();
                activity.showSuccess(entity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
                activity.showError(code, error);
            }
        });
    }

    @Override
    public void payHouseCar(Map<String, Object> map) {
        activity.startLoadData();
        model.pay(map, HttpUrl.PAY_HOUSE_CAR, new OnRequestLinstener<BaseResponseEntity<Order>>() {
            @Override
            public void success(BaseResponseEntity<Order> entity) {
                activity.loadDataOver();
                activity.showSuccess(entity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
                activity.showError(code, error);
            }
        });
    }

    @Override
    public void payTransfer(Map<String, Object> map) {
        activity.startLoadData();
        model.pay(map, HttpUrl.PAY_TRANSFER, new OnRequestLinstener<BaseResponseEntity<Order>>() {
            @Override
            public void success(BaseResponseEntity<Order> entity) {
                activity.loadDataOver();
                activity.showSuccess(entity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
                activity.showError(code, error);
            }
        });
    }

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
    public void getYuXinAgreement() {
        activity.startLoadData();
        myWealthModel.getAgreement(new OnRequestLinstener<BaseResponseEntity<String>>() {

            @Override
            public void success(BaseResponseEntity<String> entity) {
                activity.loadDataOver();
                activity.startWebActivity(entity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
            }
        }, HttpUrl.SeasonRed);
    }

    @Override
    public void getYuFangAgreement() {
        activity.startLoadData();
        myWealthModel.getAgreement(new OnRequestLinstener<BaseResponseEntity<String>>() {
            @Override
            public void success(BaseResponseEntity<String> o) {
                activity.loadDataOver();
                activity.startWebActivity(o.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
            }
        }, HttpUrl.GetHouseModel);
    }

    @Override
    public void getYuCheAgreement() {
        activity.startLoadData();
        myWealthModel.getAgreement(new OnRequestLinstener<BaseResponseEntity<String>>() {
            @Override
            public void success(BaseResponseEntity<String> o) {
                activity.loadDataOver();
                activity.startWebActivity(o.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
            }
        }, HttpUrl.GetCarModel);
    }
}
