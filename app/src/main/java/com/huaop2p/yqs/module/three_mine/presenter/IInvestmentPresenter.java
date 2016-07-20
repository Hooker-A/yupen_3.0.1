package com.huaop2p.yqs.module.three_mine.presenter;

import com.yolanda.nohttp.RequestMethod;

import java.util.Map;

/**
 * Created by Administrator on 2016/4/21.
 */
public interface IInvestmentPresenter {
    public void  getContract(Map<String, Object> map, String url, int what, RequestMethod method);
}
