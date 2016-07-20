package com.huaop2p.yqs.module.two_wealth.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;
import com.huaop2p.yqs.module.two_wealth.activity.BorrowerInfoActivity;
import com.huaop2p.yqs.module.two_wealth.model.impl.BorrowerInfoModel;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/17.
 * 作者：任洋
 * 功能：
 */
public class BorrowerInfoPresenterImpl extends BasePresenter {
    private BorrowerInfoModel model;
    private BorrowerInfoActivity activity;
    private int pageIndex;

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public BorrowerInfoPresenterImpl(BorrowerInfoActivity activity) {
        this.activity = activity;
        model = new BorrowerInfoModel();
    }

    @Override
    public void loadData(Map<String, Object> map, String url, int what, RequestMethod method) {
        model.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<Borrower>>>() {
            @Override
            public void success(BaseResponseEntity<List<Borrower>> listBaseResponseEntity) {
                activity.loadDataOver();
                if (pageIndex == 1)
                    activity.clearData();
                activity.showSuccess(listBaseResponseEntity);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
                activity.showError(code, error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<List<Borrower>>>() {
        });
    }
}
