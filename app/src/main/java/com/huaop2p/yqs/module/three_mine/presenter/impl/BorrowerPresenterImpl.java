package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;
import com.huaop2p.yqs.module.three_mine.model.impl.BorrowerModelImpl;
import com.huaop2p.yqs.module.three_mine.view.fragment.BorrowerFragment;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/19.
 * 作者：任洋
 * 功能：
 */
public class BorrowerPresenterImpl extends BasePresenter {

    private BorrowerFragment fragment;
    private BorrowerModelImpl model;

    public BorrowerPresenterImpl(BorrowerFragment fragment) {
        this.fragment = fragment;
        model=new BorrowerModelImpl();
    }

    @Override
    public void loadDetail(Map<String, Object> map, String url, int what, RequestMethod method) {
        fragment.startLoadData();
        model.loadDetailById(map, new OnRequestLinstener<BaseResponseEntity<List<Borrower>>>() {

            @Override
            public void success(BaseResponseEntity<List<Borrower>> defaultBorrowerBaseResponseEntity) {
                fragment.loadDataOver();
                fragment.showSuccess(defaultBorrowerBaseResponseEntity);
            }

            @Override
            public void error(int code, String error) {
                fragment.loadDataOver();
                fragment.showError(code,error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<List<Borrower>>>() {
        });
    }
}
