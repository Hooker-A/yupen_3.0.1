package com.huaop2p.yqs.module.two_wealth.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.two_wealth.model.entity.Transfer;
import com.huaop2p.yqs.module.two_wealth.model.impl.TranferModelImpl;
import com.huaop2p.yqs.module.two_wealth.view.fragment.TransferFragment;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/12.
 * 作者：任洋
 * 功能：
 */
public class TransferPresenterImpl extends BasePresenter {

    private TranferModelImpl tranferModel;
    private TransferFragment transferFragment;
    private int page;
    public void setPageIndex(int pageIndex) {
        this.page = pageIndex;
    }
    public TransferPresenterImpl(TransferFragment transferFragment) {
        this.transferFragment = transferFragment;
        tranferModel=new TranferModelImpl();
    }

    @Override
    public void loadData(Map<String, Object> map, String url, int what, RequestMethod method)  {
        if (transferFragment.getData() == null || transferFragment.getData().size() == 0) {
            transferFragment.setData(tranferModel.loadNativeData());
        }
        transferFragment.startLoadData();
        tranferModel.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<Transfer>>>() {

            @Override
            public void success(BaseResponseEntity<List<Transfer>> transfer) {
                transferFragment.loadDataOver();
                tranferModel.delAllDatas();
                tranferModel.saveDatas(transfer.ReturnMessage);
                if (page == 1) {
                    transferFragment.clearData();
                }
                transferFragment.showSuccess(transfer);
            }

            @Override
            public void error(int code, String error) {
                transferFragment.loadDataOver();
                transferFragment.showError(code, error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<List<Transfer>>>() {
        });
    }


}
