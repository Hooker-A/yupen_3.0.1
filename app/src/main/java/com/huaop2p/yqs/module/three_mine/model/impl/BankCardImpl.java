package com.huaop2p.yqs.module.three_mine.model.impl;

import com.google.gson.reflect.TypeToken;
import com.huaop2p.yqs.module.base.entity.BaseResponseEntity;
import com.huaop2p.yqs.module.base.model.BaseModel;
import com.huaop2p.yqs.module.three_mine.model.entity.BankCardListBean;
import com.huaop2p.yqs.utils.net.CallServer;
import com.huaop2p.yqs.utils.net.FastJsonRequest;
import com.huaop2p.yqs.utils.net.GsonUtils;
import com.huaop2p.yqs.utils.net.OnRequestLinstener;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import java.util.Map;

/**
 * Created by maoxiaofei on 2016/6/2.
 */
public class BankCardImpl extends BaseModel<BankCardListBean> {

    private static class singleTop {
        private static final BankCardImpl INSTANCE = new BankCardImpl();
    }

    public static BankCardImpl getintence() {
        return singleTop.INSTANCE;
    }

    public BankCardImpl() {

    }

    public void Getbankcard(Map map, final OnRequestLinstener<BaseResponseEntity<BankCardListBean> >requestLinstener, String url, int what, RequestMethod method, TypeToken typeToken) {

        Request<BaseResponseEntity<BankCardListBean>> request = new FastJsonRequest<BaseResponseEntity<BankCardListBean>>(url, method, typeToken);
        String reqBody = GsonUtils.getGson().toJson(map);
        request.setRequestBody(reqBody);
        CallServer.getRequestInstance().add(what, request, new OnResponseListener<BaseResponseEntity<BankCardListBean>>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<BaseResponseEntity<BankCardListBean>> response) {
                requestLinstener.success(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
