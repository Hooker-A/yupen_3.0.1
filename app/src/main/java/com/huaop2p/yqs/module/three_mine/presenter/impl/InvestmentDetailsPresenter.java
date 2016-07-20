package com.huaop2p.yqs.module.three_mine.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.activity.InvestmentDetailsActivity;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.model.impl.InvestmentModelImpl;
import com.huaop2p.yqs.module.three_mine.presenter.IInvestmentPresenter;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/25.
 * 作者：任洋
 * 功能：投资记录详情
 */
public class InvestmentDetailsPresenter extends BasePresenter implements IInvestmentPresenter {
    public InvestmentModelImpl investmentModel;
    public InvestmentDetailsActivity activity;

    public InvestmentDetailsPresenter(InvestmentDetailsActivity activity) {
        this.activity = activity;
        investmentModel=new InvestmentModelImpl();
    }

    @Override
    public void loadDetail(Map<String, Object> map, String url, int what, RequestMethod method) {
        activity.startLoadData();
        investmentModel.loadDetailById(map, new OnRequestLinstener<BaseResponseEntity<Investment>>() {

            @Override
            public void success(BaseResponseEntity<Investment> investment) {
                activity.loadDataOver();
                activity.showSuccess(investment.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
            }
        }, url, what, method, new TypeToken<BaseResponseEntity<Investment>>() {
        });
    }

    @Override
    public void getContract(Map<String, Object> map, String url, int what, RequestMethod method) {
        investmentModel.getContract(map, new OnRequestLinstener<BaseResponseEntity<String>>() {

            @Override
            public void success(BaseResponseEntity<String> contractBaseResponseEntity) {
                activity.getContract(contractBaseResponseEntity.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {
                activity.getContract("");
            }
        }, url, what, method);
    }

}
