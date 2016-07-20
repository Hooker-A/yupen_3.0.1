package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.LendInfo;
import com.huaop2p.yqs.module.three_mine.model.impl.LendListModelImpl;
import com.huaop2p.yqs.module.three_mine.view.fragment.LendListFragment;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/10.
 * 作者：任洋
 * 功能：
 */
public class LendListPresenterImpl extends BasePresenter {
    private LendListFragment view;
    private LendListModelImpl lendListModel;

    public LendListPresenterImpl(LendListFragment view) {
        this.view = view;
        lendListModel = new LendListModelImpl();
    }

    @Override
    public void loadData(Map map, String url, int what, RequestMethod method) {
        lendListModel.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<LendInfo>>>() {

            @Override
            public void success(BaseResponseEntity<List<LendInfo>> investmentBaseResponseEntity) {
                view.loadDataOver();
                view.showSuccess(investmentBaseResponseEntity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                view.loadDataOver();
                view.showError(code, error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<List<LendInfo>>>() {
        });
    }

}
