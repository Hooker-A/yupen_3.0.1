package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.model.impl.HistoryModelImpl;
import com.huaop2p.yqs.module.three_mine.view.fragment.HistoryFragment;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/18.
 * 作者：任洋
 * 功能：
 */
public class HistoryPresenterImpl extends BasePresenter {
    private HistoryFragment fragment;
    private HistoryModelImpl historyModel;

    public HistoryPresenterImpl(HistoryFragment fragment) {
        this.fragment = fragment;
        historyModel=new HistoryModelImpl();
    }

    @Override
    public void loadData(Map<String, Object> map, String url, int what, RequestMethod method){
        fragment.startLoadData();
        historyModel.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<Investment>>>() {
            @Override
            public void success(BaseResponseEntity<List<Investment>> listBaseResponseEntity) {
                fragment.loadDataOver();
                fragment.showSuccess(listBaseResponseEntity);
            }
            @Override
            public void error(int code, String error) {
                fragment.loadDataOver();
                fragment.showError(code,error);
            }
        },url, what, method,new TypeToken<BaseResponseEntity<List<Investment>>>(){});
    }
}
