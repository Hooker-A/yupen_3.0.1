package com.huaop2p.yqs.module.two_wealth.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.two_wealth.model.entity.Investor;
import com.huaop2p.yqs.module.two_wealth.model.impl.InvestorModelImpl;
import com.huaop2p.yqs.module.two_wealth.view.fragment.InvestorListFragment;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/23.
 * 作者：任洋
 * 功能：
 */
public class InvestorPresenterImpl extends BasePresenter {
    private InvestorModelImpl model;
    private InvestorListFragment fragment;

    public InvestorPresenterImpl(InvestorListFragment fragment) {
        this.fragment = fragment;
        model = new InvestorModelImpl();
    }

    @Override
    public void loadData(Map<String, Object> map, String url, int what, RequestMethod method) {
        model.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<Investor>>>() {

            @Override
            public void success(BaseResponseEntity<List<Investor>> listBaseResponseEntity) {
                fragment.loadDataOver();
                fragment.showSuccess(listBaseResponseEntity);
            }
            @Override
            public void error(int code, String error) {
                fragment.loadDataOver();
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<List<Investor>>>() {
        });
    }
}
