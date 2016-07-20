package com.huaop2p.yqs.module.two_wealth.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.presenter.BasePresenter;
import com.huaop2p.yqs.module.three_mine.model.entity.Contract;
import com.huaop2p.yqs.module.three_mine.model.entity.Investment;
import com.huaop2p.yqs.module.three_mine.model.impl.InvestmentModelImpl;
import com.huaop2p.yqs.module.three_mine.presenter.IInvestmentPresenter;
import com.huaop2p.yqs.module.two_wealth.activity.InvestmentSuccessActivity;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.RequestMethod;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/12.
 * 作者：任洋
 * 功能：
 */
public class InvestmentSuccessPresenterImpl extends BasePresenter implements IInvestmentPresenter {
    public InvestmentModelImpl investmentModel;
    public InvestmentSuccessActivity activity;

    public InvestmentSuccessPresenterImpl(InvestmentSuccessActivity activity) {
        this.activity = activity;
        investmentModel=new InvestmentModelImpl();
    }

    @Override
    public void loadDetail(Map<String, Object> map, String url, int what, RequestMethod method) {
        investmentModel.loadDetailById(map, new OnRequestLinstener<BaseResponseEntity<Investment>>() {

            @Override
            public void success(BaseResponseEntity<Investment> investment) {
                activity.showSuccess(investment.ReturnMessage);
            }

            @Override
            public void error(int code, String error) {

            }
        }, url, what, method, new TypeToken<BaseResponseEntity<Investment>>() {
        });
    }
    @Override
    public void getContract(Map<String, Object> map, String url, int what, RequestMethod method) {
        activity.startLoadData();
        investmentModel.getContract(map, new OnRequestLinstener<BaseResponseEntity<Contract>>() {

            @Override
            public void success(BaseResponseEntity<Contract> contractBaseResponseEntity) {
                activity.loadDataOver();
                activity.getContract(contractBaseResponseEntity.ReturnMessage.Remarks);
            }

            @Override
            public void error(int code, String error) {
                activity.loadDataOver();
                activity.getContract("");
            }
        }, url, what, method);
    }
}
