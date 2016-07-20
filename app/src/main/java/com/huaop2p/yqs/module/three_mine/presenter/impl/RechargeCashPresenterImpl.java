package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.activity.RechargeCashActivity;
import com.huaop2p.yqs.module.three_mine.model.entity.Balance;
import com.huaop2p.yqs.module.three_mine.model.impl.MyWealthModelImpl;
import com.huaop2p.yqs.module.three_mine.presenter.IRechargeCashPresenter;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 * 作者：任洋
 * 功能：
 */
public class RechargeCashPresenterImpl extends BasePresenter implements IRechargeCashPresenter {
    private RechargeCashActivity activity;
    private MyWealthModelImpl myWealthModel;

    public RechargeCashPresenterImpl(RechargeCashActivity activity) {
        super();
        this.activity = activity;
        myWealthModel = new MyWealthModelImpl();

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
                    activity.showSuccess(o.ReturnMessage.get(0));
                }
            }

            @Override
            public void error(int code, String error) {

            }
        });
    }
}
