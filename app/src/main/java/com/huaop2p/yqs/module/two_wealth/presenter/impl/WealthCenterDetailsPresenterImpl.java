package com.huaop2p.yqs.module.two_wealth.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.two_wealth.activity.WealthCenterDetailActivity;
import com.huaop2p.yqs.module.two_wealth.model.entity.WealthCenter;
import com.huaop2p.yqs.module.two_wealth.model.impl.WealthCenterModelImpl;
import com.huaop2p.yqs.module.two_wealth.presenter.IWealthCenterDetailsPresenter;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/26.
 * 作者：任洋
 * 功能：
 */
public class WealthCenterDetailsPresenterImpl extends BasePresenter implements IWealthCenterDetailsPresenter {
    private WealthCenterModelImpl model;
    private WealthCenterDetailActivity activity;

    public WealthCenterDetailsPresenterImpl(WealthCenterDetailActivity activity) {
        this.activity = activity;
        model = new WealthCenterModelImpl();
    }

    @Override
    public void loadDetail(Map<String, Object> map, String url, int what, RequestMethod method) {
        activity.startLoadData();
        model.loadDetailById(map, new OnRequestLinstener<BaseResponseEntity<WealthCenter>>() {

            @Override
            public void success(BaseResponseEntity<WealthCenter> wealthCenterBaseResponseEntity) {
                activity.loadDataOver();
                activity.showSuccess(wealthCenterBaseResponseEntity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
                activity.showError(code, error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<WealthCenter>>() {
        });
    }

    @Override
    public void getH5(Map<String,Object> map) {
        model.getH5(map, new OnRequestLinstener<BaseResponseEntity<Map<String, String>> > () {

            @Override
            public void success(BaseResponseEntity<Map<String, String>> entity) {
                activity.loadH5(entity.ReturnMessage.get("Url"));
            }

            @Override
            public void error(int code, String error) {

            }
        });
    }
}
