package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.TransferRecord;
import com.huaop2p.yqs.module.three_mine.model.impl.TransferModelImpl;
import com.huaop2p.yqs.module.three_mine.view.fragment.TransferRecordFragment;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/21.
 */
public class TransferRecordPresenterImpl extends BasePresenter  {
    public TransferModelImpl transferModel;
    public TransferRecordFragment iInVestmentView;

    public TransferRecordPresenterImpl(TransferRecordFragment iInVestmentView) {
        this.iInVestmentView = iInVestmentView;
        transferModel=new TransferModelImpl();
    }

    @Override
    public void loadData(Map<String, Object> map, String url, int what, RequestMethod method) {
        iInVestmentView.startLoadData();
        transferModel.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<TransferRecord>>>() {

            @Override
            public void success(BaseResponseEntity<List<TransferRecord>> transfers) {
                iInVestmentView.loadDataOver();
                iInVestmentView.showSuccess(transfers);
            }

            @Override
            public void error(int code, String error) {
                iInVestmentView.loadDataOver();
                iInVestmentView.showError(code,error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<List<TransferRecord>>>(){});
    }
}
