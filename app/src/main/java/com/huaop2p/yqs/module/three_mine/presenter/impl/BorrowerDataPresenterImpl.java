package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.BorrowersData;
import com.huaop2p.yqs.module.three_mine.model.impl.BorrowerDataModelImpl;
import com.huaop2p.yqs.module.three_mine.view.fragment.PersonalDataFragment;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/26.
 * 作者：任洋
 * 功能：
 */
public class BorrowerDataPresenterImpl extends BasePresenter {
    private BorrowerDataModelImpl model;
    private PersonalDataFragment fragment;
    public BorrowerDataPresenterImpl(PersonalDataFragment fragment) {
        this.fragment = fragment;
        model=new BorrowerDataModelImpl();
    }

    @Override
    public void loadDetail(Map<String, Object> map, String url, int what, RequestMethod method) {
        fragment.startLoadData();
        model.loadDetailById(map, new OnRequestLinstener<BaseResponseEntity<List<BorrowersData>>>() {

            @Override
            public void success(BaseResponseEntity<List<BorrowersData>> borrowersDataBaseResponseEntity) {
                fragment.loadDataOver();
                fragment.showSuccess(borrowersDataBaseResponseEntity.ReturnMessage.get(0));
            }

            @Override
            public void error(int code, String error) {
                fragment.loadDataOver();
                fragment.showError(code, error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<List<BorrowersData>>>(){});
    }
}
