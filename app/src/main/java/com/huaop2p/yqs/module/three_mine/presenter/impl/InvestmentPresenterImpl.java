package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.model.impl.InvestmentModelImpl;
import com.huaop2p.yqs.module.three_mine.view.fragment.InVestmentRecordFragment;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/21.
 */
public class InvestmentPresenterImpl extends BasePresenter {
    public InvestmentModelImpl investmentModel;
    public InVestmentRecordFragment iInVestmentView;

    public InvestmentPresenterImpl(InVestmentRecordFragment iInVestmentView) {
        this.iInVestmentView = iInVestmentView;
        investmentModel=new InvestmentModelImpl();
    }

    @Override
    public void loadData(Map<String, Object> map, String url, int what, RequestMethod method) {
        iInVestmentView.startLoadData();
        investmentModel.loadDatas(map, new OnRequestLinstener<BaseResponseEntity<List<Investment>>>() {

            @Override
            public void success(BaseResponseEntity<List<Investment>> investment) {
                iInVestmentView.loadDataOver();
                iInVestmentView.showSuccess(investment);
            }

            @Override
            public void error(int code, String error) {

            }
        }, url, what, method, new TypeToken<BaseResponseEntity<List<Investment>>>(){});
    }


}
