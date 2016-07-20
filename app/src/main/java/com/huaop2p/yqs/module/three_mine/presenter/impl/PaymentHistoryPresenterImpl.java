package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.PaymentHistory;
import com.huaop2p.yqs.module.three_mine.model.impl.PaymentHistoryModelImpl;
import com.huaop2p.yqs.module.three_mine.view.fragment.PaymentHistoryFragment;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/27.
 * 作者：任洋
 * 功能：
 */
public class PaymentHistoryPresenterImpl extends BasePresenter {
    private PaymentHistoryModelImpl model;
    private PaymentHistoryFragment fragment;

    public PaymentHistoryPresenterImpl(PaymentHistoryFragment fragment) {
        this.fragment = fragment;
        model=new PaymentHistoryModelImpl();
    }

    @Override
    public void loadData(Map<String, Object> map, String url, int what, RequestMethod method) {
        fragment.startLoadData();
        model.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<PaymentHistory>>>() {

            @Override
            public void success(BaseResponseEntity<List<PaymentHistory>> listBaseResponseEntity) {
                fragment.loadDataOver();
                fragment.showSuccess(listBaseResponseEntity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                fragment.loadDataOver();
                fragment.showError(code,error);
            }
        },url,what,method,new TypeToken<BaseResponseEntity<List<PaymentHistory>>>(){});
    }
}
