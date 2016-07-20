package com.huaop2p.yqs.module.two_wealth.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.two_wealth.activity.BorrowerInfoDetailsActivity;
import com.huaop2p.yqs.module.two_wealth.model.entity.BorrowerDetails;
import com.huaop2p.yqs.module.two_wealth.model.impl.BorrowerInfodetailsModelImpl;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/24.
 * 作者：任洋
 * 功能：
 */
public class BorrowerDetailsPresenterImpl extends BasePresenter {
    private BorrowerInfodetailsModelImpl model;
    private BorrowerInfoDetailsActivity activity;

    public BorrowerDetailsPresenterImpl(BorrowerInfoDetailsActivity activity) {
        this.activity = activity;
        model = new BorrowerInfodetailsModelImpl();
    }

    @Override
    public void loadDetail(Map<String, Object> map, String url, int what, RequestMethod method) {
        activity.startLoadData();
        model.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<BorrowerDetails>>>() {

            @Override
            public void success(BaseResponseEntity<List<BorrowerDetails>> borrowerDetailsBaseResponseEntity) {
                activity.loadDataOver();
                activity.showSuccess(borrowerDetailsBaseResponseEntity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
                activity.showError(code, error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<List<BorrowerDetails>>>() {
        });
    }
}
