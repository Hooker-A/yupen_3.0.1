package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.activity.TransferRecordDetailsActivity;
import com.huaop2p.yqs.module.three_mine.model.entity.TransferRecord;
import com.huaop2p.yqs.module.three_mine.model.impl.TransferModelImpl;
import com.huaop2p.yqs.module.three_mine.presenter.ITransferRecordDetailsPresenter;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/25.
 * 作者：任洋
 * 功能：
 */
public class TransferRecordDetailsPresenterImpl extends BasePresenter implements ITransferRecordDetailsPresenter {
    public TransferModelImpl transferModel;
    private TransferRecordDetailsActivity activity;

    public TransferRecordDetailsPresenterImpl(TransferRecordDetailsActivity activity) {
        this.activity = activity;
        transferModel=new TransferModelImpl();
    }

    @Override
    public void loadDetail(Map<String, Object> map, String url, int what, RequestMethod method) {
        transferModel.loadDetailById(map, new OnRequestLinstener<BaseResponseEntity<TransferRecord>>() {
            @Override
            public void success(BaseResponseEntity<TransferRecord> transfers) {
                activity.loadDataOver();
                activity.loadDetails(transfers.ReturnMessage);
            }
            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
                activity.showError(code,error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<TransferRecord>>(){});
    }

    @Override
    public void delTransferRecordById(Map<String, Object> map) {
        transferModel.delTransferRecordById(map, new OnRequestLinstener<BaseResponseEntity<String>>() {
            @Override
            public void success(BaseResponseEntity<String> transfers) {
                activity.loadDataOver();
                activity.cancelTransfer(transfers.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
                activity.showError(code, error);
            }
        });
    }
}
