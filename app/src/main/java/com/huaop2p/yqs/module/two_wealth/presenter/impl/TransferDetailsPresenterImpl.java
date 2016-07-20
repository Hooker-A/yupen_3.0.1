package com.huaop2p.yqs.module.two_wealth.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.two_wealth.activity.TransferDetailsActivity;
import com.huaop2p.yqs.module.two_wealth.model.entity.Transfer;
import com.huaop2p.yqs.module.two_wealth.model.impl.TranferModelImpl;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/30.
 * 作者：任洋
 * 功能：
 */
public class TransferDetailsPresenterImpl extends BasePresenter {
    private TranferModelImpl model;
    private TransferDetailsActivity activity;

    public TransferDetailsPresenterImpl(TransferDetailsActivity activity) {
        this.activity = activity;
        model=new TranferModelImpl();
    }

    @Override
    public void loadDetail(Map<String, Object> map, String url, int what, RequestMethod method) {
        model.loadDetailById(map, new OnRequestLinstener<BaseResponseEntity<Transfer>>() {

            @Override
            public void success(BaseResponseEntity<Transfer> transferBaseResponseEntity) {
                activity.showSuccess(transferBaseResponseEntity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.showError(code,error);
            }
        },url,what,method,new TypeToken<BaseResponseEntity<Transfer>>(){});
    }
}
