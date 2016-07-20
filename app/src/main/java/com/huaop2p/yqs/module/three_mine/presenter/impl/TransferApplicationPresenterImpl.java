package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.activity.TransferApplicationActivity;
import com.huaop2p.yqs.module.three_mine.model.impl.TransferModelImpl;
import com.huaop2p.yqs.module.three_mine.presenter.ITransferApplicationPresenter;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/30.
 * 作者：任洋
 * 功能：
 */
public class TransferApplicationPresenterImpl extends BasePresenter implements ITransferApplicationPresenter {
    private TransferModelImpl model;
    private TransferApplicationActivity activity;

    public TransferApplicationPresenterImpl(TransferApplicationActivity activity) {
        this.activity = activity;
        model = new TransferModelImpl();
    }

    @Override
    public void applicationTransfer(Map<String, Object> map) {
        activity.startLoadData();
        model.addTransferRecord(map, new OnRequestLinstener<BaseResponseEntity<String>>() {
            @Override
            public void success(BaseResponseEntity<String> entity) {
                activity.loadDataOver();
                activity.showSuccess(entity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
                activity.showError(code, error);
            }
        });
    }
}
