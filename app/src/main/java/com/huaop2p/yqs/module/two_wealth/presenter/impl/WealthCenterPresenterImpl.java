package com.huaop2p.yqs.module.two_wealth.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.contant.HttpUrl;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.Borrower;
import com.huaop2p.yqs.module.two_wealth.model.entity.WealthCenter;
import com.huaop2p.yqs.module.two_wealth.model.impl.BorrowerInfoModel;
import com.huaop2p.yqs.module.two_wealth.model.impl.WealthCenterModelImpl;
import com.huaop2p.yqs.module.two_wealth.presenter.IWealthCenterPresenter;
import com.huaop2p.yqs.module.two_wealth.view.fragment.WealthCenterFragment;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/11.
 */
public class WealthCenterPresenterImpl extends BasePresenter implements IWealthCenterPresenter {
    private WealthCenterModelImpl wealthModel;
    private WealthCenterFragment wealthCenterView;
    private BorrowerInfoModel model;
    private int page;
    private int type;

    public WealthCenterPresenterImpl(WealthCenterFragment wealthCenterView, int type) {
        this.wealthCenterView = wealthCenterView;
        wealthModel = new WealthCenterModelImpl();
        this.type = type;
    }

    @Override
    public void loadData(Map<String, Object> map, String url, int what, RequestMethod method) {
        if (wealthCenterView.getData() == null || wealthCenterView.getData().size() == 0) {
            wealthCenterView.setData(wealthModel.loadNativeData(type));
        }
        wealthCenterView.startLoadData();
        wealthModel.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<WealthCenter>>>() {

            @Override
            public void success(BaseResponseEntity<List<WealthCenter>> yuXinBaoList) {
                wealthCenterView.loadDataOver();
                wealthModel.delAllDatas(type);
                wealthModel.saveDatas(yuXinBaoList.ReturnMessage, type);
                if (page == 1) {
                    wealthCenterView.clearData();
                }
                wealthCenterView.showSuccess(yuXinBaoList);
            }

            @Override
            public void error(int code, String error) {
                wealthCenterView.loadDataOver();
                wealthCenterView.showError(code, error);
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<List<WealthCenter>>>() {
        });
    }

    public void setPageIndex(int pageIndex) {
        this.page = pageIndex;
    }

    @Override
    public void loadBorrowerInfo(Map<String, Object> map) {
        if (model == null)
            model = new BorrowerInfoModel();
        model.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<String>>>() {
            @Override
            public void success(BaseResponseEntity<List<String>> listBaseResponseEntity) {
                wealthCenterView.showBorrowerInfo(listBaseResponseEntity.ReturnMessage);
            }
            @Override
            public void error(int code, String error) {
                wealthCenterView.showError(code, error);
            }
        }, HttpUrl.SelectTopTen, 0, RequestMethod.POST, new TypeToken<BaseResponseEntity<List<String>>>() {
        });
    }
}
