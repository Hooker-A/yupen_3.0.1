package com.huaop2p.yqs.module.two_wealth.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;
import com.huaop2p.yqs.module.two_wealth.activity.BorrowerListDialog;
import com.huaop2p.yqs.module.two_wealth.model.impl.BorrowerInfoModel;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 * 作者：任洋
 * 功能：
 */
public class BorrowerListPresenterImpl extends BasePresenter {
    private BorrowerInfoModel model;
    private BorrowerListDialog dialog;

    public BorrowerListPresenterImpl(BorrowerListDialog dialog) {
        this.dialog = dialog;
        model = new BorrowerInfoModel();
    }

    @Override
    public void loadData(Map<String, Object> map, String url, int what, RequestMethod method) {
        model.loadDetailById(map, new OnRequestLinstener<BaseResponseEntity<Map<String,List<Borrower>>>>() {

            @Override
            public void success(BaseResponseEntity<Map<String, List<Borrower>>> mapBaseResponseEntity) {
                dialog.showSuccess(mapBaseResponseEntity.ReturnMessage.get("_default"));
            }

            @Override
            public void error(int code, String error) {
                dialog.showError(code, error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<Map<String,List<Borrower>>>>() {
        });
    }
}
